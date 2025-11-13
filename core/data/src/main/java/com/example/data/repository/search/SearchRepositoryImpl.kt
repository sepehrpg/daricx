package com.example.data.repository.search


import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.model.Search
import com.example.network.datasource.search.SearchDataSource
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Network-backed implementation of [SearchRepository].
 */
class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchDataSource,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) : SearchRepository {

    override suspend fun search(query: String): Search = withContext(ioDispatcher) {
        remoteDataSource.search(query).toDomain()
    }
}
