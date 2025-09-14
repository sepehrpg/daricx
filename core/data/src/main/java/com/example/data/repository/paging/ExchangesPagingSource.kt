package com.example.data.repository.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.Exchange
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.model.toDomain

class ExchangesPagingSource(
    private val remote: ExchangesDataSource,
    private val perPage: Int = 50,
    private val page: Int = 1,
) : PagingSource<Int, Exchange>() {

    override fun getRefreshKey(state: PagingState<Int, Exchange>): Int? =
        state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Exchange> {
        return try {

            val dto = remote.getExchanges(
                page = page,
                perPage = perPage,
            )

            val data = dto.map { it.toDomain() }
            val nextKey = if (dto.isEmpty()) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)

        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}