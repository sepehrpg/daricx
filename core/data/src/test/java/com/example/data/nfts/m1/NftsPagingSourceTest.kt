package com.example.data.nfts.m1

import androidx.paging.PagingConfig
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.example.data.paging.NftsPagingSource
import com.example.model.nfts.Nfts
import com.example.model.sort.NftsSort
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

/**
 * Unit Tests for [NftsPagingSource].
 *
 * Test Goal:
 * - Validate page loading, key management, error propagation, and page transforms.
 *
 * Scenarios Covered:
 * 1) First page success -> returns data, prevKey = null, nextKey = 2.
 * 2) Empty page -> nextKey = null, prevKey reflects previous page.
 * 3) Remote error -> propagates as [LoadResult.Error].
 * 4) pageTransform applied on the list prior to emission.
 * 5) getRefreshKey derived from anchor position and closest page keys.
 *
 * Notes on Prefetch:
 * - These tests call PagingSource directly (bypassing Pager). PrefetchDistance is irrelevant here,
 *   so results are deterministic and not impacted by UI scroll positions.
 */
class NftsPagingSourceTest {

    @Test
    fun `first page success returns Page with nextKey=2 prevKey=null`() = runTest {
        val fake = FakeNftsDataSource(
            pages = mapOf(1 to listOf(nftsDto("1"), nftsDto("2")))
        )
        val ps = NftsPagingSource(remote = fake, perPage = 2, order = null)

        val result = ps.load(LoadParams.Refresh(key = null, loadSize = 2, placeholdersEnabled = false))

        val page = result as LoadResult.Page
        assertThat(page.data.map { it.id }).containsExactly("1", "2").inOrder()
        assertThat(page.prevKey).isNull()
        assertThat(page.nextKey).isEqualTo(2)
    }

    @Test
    fun `empty page returns nextKey=null and proper prevKey`() = runTest {
        val fake = FakeNftsDataSource(pages = mapOf(2 to emptyList()))
        val ps = NftsPagingSource(remote = fake, perPage = 50, order = NftsSort.MarketCapUsdDesc)

        val result = ps.load(LoadParams.Append(key = 2, loadSize = 50, placeholdersEnabled = false))

        val page = result as LoadResult.Page
        assertThat(page.data).isEmpty()
        assertThat(page.nextKey).isNull()
        assertThat(page.prevKey).isEqualTo(1)
    }

    @Test
    fun `error from remote is propagated`() = runTest {
        val fake = FakeNftsDataSource(pages = emptyMap(), throwOnPage = 1)
        val ps = NftsPagingSource(remote = fake, perPage = 10)

        val result = ps.load(LoadParams.Refresh(key = null, loadSize = 10, placeholdersEnabled = false))

        assertThat(result is LoadResult.Error).isTrue()
        val error = result as LoadResult.Error
        assertThat(error.throwable).isInstanceOf(IOException::class.java)
    }

    @Test
    fun `pageTransform is applied`() = runTest {
        val fake = FakeNftsDataSource(
            pages = mapOf(1 to listOf(nftsDto("1"), nftsDto("2"), nftsDto("3")))
        )
        val reverse: (List<Nfts>) -> List<Nfts> = { it.asReversed() }
        val ps = NftsPagingSource(remote = fake, perPage = 3, pageTransform = reverse)

        val result = ps.load(
            LoadParams.Refresh(key = null, loadSize = 3, placeholdersEnabled = false)
        ) as LoadResult.Page

        assertThat(result.data.map { it.id }).containsExactly("3", "2", "1").inOrder()
    }

    @Test
    fun `getRefreshKey derives from closest page`() {
        val page1 = LoadResult.Page(
            data = listOf(Nfts(assetPlatformId = null, contractAddress = null, id = "1", name = "N1", symbol = "S1")),
            prevKey = null,
            nextKey = 2
        )
        val page2 = LoadResult.Page(
            data = listOf(Nfts(assetPlatformId = null, contractAddress = null, id = "2", name = "N2", symbol = "S2")),
            prevKey = 1,
            nextKey = 3
        )
        val state = PagingState(
            pages = listOf(page1, page2),
            anchorPosition = 1, // somewhere within the combined list
            config = PagingConfig(pageSize = 1),
            leadingPlaceholderCount = 0
        )

        val key = NftsPagingSource(remote = FakeNftsDataSource(emptyMap())).getRefreshKey(state)
        // For anchor inside page2, prevKey + 1 = 2
        assertThat(key).isEqualTo(2)
    }
}
