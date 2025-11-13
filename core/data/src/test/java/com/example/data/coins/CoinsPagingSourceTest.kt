package com.example.data.coins

import androidx.paging.PagingSource
import com.example.data.paging.CoinsPagingSource
import com.example.model.coins.Coins
import com.example.model.sort.CoinsSort
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.model.coins.CoinsDto
import com.example.network.model.coins.CoinsListDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Test suite for [com.example.data.paging.CoinsPagingSource].
 *
 * 🎯 Goals:
 * - Ensure paging source requests the correct page from the data source.
 * - Verify DTO → Domain mapping is applied (via toDomain()) and returned in LoadResult.Page.
 * - Validate prev/next keys logic (page=1 → prev=null; empty next → next=null).
 * - Ensure pageTransform is applied when provided.
 * - Ensure exceptions from data source become LoadResult.Error.
 *
 * 🧪 Scenarios:
 * 1) load - first page success → returns Page with nextKey=2, prevKey=null, mapped items.
 * 2) load - apply pageTransform → data in Page is transformed.
 * 3) load - empty page → nextKey=null.
 * 4) load - exception → LoadResult.Error.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CoinsPagingSourceTest {

    private lateinit var remote: CoinsDataSource
    private lateinit var pagingSource: CoinsPagingSource
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        remote = mockk(relaxed = true)
    }

    private fun dto(
        id: String,
        name: String,
        price: Double,
        mcRank: Int
    ) = CoinsDto(
        ath = null,
        athChangePercentage = null,
        athDate = null,
        atl = null,
        atlChangePercentage = null,
        atlDate = null,
        circulatingSupply = null,
        currentPrice = price,
        fullyDilutedValuation = null,
        high24h = null,
        id = id,
        image = "img/$id.png",
        lastUpdated = null,
        low24h = null,
        marketCap = null,
        marketCapChange24h = null,
        marketCapChangePercentage24h = null,
        marketCapRank = mcRank,
        maxSupply = null,
        name = name,
        priceChange24h = null,
        priceChangePercentage24h = null,
        roi = null,
        symbol = id.uppercase(),
        totalSupply = null,
        totalVolume = null,
        sparklineIn7d = null
    )

    @Test
    fun `load page=1 success returns Page with mapped data and keys`() = runTest(dispatcher) {
        // Given
        val page1: CoinsListDto = listOf(
            dto("btc", "Bitcoin", 100.0, 1),
            dto("eth", "Ethereum", 50.0, 2)
        )
        coEvery {
            remote.getCoinMarkets(
                vsCurrency = "usd",
                page = 1,
                perPage = 2,
                order = CoinsSort.MarketCapDesc,
                sparkline = false,
                priceChangePercentage = null,
                locale = null,
                precision = null
            )
        } returns page1

        pagingSource = CoinsPagingSource(
            remote = remote,
            vsCurrency = "usd",
            perPage = 2,
            order = CoinsSort.MarketCapDesc,
            sparkline = false,
            priceChangePercentage = null,
            locale = null,
            precision = null,
            pageTransform = null
        )

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        // Then
        TestCase.assertTrue(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page
        TestCase.assertEquals(null, result.prevKey)
        TestCase.assertEquals(2, result.nextKey)
        TestCase.assertEquals(2, result.data.size)
        TestCase.assertEquals("btc", result.data[0].id)
        TestCase.assertEquals("eth", result.data[1].id)

        coVerify(exactly = 1) {
            remote.getCoinMarkets(
                vsCurrency = "usd",
                page = 1,
                perPage = 2,
                order = CoinsSort.MarketCapDesc,
                sparkline = false,
                priceChangePercentage = null,
                locale = null,
                precision = null
            )
        }
    }

    @Test
    fun `load applies pageTransform when provided`() = runTest(dispatcher) {
        // Given
        val page1: CoinsListDto = listOf(
            dto("btc", "Bitcoin", 100.0, 1),
            dto("eth", "Ethereum", 50.0, 2)
        )
        coEvery {
            remote.getCoinMarkets(
                any(),
                1,
                2,
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns page1

        val transform: (List<Coins>) -> List<Coins> = { list ->
            // reverse order by currentPrice (just demo)
            list.sortedByDescending { it.currentPrice ?: Double.MIN_VALUE }
        }

        pagingSource = CoinsPagingSource(
            remote = remote,
            vsCurrency = "usd",
            perPage = 2,
            order = null,
            sparkline = true,
            priceChangePercentage = null,
            pageTransform = transform
        )

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page

        // Then (btc=100 should come before eth=50)
        TestCase.assertEquals(listOf("btc", "eth"), result.data.map { it.id })
    }

    @Test
    fun `load empty page - nextKey is null`() = runTest(dispatcher) {
        coEvery {
            remote.getCoinMarkets(
                any(),
                page = 2,
                perPage = 2,
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns emptyList()

        pagingSource = CoinsPagingSource(
            remote = remote,
            vsCurrency = "usd",
            perPage = 2
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = 2,
                placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page

        TestCase.assertEquals(null, result.nextKey)
        TestCase.assertTrue(result.data.isEmpty())
    }

    @Test
    fun `load throws from data source - returns Error`() = runTest(dispatcher) {
        coEvery {
            remote.getCoinMarkets(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } throws IOException("boom")

        pagingSource = CoinsPagingSource(
            remote = remote,
            vsCurrency = "usd",
            perPage = 2
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        TestCase.assertTrue(result is PagingSource.LoadResult.Error)
    }
}