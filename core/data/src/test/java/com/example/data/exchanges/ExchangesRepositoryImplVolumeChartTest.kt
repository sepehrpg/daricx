package com.example.data.exchanges

import com.example.common.result.AppResult
import com.example.data.MainDispatcherRule
import com.example.data.repository.exchanges.ExchangesRepositoryImpl
import com.example.model.exchanges.ExchangeVolumeChart
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.model.exchanges.ExchangeVolumeChartDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ExchangesRepositoryImplVolumeChartTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private lateinit var remote: ExchangesDataSource
    private lateinit var repository: ExchangesRepositoryImpl

    @Before
    fun setup() {
        remote = mockk(relaxed = true)
        repository = ExchangesRepositoryImpl(
            remoteDataSource = remote,
            ioDispatcher = mainRule.testDispatcher
        )
    }

    private fun dto(vararg pts: Pair<Long, Double?>): ExchangeVolumeChartDto =
        ExchangeVolumeChartDto(
            points = pts.map { (ts, vol) ->
                ExchangeVolumeChartDto.Point(timestampMillis = ts, volume = vol)
            }
        )

    @Test
    fun `getExchangeVolumeChart - emits Loading then Success with mapped data`() = runTest {
        // Given
        coEvery { remote.getExchangeVolumeChart(id = "binance", days = "30") } returns
                dto(1L to 10.0, 2L to 20.5)

        // When
        val emissions = mutableListOf<AppResult<ExchangeVolumeChart>>()
        repository.getExchangeVolumeChart(id = "binance", days = "30").toList(emissions)

        // Then
        assertTrue(emissions[0] is AppResult.Loading)
        assertTrue(emissions[1] is AppResult.Success)

        val data = (emissions[1] as AppResult.Success).data
        assertEquals(2, data.points.size)
        assertEquals(1L, data.points[0].timestampMillis)
        assertEquals(10.0, data.points[0].volume!!, 0.0)
        assertEquals(20.5, data.points[1].volume!!, 0.0)

        coVerify(exactly = 1) { remote.getExchangeVolumeChart(id = "binance", days = "30") }
    }

    @Test
    fun `getExchangeVolumeChart - error maps to AppResult_Error`() = runTest {
        coEvery { remote.getExchangeVolumeChart(id = "kraken", days = "7") } throws IOException("boom")

        val emissions = mutableListOf<AppResult<ExchangeVolumeChart>>()
        repository.getExchangeVolumeChart(id = "kraken", days = "7").toList(emissions)

        assertTrue(emissions[0] is AppResult.Loading)
        assertTrue(emissions[1] is AppResult.Error)
    }

    @Test
    fun `getExchangeVolumeChart - CancellationException is not swallowed`() = runTest {
        coEvery { remote.getExchangeVolumeChart(id = "bybit", days = "1") } throws CancellationException("cancel")

        var thrown: Throwable? = null
        try {
            repository.getExchangeVolumeChart(id = "bybit", days = "1").toList(mutableListOf())
        } catch (e: Throwable) {
            thrown = e
        }
        assertTrue(thrown is CancellationException)
    }
}
