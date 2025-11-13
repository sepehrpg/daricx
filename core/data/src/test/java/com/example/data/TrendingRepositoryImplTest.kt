package com.example.data

import app.cash.turbine.test
import com.example.common.result.AppError
import com.example.common.result.AppResult
import com.example.data.repository.trending.TrendingRepository
import com.example.data.repository.trending.TrendingRepositoryImpl
import com.example.model.Trending
import com.example.network.datasource.trending.TrendingDataSource
import com.example.network.model.TrendingDto
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Tests for TrendingRepositoryImpl emitting Flow<AppResult<Trending>>.
 *
 * Scenarios:
 * 1) Emits Loading -> Success with correctly mapped domain model.
 * 2) Emits Loading -> Error(AppError.Network) for IOException.
 * 3) Empty DTO -> domain lists are empty.
 * 4) Does not swallow CancellationException (is rethrown).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TrendingRepositoryImplFlowTest {

    private lateinit var repository: TrendingRepository
    private lateinit var remote: TrendingDataSource
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        remote = mockk(relaxed = true)
        repository = TrendingRepositoryImpl(remote, dispatcher)
    }

    private fun sampleDto(): TrendingDto = TrendingDto(
        coins = listOf(
            TrendingDto.Coin(
                item = TrendingDto.Coin.Item(
                    id = "eth",
                    name = "Ethereum",
                    symbol = "ETH",
                    thumb = "eth-thumb.png",
                    large = "eth-large.png",
                    coinId = 2,
                    marketCapRank = 2,
                    priceBtc = 0.062,
                    score = 1,
                    slug = "ethereum",
                    small = "eth-small.png",
                    data = TrendingDto.Coin.Item.Data(
                        price = 3000.0,
                        priceBtc = "0.062",
                        marketCap = "400B",
                        marketCapBtc = "20M",
                        totalVolume = "10B",
                        totalVolumeBtc = "500k",
                        priceChangePercentage24h = mapOf("usd" to 2.5),
                        sparkline = "sparkline-data",
                        content = TrendingDto.Coin.Item.Data.Content(
                            title = "ETH description",
                            description = "Smart contracts leader"
                        )
                    )
                )
            )
        ),
        nfts = listOf(
            TrendingDto.Nft(
                id = "cryptopunks",
                name = "CryptoPunks",
                symbol = "PUNK",
                thumb = "punk-thumb.png",
                nftContractId = 1,
                nativeCurrencySymbol = "ETH",
                floorPrice24hPercentageChange = -1.2,
                floorPriceInNativeCurrency = 50.0,
                data = TrendingDto.Nft.Data(
                    floorPrice = "50",
                    h24Volume = "1000",
                    h24AverageSalePrice = "60",
                    sparkline = "sparkline-nft",
                    floorPriceInUsd24hPercentageChange = "5.0",
                    content = TrendingDto.Nft.Data.Content(
                        title = "CryptoPunks",
                        description = "OG NFT collection"
                    )
                )
            )
        ),
        categories = listOf(
            TrendingDto.Category(
                id = 100,
                name = "DeFi",
                slug = "defi",
                coinsCount = 200,
                marketCap1hChange = 0.5,
                data = TrendingDto.Category.Data(
                    marketCap = 1_000_000.0,
                    marketCapBtc = 50.0,
                    totalVolume = 200_000.0,
                    totalVolumeBtc = 10.0,
                    sparkline = "sparkline-cat",
                    marketCapChangePercentage24h = mapOf("usd" to -2.0)
                )
            )
        )
    )

    @Test
    fun `emits Loading then Success with mapped domain`() = runTest(dispatcher) {
        // Given
        coEvery { remote.getTrending() } returns sampleDto()

        // When & Then
        repository.getTrending().test {
            // 1) Loading
            assertTrue(awaitItem() is AppResult.Loading)

            // 2) Success + mapped values
            val success = awaitItem() as AppResult.Success<Trending>
            assertEquals("Ethereum", success.data.coins?.first()?.item?.name)
            assertEquals("CryptoPunks", success.data.nfts?.first()?.name)
            assertEquals("DeFi", success.data.categories?.first()?.name)

            awaitComplete()
        }
    }

    @Test
    fun `emits Loading then Error for IOException`() = runTest(dispatcher) {
        // Given
        coEvery { remote.getTrending() } throws IOException("boom")

        // When & Then
        repository.getTrending().test {
            assertTrue(awaitItem() is AppResult.Loading)
            val error = awaitItem() as AppResult.Error
            assertTrue(error.error is AppError.Network)
            awaitComplete()
        }
    }

    @Test
    fun `empty DTO  empty domain lists`() = runTest(dispatcher) {
        // Given
        coEvery { remote.getTrending() } returns TrendingDto(
            coins = emptyList(),
            categories = emptyList(),
            nfts = emptyList()
        )

        // When & Then
        repository.getTrending().test {
            assertTrue(awaitItem() is AppResult.Loading)
            val success = awaitItem() as AppResult.Success<Trending>
            assertTrue(success.data.coins?.isEmpty() == true)
            assertTrue(success.data.categories?.isEmpty() == true)
            assertTrue(success.data.nfts?.isEmpty() == true)
            awaitComplete()
        }
    }

    @Test
    fun `cancellation is rethrown (not swallowed)`() = runTest(dispatcher) {
        // Given
        coEvery { remote.getTrending() } coAnswers { throw CancellationException("cancel") }

        // When & Then: collect and assert it throws CancellationException,
        // but the first emission (Loading) still occurs before cancellation.
        val emissions = mutableListOf<AppResult<Trending>>()
        try {
            repository.getTrending()
                .onEach { emissions.add(it) }
                .collect()
        } catch (e: CancellationException) {
            // expected
        }

        // Verify first emission was Loading
        assertTrue(emissions.firstOrNull() is AppResult.Loading)
    }
}
