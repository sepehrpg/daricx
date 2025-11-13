package com.example.data.nfts.m2


import androidx.paging.PagingSource
import com.example.data.paging.NftsPagingSource
import com.example.model.sort.NftsSort
import com.example.network.datasource.nfts.NftsDataSource
import com.example.network.model.nfts.NftsDto
import com.example.network.model.nfts.NftsListDto
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

@OptIn(ExperimentalCoroutinesApi::class)
class NftsPagingSource_MockStyle_Test {

    private lateinit var remote: NftsDataSource
    private lateinit var pagingSource: NftsPagingSource
    private val dispatcher = StandardTestDispatcher()

    private fun dto(id: String) = NftsDto(
        assetPlatformId = "ethereum",
        contractAddress = "0x$id",
        id = id,
        name = "NFT $id",
        symbol = "SYM$id"
    )

    @Before fun setup() { remote = mockk(relaxed = true) }

    @Test
    fun `load page=1 success`() = runTest(dispatcher) {
        val page1: NftsListDto = listOf(dto("1"), dto("2"))
        coEvery { remote.getNftsList(page = 1, perPage = 2, order = NftsSort.MarketCapUsdDesc) } returns page1

        pagingSource = NftsPagingSource(remote, perPage = 2, order = NftsSort.MarketCapUsdDesc)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 2, placeholdersEnabled = false)
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page
        assertEquals(null, result.prevKey)
        assertEquals(2, result.nextKey)
        assertEquals(listOf("1", "2"), result.data.map { it.id })

        coVerify(exactly = 1) { remote.getNftsList(1, 2, NftsSort.MarketCapUsdDesc) }
    }

    @Test
    fun `load error`() = runTest(dispatcher) {
        coEvery { remote.getNftsList(any(), any(), any()) } throws IOException("boom")
        pagingSource = NftsPagingSource(remote)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 50, placeholdersEnabled = false)
        )
        assertTrue(result is PagingSource.LoadResult.Error)
    }
}
