package com.example.data.exchanges


import androidx.paging.PagingSource
import com.example.data.paging.ExchangesPagingSource
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.model.exchanges.ExchangesDto
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException


/**
 * Test suite for [ExchangesPagingSource].
 *
 * 🎯 Goals:
 * - Verify that `load` returns correctly mapped domain data with proper prev/next keys.
 * - Ensure that when the remote returns an empty list, the nextKey is null and prevKey is preserved.
 * - Confirm that exceptions from the remote are propagated as [PagingSource.LoadResult.Error].
 *
 * 🧪 Scenarios:
 * 1) Refresh load with valid data → mapped items, prevKey = null, nextKey = 2.
 * 2) Append load with empty list → no items, nextKey = null, prevKey decremented.
 * 3) Refresh load with exception → [LoadResult.Error] returned.
 */
class ExchangesPagingSourceTest {

    private fun dto(id: String, name: String, score: Int = 10) = ExchangesDto(
        country = "US",
        description = "desc-$id",
        hasTradingIncentive = true,
        id = id,
        image = "https://ex/$id.png",
        name = name,
        tradeVolume24hBtc = 123.45,
        trustScore = score,
        trustScoreRank = 1,
        url = "https://$id.com",
        yearEstablished = 2017
    )

    @Test
    fun `load refresh returns mapped page with correct keys`() = runTest {
        val remote = mockk<ExchangesDataSource>(relaxed = true)
        coEvery { remote.getExchanges(page = 1, perPage = 2) } returns listOf(
            dto("binance", "Binance"),
            dto("kraken", "Kraken")
        )

        val pagingSource = ExchangesPagingSource(remote = remote, perPage = 2)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        val page = result as PagingSource.LoadResult.Page
        assertEquals(null, page.prevKey)     // page=1 → prevKey=null
        assertEquals(2, page.nextKey)        //  → nextKey=2
        assertEquals(2, page.data.size)
        assertEquals("binance", page.data[0].id)
        assertEquals("Kraken", page.data[1].name)
    }

    @Test
    fun `load append empty returns nextKey null`() = runTest {
        val remote = mockk<ExchangesDataSource>(relaxed = true)
        coEvery { remote.getExchanges(page = 2, perPage = 2) } returns emptyList()

        val pagingSource = ExchangesPagingSource(remote = remote, perPage = 2)

        val result = pagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        val page = result as PagingSource.LoadResult.Page
        assertTrue(page.data.isEmpty())
        assertEquals(null, page.nextKey)
        assertEquals(1, page.prevKey)        // prevKey=1
    }

    @Test
    fun `load refresh error returns LoadResult_Error`() = runTest {
        val remote = mockk<ExchangesDataSource>(relaxed = true)
        coEvery { remote.getExchanges(page = 1, perPage = 2) } throws IOException("boom")

        val pagingSource = ExchangesPagingSource(remote = remote, perPage = 2)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Error)
    }
}
