package com.example.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.model.CoinMarket
import com.example.network.datasource.RemoteCoinsDataSource
import com.example.network.model.toDomain

class CoinMarketsPagingSource(
    private val remote: RemoteCoinsDataSource,
    private val vsCurrency: String,
    private val perPage: Int = 50 ,
    private val order: String? =null,
    private val sparkline: Boolean? = true,
    private val priceChangePercentage: String? = null,
    private val locale: String? = null,
    private val precision: String? = null,
    /** Optional transform that reorders items **within the loaded page** (used for unsupported sorts) */
    private val pageTransform: ((List<CoinMarket>) -> List<CoinMarket>)? = null
) : PagingSource<Int, CoinMarket>() {

    override fun getRefreshKey(state: PagingState<Int, CoinMarket>): Int? =
        state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinMarket> {
        val page = params.key ?: 1
        return try {
            val dto = remote.getCoinMarkets(
                vsCurrency = vsCurrency,
                page = page,
                perPage = perPage,
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
            LoadResult.Error(t)
        }
    }
}