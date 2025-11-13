package com.example.data.coins


import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.data.repository.coins.CoinsRepositoryImpl
import com.example.model.coins.CoinTickers
import com.example.model.sort.CoinTickersOrder
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.model.coins.CoinTickersDto
import com.example.network.model.coins.CoinTickersDto.Ticker
import com.example.network.model.coins.CoinTickersDto.Ticker.ConvertedLast
import com.example.network.model.coins.CoinTickersDto.Ticker.ConvertedVolume
import com.example.network.model.coins.CoinTickersDto.Ticker.Market
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoinsRepositoryGetCoinTickersPagedTest {

    private lateinit var remote: CoinsDataSource
    private lateinit var repository: CoinsRepositoryImpl
    private val dispatcher = StandardTestDispatcher()

    private val noOpListCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
    private val diff = object : DiffUtil.ItemCallback<CoinTickers.Ticker>() {
        override fun areItemsTheSame(oldItem: CoinTickers.Ticker, newItem: CoinTickers.Ticker) =
            oldItem.market?.identifier == newItem.market?.identifier &&
                    oldItem.base == newItem.base && oldItem.target == newItem.target

        override fun areContentsTheSame(oldItem: CoinTickers.Ticker, newItem: CoinTickers.Ticker) =
            oldItem == newItem
    }

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        remote = mockk(relaxed = true)
        repository = CoinsRepositoryImpl(remoteDataSource = remote, ioDispatcher = dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun t(base: String, target: String, last: Double, exch: String) = Ticker(
        base = base,
        target = target,
        market = Market(identifier = exch, name = exch, hasTradingIncentive = false),
        last = last,
        volume = 1000.0,
        trustScore = "green",
        bidAskSpreadPercentage = 0.05,
        convertedLast = ConvertedLast(btc = 1.0, eth = 10.0, usd = 100.0),
        convertedVolume = ConvertedVolume(btc = 10.0, eth = 100.0, usd = 10000.0),
        isAnomaly = false,
        isStale = false,
        lastFetchAt = "2025-11-11T00:00:00Z",
        lastTradedAt = "2025-11-11T00:00:00Z",
        timestamp = "2025-11-11T00:00:00Z",
        tokenInfoUrl = null,
        tradeUrl = "https://example.com/$base-$target",
        coinId = "coin-id", coinMcapUsd = 1_000_000.0, targetCoinId = null
    )

    @Test
    fun `getCoinTickersPaged emits mapped items for first page`() = runTest(dispatcher) {
        // Given: page1 دو تیکر، page2 خالی
        coEvery { remote.getCoinTickersById("bitcoin", any(), any(), any(), any(), 1, any()) } returns
                CoinTickersDto(name = "Bitcoin", tickers = listOf(t("BTC", "USDT", 100.0, "binance"), t("BTC", "USD", 99.5, "coinbase")))
        coEvery { remote.getCoinTickersById("bitcoin", any(), any(), any(), any(), 2, any()) } returns
                CoinTickersDto(name = "Bitcoin", tickers = emptyList())

        val flow = repository.getCoinTickersPaged(
            id = "bitcoin",
            pageSize = 50,
            order = CoinTickersOrder.TRUST_SCORE_DESC
        )

        val differ = AsyncPagingDataDiffer(
            diffCallback = diff,
            updateCallback = noOpListCallback,
            workerDispatcher = dispatcher,
            mainDispatcher = dispatcher
        )

        val job = launch {
            flow.collectLatest { pagingData -> differ.submitData(pagingData) }
        }

        advanceUntilIdle()

        val snapshot = differ.snapshot().items
        assertEquals(2, snapshot.size)
        assertEquals(listOf("USDT", "USD"), snapshot.map { it?.target })

        coVerify(atLeast = 1) {
            remote.getCoinTickersById(
                id = "bitcoin",
                exchangeIds = null,
                includeExchangeLogo = true,
                depth = null,
                dexPairFormat = null,
                page = 1,
                order = CoinTickersOrder.TRUST_SCORE_DESC
            )
        }

        job.cancel()
    }

    @Test
    fun `getCoinTickersPaged applies pageTransform`() = runTest(dispatcher) {
        coEvery { remote.getCoinTickersById(any(), any(), any(), any(), any(), 1, any()) } returns
                CoinTickersDto(name = "Bitcoin", tickers = listOf(t("BTC", "USD", 90.0, "coinbase"), t("BTC", "USDT", 100.0, "binance")))
        coEvery { remote.getCoinTickersById(any(), any(), any(), any(), any(), 2, any()) } returns
                CoinTickersDto(name = "Bitcoin", tickers = emptyList())

        val flow = repository.getCoinTickersPaged(
            id = "bitcoin",
            pageSize = 50,
            order = null,
            pageTransform = { list -> list.sortedByDescending { it.last ?: Double.MIN_VALUE } }
        )

        val differ = AsyncPagingDataDiffer(diff, noOpListCallback, dispatcher, dispatcher)

        val job = launch { flow.collectLatest { differ.submitData(it) } }
        advanceUntilIdle()

        assertEquals(listOf("USDT", "USD"), differ.snapshot().items.filterNotNull().map { it.target })

        job.cancel()
    }
}
