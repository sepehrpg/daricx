package com.example.data.repository.exchanges

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.result.AppResult
import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.data.paging.ExchangeTickersPagingSource
import com.example.data.paging.ExchangesPagingSource
import com.example.model.exchanges.ExchangeDetail
import com.example.model.exchanges.ExchangeTickers
import com.example.model.exchanges.ExchangeVolumeChart
import com.example.model.exchanges.Exchanges
import com.example.model.sort.DexPairFormat
import com.example.model.sort.ExchangeTickersOrder
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.errors.toAppError
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.exchanges.toDomain
import com.example.network.options.toApiOrderParam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException


class ExchangesRepositoryImpl @Inject constructor(
    private val remoteDataSource: ExchangesDataSource,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : ExchangesRepository {

    override fun getExchangesPaged(
        pageSize: Int,
    ): Flow<PagingData<Exchanges>> {
        val config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
        return Pager(
            config = config,
            pagingSourceFactory = {
                ExchangesPagingSource(
                    remote = remoteDataSource,
                    perPage = pageSize,
                )
            }
        ).flow.flowOn(ioDispatcher)
    }

    override fun getExchangeById(
        id: String,
        dexPairFormat: DexPairFormat?
    ): Flow<AppResult<ExchangeDetail>> = resultFlow(
        api = {
            remoteDataSource.getExchangeById(
                id = id,
                dexPairFormat = dexPairFormat
            )
        },
        map = { it.toDomain() }
    )

    override fun getExchangeVolumeChart(
        id: String,
        days: String
    ): Flow<AppResult<ExchangeVolumeChart>> = resultFlow(
        api = { remoteDataSource.getExchangeVolumeChart(id = id, days = days) },
        map = { it.toDomain() }
    )


    override fun getExchangeTickersPaged(
        id: String,
        pageSize: Int,
        coinIds: String?,
        includeExchangeLogo: Boolean?,
        depth: Boolean?,
        dexPairFormat: DexPairFormat?,
        order: ExchangeTickersOrder?,
    ): Flow<PagingData<ExchangeTickers.Ticker>> {
        val config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
        return Pager(
            config = config,
            pagingSourceFactory = {
                ExchangeTickersPagingSource(
                    remote = remoteDataSource,
                    id = id,
                    coinIds = coinIds,
                    includeExchangeLogo = includeExchangeLogo,
                    depth = depth,
                    dexPairFormat = dexPairFormat,
                    order = order
                )
            }
        ).flow.flowOn(ioDispatcher)
    }




    private inline fun <T, R> resultFlow(
        noinline api: suspend () -> T,
        crossinline map: (T) -> R
    ): Flow<AppResult<R>> = flow {
        emit(AppResult.Loading)
        val dto = api()
        emit(AppResult.Success(map(dto)))
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(AppResult.Error(e.toAppError()))
    }.flowOn(ioDispatcher)
}