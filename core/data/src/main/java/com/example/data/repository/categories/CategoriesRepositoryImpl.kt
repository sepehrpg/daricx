package com.example.data.repository.categories


import com.example.common.result.AppResult
import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.model.Categories
import com.example.model.sort.CategoriesSort
import com.example.network.datasource.categories.CategoriesDataSource
import com.example.network.errors.toAppError
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Single
import kotlin.coroutines.cancellation.CancellationException

/**
 * Network-backed implementation of [CategoriesRepository].
 */
@Single(binds = [CategoriesRepository::class])
class CategoriesRepositoryImpl (
    private val remote: CategoriesDataSource,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) : CategoriesRepository {

    override fun getCategories(order: CategoriesSort?): Flow<AppResult<List<Categories>>> = flow {
        emit(AppResult.Loading)

        val dto = remote.getCategories(order)
        emit(AppResult.Success(dto.map { it.toDomain() }))
    }
        .catch { e ->
            // Don't swallow cancellation
            if (e is CancellationException) throw e
            emit(AppResult.Error(e.toAppError()))
        }
        .flowOn(ioDispatcher)
}


