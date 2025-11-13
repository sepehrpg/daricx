package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.nfts.Nfts
import com.example.model.sort.NftsSort
import com.example.network.datasource.nfts.NftsDataSource
import com.example.network.errors.toPagingException
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.nfts.toDomain
import kotlin.coroutines.cancellation.CancellationException

/**
 * PagingSource for /nfts/list.
 */
class NftsPagingSource(
    private val remote: NftsDataSource,
    private val perPage: Int = 50,
    private val order: NftsSort? = null,
    /** Optional transform to reorder items within each loaded page (rarely needed). */
    private val pageTransform: ((List<Nfts>) -> List<Nfts>)? = null
) : PagingSource<Int, Nfts>() {

    override fun getRefreshKey(state: PagingState<Int, Nfts>): Int? =
        state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Nfts> {
        val page = params.key ?: 1
        return try {
            val dto = remote.getNftsList(
                page = page,
                perPage = perPage,
                order = order
            )

            var data = dto.map { it.toDomain() }
            if (pageTransform != null && data.isNotEmpty()) {
                data = pageTransform.invoke(data)
            }

            val nextKey = if (dto.isEmpty()) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)
        } catch (t: Throwable) {
            // Don't swallow coroutine cancellation
            if (t is CancellationException) throw t
            // Uniform error surface for Paging UI
            LoadResult.Error(t.toPagingException())
        }
    }
}
