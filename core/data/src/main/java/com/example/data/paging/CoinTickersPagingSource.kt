package com.example.data.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.coins.CoinTickers
import com.example.model.sort.CoinTickersOrder
import com.example.model.sort.DexPairFormat
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.errors.toPagingException
import com.example.network.model.mappers.coins.toDomain
import kotlin.coroutines.cancellation.CancellationException

class CoinTickersPagingSource(
    private val remote: CoinsDataSource,
    private val id: String,
    private val exchangeIds: String? = null,
    private val includeExchangeLogo: Boolean? = true,
    private val depth: Boolean? = null,
    private val dexPairFormat: DexPairFormat? = null,
    private val order: CoinTickersOrder? = null,
    private val pageTransform: ((List<CoinTickers.Ticker>) -> List<CoinTickers.Ticker>)? = null
) : PagingSource<Int, CoinTickers.Ticker>() {

    override fun getRefreshKey(state: PagingState<Int, CoinTickers.Ticker>): Int? =
        state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinTickers.Ticker> {
        val page = params.key ?: 1
        return try {
            val dto = remote.getCoinTickersById(
                id = id,
                exchangeIds = exchangeIds,
                includeExchangeLogo = includeExchangeLogo,
                depth = depth,
                dexPairFormat = dexPairFormat,
                page = page,
                order = order
            )
            var data = dto.toDomain().tickers.orEmpty().filterNotNull()
            if (pageTransform != null && data.isNotEmpty()) {
                data = pageTransform.invoke(data)
            }

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
