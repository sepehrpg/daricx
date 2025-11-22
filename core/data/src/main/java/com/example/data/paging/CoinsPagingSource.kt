package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.coins.Coins
import com.example.model.sort.CoinsSort
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.errors.toPagingException
import com.example.network.model.mappers.coins.toDomain
import kotlin.coroutines.cancellation.CancellationException


class CoinsPagingSource(
    private val remote: CoinsDataSource,
    private val vsCurrency: String,
    private val ids: String? = null,
    private val perPage: Int = 50,
    private val order: CoinsSort? = null,
    private val sparkline: Boolean? = true,
    private val priceChangePercentage: String? = null,
    private val locale: String? = null,
    private val precision: String? = null,
    /** Optional transform that reorders items **within the loaded page** (used for unsupported sorts) */
    private val pageTransform: ((List<Coins>) -> List<Coins>)? = null
) : PagingSource<Int, Coins>() {

    override fun getRefreshKey(state: PagingState<Int, Coins>): Int? =
        state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coins> {
        val page = params.key ?: 1
        return try {
            val dto = remote.getCoinMarkets(
                vsCurrency = vsCurrency,
                page = page,
                perPage = perPage,
                ids = ids,
                order = order,
                sparkline = sparkline,
                priceChangePercentage = priceChangePercentage,
                locale = locale,
                precision = precision
            )
            var data = dto.toDomain()
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
