package com.example.data.exchanges


import androidx.paging.PagingSource
import com.example.data.paging.ExchangeTickersPagingSource
import com.example.model.exchanges.ExchangeTickers
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.model.exchanges.ExchangeTickersDto
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException


class ExchangeTickersPagingSourceTest {

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
    fun `load refresh returns mapped page with correct keys`() = runTest {
        // Given
        val remote = mockk<ExchangesDataSource>(relaxed = true)
        val dtoPage1 = pageDto(
            ticker("BTC", "USDT"),
            ticker("ETH", "USDT")
        )
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
        } returns dtoPage1

        val pagingSource = ExchangeTickersPagingSource(
            remote = remote,
            id = "binance",
            coinIds = null,
            includeExchangeLogo = true,
            depth = null,
            dexPairFormat = null,
            order = null
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
        val page = result as PagingSource.LoadResult.Page
        assertEquals(null, page.prevKey)
        assertEquals(2, page.nextKey)
        assertEquals(2, page.data.size)
        assertEquals("BTC", page.data[0].base)
        assertEquals("USDT", page.data[0].target)
        assertEquals("ETH", page.data[1].base)
    }

    @Test
    fun `load append empty returns nextKey null`() = runTest {
        val remote = mockk<ExchangesDataSource>(relaxed = true)
        val emptyDto = pageDto() // tickers = empty

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
        } returns emptyDto

        val pagingSource = ExchangeTickersPagingSource(
            remote = remote,
            id = "binance",
            coinIds = null,
            includeExchangeLogo = true,
            depth = null,
            dexPairFormat = null,
            order = null
        )

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
        assertEquals(1, page.prevKey)
    }

    @Test
    fun `load refresh error returns LoadResult_Error`() = runTest {
        val remote = mockk<ExchangesDataSource>(relaxed = true)
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

        val pagingSource = ExchangeTickersPagingSource(
            remote = remote,
            id = "binance",
            coinIds = null,
            includeExchangeLogo = true,
            depth = null,
            dexPairFormat = null,
            order = null
        )

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
