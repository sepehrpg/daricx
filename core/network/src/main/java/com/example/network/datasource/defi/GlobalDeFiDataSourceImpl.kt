package com.example.network.datasource.defi

import com.example.network.api.ApiService
import com.example.network.model.GlobalDeFiMarketDataDto
import javax.inject.Inject

/**
 * Network-backed implementation of [GlobalDeFiDataSource].
 */
class GlobalDeFiDataSourceImpl @Inject constructor(
    private val api: ApiService
) : GlobalDeFiDataSource {

    override suspend fun getGlobalDeFi(): GlobalDeFiMarketDataDto = api.getGlobalDeFi()
}