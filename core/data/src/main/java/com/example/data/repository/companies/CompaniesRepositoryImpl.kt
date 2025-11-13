package com.example.data.repository.companies

import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.model.CompaniesTreasury
import com.example.model.option.TreasuryAsset
import com.example.network.datasource.companies.CompaniesTreasuryDataSource
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/** Network-backed implementation of [CompaniesRepository]. */
class CompaniesRepositoryImpl @Inject constructor(
    private val remote: CompaniesTreasuryDataSource,
    @Dispatcher(AppDispatcher.IO) private val io: CoroutineDispatcher
) : CompaniesRepository {

    override fun getCompaniesTreasury(asset: TreasuryAsset): Flow<CompaniesTreasury> = flow {
        val dto = remote.getCompaniesTreasury(asset)   // <- domain enum in, API mapping in network
        emit(dto.toDomain())
    }.flowOn(io)
}
