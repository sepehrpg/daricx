package com.example.data.exchanges


import com.example.common.result.AppResult
import com.example.data.MainDispatcherRule
import com.example.data.repository.exchanges.ExchangesRepositoryImpl
import com.example.model.exchanges.ExchangeDetail
import com.example.model.sort.DexPairFormat
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.model.exchanges.ExchangeDetailDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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
class ExchangesRepositoryImplGetByIdTest {

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

    private fun dto(
        name: String = "Binance",
        url: String = "https://www.binance.com",
        trustScoreRank: Int? = 1
    ): ExchangeDetailDto {
        val d = mockk<ExchangeDetailDto>(relaxed = true)
        every { d.name } returns name
        every { d.url } returns url
        every { d.trustScoreRank } returns trustScoreRank
        return d
    }

    @Test
    fun `getExchangeById - emits Loading then Success and forwards params`() = runTest {
        // Arrange
        val dex = mockk<DexPairFormat>(relaxed = true)
        coEvery { remote.getExchangeById(id = "binance", dexPairFormat = dex) } returns dto(
            name = "Binance",
            url = "https://www.binance.com",
            trustScoreRank = 1
        )

        // Act
        val emissions = mutableListOf<AppResult<ExchangeDetail>>()
        repository.getExchangeById(id = "binance", dexPairFormat = dex).toList(emissions)

        // Assert
        assertTrue(emissions[0] is AppResult.Loading)
        assertTrue(emissions[1] is AppResult.Success)

        val data = (emissions[1] as AppResult.Success).data
        assertEquals("Binance", data.name)
        assertEquals("https://www.binance.com", data.url)
        assertEquals(1, data.trustScoreRank)

        coVerify(exactly = 1) { remote.getExchangeById(id = "binance", dexPairFormat = dex) }
    }

    @Test
    fun `getExchangeById - maps exception to AppResult_Error`() = runTest {
        val dex = mockk<DexPairFormat>(relaxed = true)
        coEvery { remote.getExchangeById(id = "kraken", dexPairFormat = dex) } throws IOException("boom")

        val emissions = mutableListOf<AppResult<ExchangeDetail>>()
        repository.getExchangeById(id = "kraken", dexPairFormat = dex).toList(emissions)

        assertTrue(emissions[0] is AppResult.Loading)
        assertTrue(emissions[1] is AppResult.Error)
    }

    @Test
    fun `getExchangeById - CancellationException is rethrown`() = runTest {
        val dex = mockk<DexPairFormat>(relaxed = true)
        coEvery { remote.getExchangeById(id = "bybit", dexPairFormat = dex) } throws CancellationException("cancel")

        var thrown: Throwable? = null
        try {
            repository.getExchangeById(id = "bybit", dexPairFormat = dex).toList(mutableListOf())
        } catch (e: Throwable) {
            thrown = e
        }
        assertTrue(thrown is CancellationException)
    }
}
