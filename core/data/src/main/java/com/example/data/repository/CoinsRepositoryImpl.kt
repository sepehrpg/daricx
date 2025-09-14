package com.example.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.data.repository.paging.CoinMarketsPagingSource
import com.example.model.CoinMarket
import com.example.network.datasource.coins.CoinsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CoinsRepositoryImpl @Inject constructor(
    private val remoteDataSource: CoinsDataSource,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : CoinsRepository {

    override fun getCoinMarketsPaged(
        vsCurrency: String,
        pageSize: Int,
        order: String?,
        sparkline: Boolean?,
        priceChangePercentage: String?,
        pageTransform: ((List<CoinMarket>) -> List<CoinMarket>)?
    ): Flow<androidx.paging.PagingData<CoinMarket>> {
        val config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize ,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
        return Pager(
            config = config,
            pagingSourceFactory = {
                CoinMarketsPagingSource(
                    remote = remoteDataSource,
                    vsCurrency = vsCurrency,
                    perPage = pageSize,
                    order = order,
                    sparkline = sparkline,
                    priceChangePercentage = priceChangePercentage,
                    pageTransform = pageTransform
                )
            }
        ).flow.flowOn(ioDispatcher)
    }
}