package com.example.data.exchanges


import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.LoadState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.data.MainDispatcherRule
import com.example.data.repository.exchanges.ExchangesRepositoryImpl
import com.example.model.exchanges.ExchangeTickers
import com.example.model.sort.ExchangeTickersOrder
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.model.exchanges.ExchangeTickersDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ExchangesRepositoryImplTickersTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private lateinit var remote: ExchangesDataSource
    private lateinit var repository: ExchangesRepositoryImpl

    private val diff = object : DiffUtil.ItemCallback<ExchangeTickers.Ticker>() {
        override fun areItemsTheSame(oldItem: ExchangeTickers.Ticker, newItem: ExchangeTickers.Ticker): Boolean =
            oldItem.base == newItem.base && oldItem.target == newItem.target

        override fun areContentsTheSame(oldItem: ExchangeTickers.Ticker, newItem: ExchangeTickers.Ticker): Boolean =
            oldItem == newItem
    }

    private val noOpListCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    @Before
    fun setup() {
        remote = mockk(relaxed = true)
        repository = ExchangesRepositoryImpl(
            remoteDataSource = remote,
            ioDispatcher = mainRule.testDispatcher
        )
    }

    private fun ticker(base: String, target: String): ExchangeTickersDto.Ticker {
        val t = mockk<ExchangeTickersDto.Ticker>(relaxed = true)
        every { t.base } returns base
        every { t.target } returns target
        return t
    }

    private fun pageDto(vararg tickers: ExchangeTickersDto.Ticker): ExchangeTickersDto {
        val dto = mockk<ExchangeTickersDto>(relaxed = true)
        every { dto.tickers } returns tickers.toList()
        return dto
    }

    @Test
    fun `getExchangeTickersPaged - first page mapped items`() = runTest {
        // Given
        coEvery {
            remote.getExchangeTickersById(
                id = "binance",
                page = 1,
                coinIds = any(),
                includeExchangeLogo = any(),
                depth = any(),
                dexPairFormat = any(),
                order = any()
            )
        } returns pageDto(
            ticker("BTC", "USDT"),
            ticker("ETH", "USDT")
        )

        coEvery {
            remote.getExchangeTickersById(
                id = "binance",
                page = 2,
                coinIds = any(),
                includeExchangeLogo = any(),
                depth = any(),
                dexPairFormat = any(),
                order = any()
            )
        } returns pageDto() // empty

        val flow = repository.getExchangeTickersPaged(
            id = "binance",
            pageSize = 2
        )

        val differ = AsyncPagingDataDiffer(
            diffCallback = diff,
            updateCallback = noOpListCallback,
            mainDispatcher = mainRule.testDispatcher,
            workerDispatcher = mainRule.testDispatcher
        )

        val job = launch { flow.collectLatest { differ.submitData(it) } }

        advanceUntilIdle()

        val snap = differ.snapshot()
        assertEquals(2, snap.size)
        assertEquals("BTC", snap[0]?.base)
        assertEquals("USDT", snap[0]?.target)
        assertEquals("ETH", snap[1]?.base)

        // صفحه اول واقعاً فراخوانی شد
        coVerify(atLeast = 1) {
            remote.getExchangeTickersById(
                id = "binance",
                page = 1,
                coinIds = any(),
                includeExchangeLogo = any(),
                depth = any(),
                dexPairFormat = any(),
                order = any()
            )
        }

        job.cancel()
    }

    @Test
    fun `getExchangeTickersPaged - refresh error surfaces via LoadState`() = runTest {
        coEvery {
            remote.getExchangeTickersById(
                id = "binance",
                page = 1,
                coinIds = any(),
                includeExchangeLogo = any(),
                depth = any(),
                dexPairFormat = any(),
                order = any()
            )
        } throws IOException("boom")

        val flow = repository.getExchangeTickersPaged(
            id = "binance",
            pageSize = 2
        )

        val differ = AsyncPagingDataDiffer(
            diffCallback = diff,
            updateCallback = noOpListCallback,
            mainDispatcher = mainRule.testDispatcher,
            workerDispatcher = mainRule.testDispatcher
        )

        val job = launch { flow.collectLatest { differ.submitData(it) } }

        advanceUntilIdle()

        val state = differ.loadStateFlow.first()
        assertTrue(state.refresh is LoadState.Error)
        assertEquals(0, differ.snapshot().size)

        job.cancel()
    }

    @Test
    fun `getExchangeTickersPaged - forwards coinIds and order to data source`() = runTest {
        val coinIds = "bitcoin,ethereum"
        val order = ExchangeTickersOrder.TRUST_SCORE_DESC

        coEvery {
            remote.getExchangeTickersById(
                id = "binance",
                page = 1,
                coinIds = coinIds,
                includeExchangeLogo = true,
                depth = null,
                dexPairFormat = any(),
                order = order
            )
        } returns pageDto(ticker("BTC", "USDT"))

        coEvery {
            remote.getExchangeTickersById(
                id = "binance",
                page = 2,
                coinIds = any(),
                includeExchangeLogo = any(),
                depth = any(),
                dexPairFormat = any(),
                order = any()
            )
        } returns pageDto()

        val flow = repository.getExchangeTickersPaged(
            id = "binance",
            pageSize = 2,
            coinIds = coinIds,
            includeExchangeLogo = true,
            depth = null,
            dexPairFormat = null,
            order = order
        )

        val differ = AsyncPagingDataDiffer(
            diffCallback = diff,
            updateCallback = noOpListCallback,
            mainDispatcher = mainRule.testDispatcher,
            workerDispatcher = mainRule.testDispatcher
        )

        val job = launch { flow.collectLatest { differ.submitData(it) } }
        advanceUntilIdle()

        coVerify(exactly = 1) {
            remote.getExchangeTickersById(
                id = "binance",
                page = 1,
                coinIds = coinIds,
                includeExchangeLogo = true,
                depth = null,
                dexPairFormat = any(),
                order = order
            )
        }

        job.cancel()
    }
}
