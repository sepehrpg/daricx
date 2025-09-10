package com.example.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.repository.paging.CoinMarketsPagingSource
import com.example.model.CoinMarket
import com.example.network.datasource.RemoteCoinsDataSource
import com.example.network.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject


class CoinRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteCoinsDataSource
) : CoinRepository {

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
        ).flow
    }
}