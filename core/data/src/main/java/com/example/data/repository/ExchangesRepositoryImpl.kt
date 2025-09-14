package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.data.repository.paging.ExchangesPagingSource
import com.example.model.Exchange
import com.example.network.datasource.exchanges.ExchangesDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ExchangesRepositoryImpl @Inject constructor(
    private val remoteDataSource: ExchangesDataSource,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : ExchangesRepository {

    override fun getExchangesPaged(
        pageSize: Int,
        page: Int,
    ): Flow<PagingData<Exchange>> {
        val config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize ,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
        return Pager(
            config = config,
            pagingSourceFactory = {
                ExchangesPagingSource(
                    remote = remoteDataSource,
                    perPage = pageSize,
                    page = page
                )
            }
        ).flow.flowOn(ioDispatcher)
    }

}