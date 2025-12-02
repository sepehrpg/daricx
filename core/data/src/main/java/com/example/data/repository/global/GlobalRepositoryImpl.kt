package com.example.data.repository.global


import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.data.repository.exchanges.ExchangesRepository
import com.example.model.GlobalCryptoMarketData
import com.example.network.datasource.global.GlobalDataSource
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Single

/** Network-backed implementation of [GlobalRepository]. */
@Single(binds = [GlobalRepository::class])
class GlobalRepositoryImpl (
    private val remote: GlobalDataSource,
    @Dispatcher(AppDispatcher.IO) private val io: CoroutineDispatcher
) : GlobalRepository {

    override fun getGlobal(): Flow<GlobalCryptoMarketData?> = flow {
        val dto = remote.getGlobal()
        emit(dto.toDomain())
    }.flowOn(io)
}
