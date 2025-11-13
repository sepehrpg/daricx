package com.example.data.repository.defi

import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.model.GlobalDeFiMarketData
import com.example.network.datasource.defi.GlobalDeFiDataSource
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/** Network-backed implementation of [DeFiRepository]. */
class DeFiRepositoryImpl @Inject constructor(
    private val remote: GlobalDeFiDataSource,
    @Dispatcher(AppDispatcher.IO) private val io: CoroutineDispatcher
) : DeFiRepository {

    override fun getGlobalDeFi(): Flow<GlobalDeFiMarketData?> = flow {
        val dto = remote.getGlobalDeFi()
        emit(dto.toDomain())
    }.flowOn(io)
}
