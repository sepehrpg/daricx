package com.example.data.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.exchanges.ExchangeTickers
import com.example.model.sort.DexPairFormat
import com.example.model.sort.ExchangeTickersOrder
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.errors.toPagingException
import com.example.network.model.mappers.exchanges.toDomain
import kotlin.coroutines.cancellation.CancellationException


class ExchangeTickersPagingSource(
    private val remote: ExchangesDataSource,
    private val id: String,
    private val coinIds: String?,
    private val includeExchangeLogo: Boolean?,
    private val depth: Boolean?,
    private val dexPairFormat: DexPairFormat?,
    private val order: ExchangeTickersOrder?,
) : PagingSource<Int, ExchangeTickers.Ticker>() {

    override fun getRefreshKey(state: PagingState<Int, ExchangeTickers.Ticker>): Int? =
        state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ExchangeTickers.Ticker> {
        return try {
            val page = params.key ?: 1

            val dto = remote.getExchangeTickersById(
                id = id,
                coinIds = coinIds,
                includeExchangeLogo = includeExchangeLogo,
                depth = depth,
                dexPairFormat = dexPairFormat,
                page = page,
                order = order
            )

            val domain = dto.toDomain()
            val data = domain.tickers?.filterNotNull().orEmpty()

            val nextKey = if (data.isEmpty()) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (t: Throwable) {
            if (t is CancellationException) throw t
            LoadResult.Error(t.toPagingException())
        }
    }
}
