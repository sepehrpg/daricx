package com.example.data.coins


import com.example.common.result.AppResult
import com.example.data.repository.coins.CoinsRepositoryImpl
import com.example.model.coins.CoinDetails
import com.example.model.sort.DexPairFormat
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.model.coins.CoinDetailsDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
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
class CoinsRepositoryGetCoinDetailTest {

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

    /**
     * Helper: create a minimal but safe CoinDto with common numeric maps set.
     * This tries to cover the typical fields used by mappers (currentPrice, marketCap, totalVolume).
     */
    private fun makeCoinDto(id: String, name: String, usdPrice: Double): CoinDetailsDto {
        val currentPrice = CoinDetailsDto.MarketData.CurrentPrice(prices = mapOf("usd" to usdPrice))
        val marketCap = CoinDetailsDto.MarketData.MarketCap(caps = mapOf("usd" to 1_000_000.0))
        val totalVolume = CoinDetailsDto.MarketData.TotalVolume(volumes = mapOf("usd" to 123_456.0))

        val marketData = CoinDetailsDto.MarketData(
            currentPrice = currentPrice,
            marketCap = marketCap,
            totalVolume = totalVolume,
            // other fields left as defaults (null / empty maps) which should be safe
        )

        return CoinDetailsDto(
            id = id,
            name = name,
            symbol = id.uppercase(),
            marketData = marketData
            // other top-level fields use defaults defined in data class
        )
    }

    @Test
    fun `getCoinDetail emits Loading then Success and maps id`() = runTest {
        val requestedId = "bitcoin"
        val sampleDto = makeCoinDto(requestedId, "Bitcoin", 65000.0)

        coEvery {
            remote.getCoinDetail(
                id = requestedId,
                localization = true,
                tickers = true,
                marketData = true,
                communityData = true,
                developerData = true,
                sparkline = false,
                dexPairFormat = DexPairFormat.CONTRACT_ADDRESS
            )
        } returns sampleDto

        val emissions = mutableListOf<AppResult<CoinDetails>>()

        val job = launch {
            // repository function is suspend -> Flow<AppResult<...>>
            repository.getCoinDetail(
                id = requestedId,
                localization = true,
                tickers = true,
                marketData = true,
                communityData = true,
                developerData = true,
                sparkline = false,
                dexPairFormat = DexPairFormat.CONTRACT_ADDRESS
            ).collect { emissions.add(it) }
        }

        // let the flow execute
        advanceUntilIdle()

        // Verify emissions sequence: Loading, then Success
        assertTrue(emissions.isNotEmpty())
        assertTrue(emissions[0] is AppResult.Loading)
        assertTrue(emissions.size >= 2 && emissions[1] is AppResult.Success)

        // Check mapped domain id (assumes toDomain maps dto.id -> CoinDetails.id)
        val success = emissions[1] as AppResult.Success
        assertEquals(requestedId, success.data.id)

        // verify remote called with expected params
        coVerify(atLeast = 1) {
            remote.getCoinDetail(
                id = requestedId,
                localization = true,
                tickers = true,
                marketData = true,
                communityData = true,
                developerData = true,
                sparkline = false,
                dexPairFormat = DexPairFormat.CONTRACT_ADDRESS
            )
        }

        job.cancel()
    }

    @Test
    fun `getCoinDetail emits Loading then Error when remote throws`() = runTest {
        val requestedId = "ethereum"
        val ex = RuntimeException("network failure")

        coEvery {
            remote.getCoinDetail(
                id = requestedId,
                localization = any(),
                tickers = any(),
                marketData = any(),
                communityData = any(),
                developerData = any(),
                sparkline = any(),
                dexPairFormat = any()
            )
        } throws ex

        val emissions = mutableListOf<AppResult<CoinDetails>>()

        val job = launch {
            repository.getCoinDetail(
                id = requestedId,
                localization = true,
                tickers = true,
                marketData = true,
                communityData = true,
                developerData = true,
                sparkline = false,
                dexPairFormat = DexPairFormat.SYMBOL
            ).collect { emissions.add(it) }
        }

        advanceUntilIdle()

        // Expect Loading then Error
        assertTrue(emissions.isNotEmpty())
        assertTrue(emissions[0] is AppResult.Loading)
        assertTrue(emissions.size >= 2 && emissions[1] is AppResult.Error)

        job.cancel()
    }
}
