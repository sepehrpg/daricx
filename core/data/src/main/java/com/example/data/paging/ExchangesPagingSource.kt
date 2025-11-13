package com.example.data.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.exchanges.Exchanges
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.errors.toPagingException
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.exchanges.toDomain
import kotlin.coroutines.cancellation.CancellationException

class ExchangesPagingSource(
    private val remote: ExchangesDataSource,
    private val perPage: Int = 50,
) : PagingSource<Int, Exchanges>() {

    override fun getRefreshKey(state: PagingState<Int, Exchanges>): Int? =
        state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Exchanges> {
        return try {
            val page = params.key ?: 1

            val dto = remote.getExchanges(
                page = page,
                perPage = perPage,
            )

            val data = dto.map { it.toDomain() }
            val nextKey = if (dto.isEmpty()) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)

        }  catch (t: Throwable) {
            // Don't swallow coroutine cancellation
            if (t is CancellationException) throw t
            // Uniform error surface for Paging UI
            LoadResult.Error(t.toPagingException())
        }
    }
}