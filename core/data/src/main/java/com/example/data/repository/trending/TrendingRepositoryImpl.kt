package com.example.data.repository.trending


import com.example.common.result.AppResult
import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.network.errors.toAppError
import com.example.model.Trending
import com.example.network.datasource.trending.TrendingDataSource
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * Network-backed implementation of [TrendingRepository].
 */
class TrendingRepositoryImpl @Inject constructor(
    private val remoteDataSource: TrendingDataSource,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) : TrendingRepository {

    override fun getTrending(): Flow<AppResult<Trending>> = flow {
        emit(AppResult.Loading)

        val dto = remoteDataSource.getTrending()
        emit(AppResult.Success(dto.toDomain()))
    }
        .catch { e ->
            // Don't swallow cancellation
            if (e is CancellationException) throw e
            emit(AppResult.Error(e.toAppError()))
        }
        .flowOn(ioDispatcher)

}
