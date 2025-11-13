package com.example.data.coins


import com.example.common.result.AppResult
import com.example.data.repository.coins.CoinsRepositoryImpl
import com.example.model.coins.CoinHistoricalChart
import com.example.model.coins.CoinHistoricalData
import com.example.model.coins.CoinOHLCChartCandle
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.model.coins.CoinHistoricalChartDto
import com.example.network.model.coins.CoinHistoricalChartPointDto
import com.example.network.model.coins.CoinHistoricalDataDto
import com.example.network.model.coins.CoinOHLCChartCandleDto
import com.example.network.model.coins.CoinOHLCChartCandleDto.Ohlc
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class CoinsRepositoryHistoryAndChartsTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var remote: CoinsDataSource
    private lateinit var repository: CoinsRepositoryImpl

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        remote = mockk(relaxed = true)
        repository = CoinsRepositoryImpl(
            remoteDataSource = remote,
            ioDispatcher = dispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    private fun chartDto(): CoinHistoricalChartDto =
        CoinHistoricalChartDto(
            prices = listOf(CoinHistoricalChartPointDto(timestampMillis = 1_700_000_000_000, value = 100.0)),
            marketCaps = listOf(CoinHistoricalChartPointDto(timestampMillis = 1_700_000_000_000, value = 1_000_000_000.0)),
            totalVolumes = listOf(CoinHistoricalChartPointDto(timestampMillis = 1_700_000_000_000, value = 500_000.0)),
        )


    private fun historyDto(): CoinHistoricalDataDto =
        CoinHistoricalDataDto(
            communityData = null,
            developerData = null,
            id = "bitcoin",
            image = CoinHistoricalDataDto.Image(thumb = "t.png", small = "s.png"),
            localization = CoinHistoricalDataDto.Localization(translations = mapOf("en" to "Bitcoin")),
            marketData = CoinHistoricalDataDto.MarketData(
                currentPrice = CoinHistoricalDataDto.MarketData.CurrentPrice(mapOf("usd" to 100.0)),
                marketCap = CoinHistoricalDataDto.MarketData.MarketCap(mapOf("usd" to 1_000_000.0)),
                totalVolume = CoinHistoricalDataDto.MarketData.TotalVolume(mapOf("usd" to 500_000.0))
            ),
            name = "Bitcoin",
            publicInterestStats = null,
            symbol = "BTC"
        )

    private fun ohlcDto(): CoinOHLCChartCandleDto = CoinOHLCChartCandleDto(
        candles = listOf(Ohlc(1_700_000_000_000, 95.0, 105.0, 90.0, 100.0))
    )


    // ---------- Historical Data ----------

    @Test
    fun `getCoinHistoricalDataById emits Loading then Success`() = runTest(dispatcher) {
        coEvery { remote.getCoinHistoricalDataById("bitcoin", "11-11-2025", true) } returns historyDto()

        val emissions = mutableListOf<AppResult<CoinHistoricalData>>()
        val job = launch {
            repository.getCoinHistoricalDataById("bitcoin", "11-11-2025", true).collect { emissions.add(it) }
        }
        advanceUntilIdle()

        assertTrue(emissions.first() is AppResult.Loading)
        assertTrue(emissions.last() is AppResult.Success)

        coVerify(exactly = 1) { remote.getCoinHistoricalDataById("bitcoin", "11-11-2025", true) }
        job.cancel()
    }

    @Test
    fun `getCoinHistoricalDataById emits Error on exception`() = runTest(dispatcher) {
        coEvery { remote.getCoinHistoricalDataById(any(), any(), any()) } throws RuntimeException("boom")

        val emissions = mutableListOf<AppResult<CoinHistoricalData>>()
        val job = launch {
            repository.getCoinHistoricalDataById("bitcoin", "11-11-2025", true).collect { emissions.add(it) }
        }
        advanceUntilIdle()

        assertTrue(emissions.first() is AppResult.Loading)
        assertTrue(emissions.last() is AppResult.Error)
        job.cancel()
    }

    // ---------- Historical Chart (days) ----------

    @Test
    fun `getCoinHistoricalChart emits Loading then Success`() = runTest(dispatcher) {
        coEvery { remote.getCoinHistoricalChart("bitcoin", "usd", "7", null, null) } returns chartDto()

        val emissions = mutableListOf<AppResult<CoinHistoricalChart>>()
        val job = launch {
            repository.getCoinHistoricalChart("bitcoin", "usd", "7", null, null).collect { emissions.add(it) }
        }
        advanceUntilIdle()

        assertTrue(emissions.first() is AppResult.Loading)
        assertTrue(emissions.last() is AppResult.Success)

        coVerify(exactly = 1) { remote.getCoinHistoricalChart("bitcoin", "usd", "7", null, null) }
        job.cancel()
    }

    @Test
    fun `getCoinHistoricalChart emits Error on exception`() = runTest(dispatcher) {
        coEvery { remote.getCoinHistoricalChart(any(), any(), any(), any(), any()) } throws IllegalStateException("oops")

        val emissions = mutableListOf<AppResult<CoinHistoricalChart>>()
        val job = launch {
            repository.getCoinHistoricalChart("bitcoin", "usd", "30", null, null).collect { emissions.add(it) }
        }
        advanceUntilIdle()

        assertTrue(emissions.first() is AppResult.Loading)
        assertTrue(emissions.last() is AppResult.Error)
        job.cancel()
    }

    // ---------- Historical Chart (time range) ----------

    @Test
    fun `getCoinHistoricalChartWithTimeRange emits Loading then Success`() = runTest(dispatcher) {
        coEvery { remote.getCoinHistoricalChartWithTimeRange("bitcoin", "usd", 1_700_000_000, 1_700_086_400, null) } returns chartDto()

        val emissions = mutableListOf<AppResult<CoinHistoricalChart>>()
        val job = launch {
            repository.getCoinHistoricalChartWithTimeRange("bitcoin", "usd", 1_700_000_000, 1_700_086_400, null)
                .collect { emissions.add(it) }
        }
        advanceUntilIdle()

        assertTrue(emissions.first() is AppResult.Loading)
        assertTrue(emissions.last() is AppResult.Success)

        coVerify(exactly = 1) {
            remote.getCoinHistoricalChartWithTimeRange("bitcoin", "usd", 1_700_000_000, 1_700_086_400, null)
        }
        job.cancel()
    }

    @Test
    fun `getCoinHistoricalChartWithTimeRange emits Error on exception`() = runTest(dispatcher) {
        coEvery { remote.getCoinHistoricalChartWithTimeRange(any(), any(), any(), any(), any()) } throws RuntimeException("range fail")

        val emissions = mutableListOf<AppResult<CoinHistoricalChart>>()
        val job = launch {
            repository.getCoinHistoricalChartWithTimeRange("bitcoin", "usd", 1, 2, null).collect { emissions.add(it) }
        }
        advanceUntilIdle()

        assertTrue(emissions.first() is AppResult.Loading)
        assertTrue(emissions.last() is AppResult.Error)
        job.cancel()
    }

    // ---------- OHLC Candles ----------

    @Test
    fun `getCoinOHLCChartCandle emits Loading then Success`() = runTest(dispatcher) {
        coEvery { remote.getCoinOHLCChartCandle("bitcoin", "usd", "7", null) } returns ohlcDto()

        val emissions = mutableListOf<AppResult<CoinOHLCChartCandle>>()
        val job = launch {
            repository.getCoinOHLCChartCandle("bitcoin", "usd", "7", null).collect { emissions.add(it) }
        }
        advanceUntilIdle()

        assertTrue(emissions.first() is AppResult.Loading)
        assertTrue(emissions.last() is AppResult.Success)

        coVerify(exactly = 1) { remote.getCoinOHLCChartCandle("bitcoin", "usd", "7", null) }
        job.cancel()
    }

    @Test
    fun `getCoinOHLCChartCandle emits Error on exception`() = runTest(dispatcher) {
        coEvery { remote.getCoinOHLCChartCandle(any(), any(), any(), any()) } throws RuntimeException("ohlc fail")

        val emissions = mutableListOf<AppResult<CoinOHLCChartCandle>>()
        val job = launch {
            repository.getCoinOHLCChartCandle("bitcoin", "usd", "30", null).collect { emissions.add(it) }
        }
        advanceUntilIdle()

        assertTrue(emissions.first() is AppResult.Loading)
        assertTrue(emissions.last() is AppResult.Error)
        job.cancel()
    }
}
