package com.example.data.coins


import androidx.paging.PagingSource
import com.example.data.paging.CoinTickersPagingSource
import com.example.model.coins.CoinTickers
import com.example.model.sort.CoinTickersOrder
import com.example.model.sort.DexPairFormat
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.model.coins.CoinTickersDto
import com.example.network.model.coins.CoinTickersDto.Ticker
import com.example.network.model.coins.CoinTickersDto.Ticker.ConvertedLast
import com.example.network.model.coins.CoinTickersDto.Ticker.ConvertedVolume
import com.example.network.model.coins.CoinTickersDto.Ticker.Market
import com.example.network.model.mappers.coins.toDomain
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalCoroutinesApi::class)
class CoinTickersPagingSourceTest {

    private lateinit var remote: CoinsDataSource
    private lateinit var pagingSource: CoinTickersPagingSource
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        remote = mockk(relaxed = true)
    }

    private fun makeTickerDto(
        base: String,
        target: String,
        last: Double,
        exchange: String
    ): Ticker = Ticker(
        base = base,
        target = target,
        market = Market(identifier = exchange, name = exchange, hasTradingIncentive = false),
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
        coinId = "coin-id",
        coinMcapUsd = 1_000_000.0,
        targetCoinId = null
    )

    @Test
    fun `load page=1 success returns Page with mapped data and keys`() = runTest(dispatcher) {
        // Given
        val page1 = CoinTickersDto(
            name = "Bitcoin",
            tickers = listOf(
                makeTickerDto("BTC", "USDT", 100.0, "binance"),
                makeTickerDto("BTC", "USD",  99.5,  "coinbase")
            )
        )

        coEvery {
            remote.getCoinTickersById(
                id = "bitcoin",
                exchangeIds = null,
                includeExchangeLogo = true,
                depth = null,
                dexPairFormat = null,
                page = 1,
                order = CoinTickersOrder.TRUST_SCORE_DESC
            )
        } returns page1

        pagingSource = CoinTickersPagingSource(
            remote = remote,
            id = "bitcoin",
            exchangeIds = null,
            includeExchangeLogo = true,
            depth = null,
            dexPairFormat = null,
            order = CoinTickersOrder.TRUST_SCORE_DESC,
            pageTransform = null
        )

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 50,
                placeholdersEnabled = false
            )
        )

        // Then
        assertTrue(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page
        assertEquals(null, result.prevKey)
        assertEquals(2, result.nextKey)
        assertEquals(2, result.data.size)
        assertEquals("USDT", result.data[0].target) // mapped to domain
        assertEquals("USD",  result.data[1].target)

        coVerify(exactly = 1) {
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
    }

    @Test
    fun `load applies pageTransform`() = runTest(dispatcher) {
        val page1 = CoinTickersDto(
            name = "Bitcoin",
            tickers = listOf(
                makeTickerDto("BTC", "USD",  90.0, "coinbase"),
                makeTickerDto("BTC", "USDT", 100.0, "binance")
            )
        )
        coEvery { remote.getCoinTickersById(any(), any(), any(), any(), any(), 1, any()) } returns page1

        val transform: (List<CoinTickers.Ticker>) -> List<CoinTickers.Ticker> = { list ->
            list.sortedByDescending { it.last ?: Double.MIN_VALUE }
        }

        pagingSource = CoinTickersPagingSource(
            remote = remote,
            id = "bitcoin",
            pageTransform = transform
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 50, placeholdersEnabled = false)
        ) as PagingSource.LoadResult.Page

        // Expect USDT(100) before USD(90)
        assertEquals(listOf("USDT", "USD"), result.data.map { it.target })
    }

    @Test
    fun `load empty page - nextKey is null`() = runTest(dispatcher) {
        coEvery { remote.getCoinTickersById(any(), any(), any(), any(), any(), 2, any()) } returns
                CoinTickersDto(name = "Bitcoin", tickers = emptyList())

        pagingSource = CoinTickersPagingSource(remote = remote, id = "bitcoin")

        val result = pagingSource.load(
            PagingSource.LoadParams.Append(key = 2, loadSize = 50, placeholdersEnabled = false)
        ) as PagingSource.LoadResult.Page

        assertEquals(null, result.nextKey)
        assertTrue(result.data.isEmpty())
    }

    @Test
    fun `load throws from data source - returns Error`() = runTest(dispatcher) {
        coEvery { remote.getCoinTickersById(any(), any(), any(), any(), any(), any(), any()) } throws IOException("boom")

        pagingSource = CoinTickersPagingSource(remote = remote, id = "bitcoin")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 50, placeholdersEnabled = false)
        )
        assertTrue(result is PagingSource.LoadResult.Error)
    }
}
