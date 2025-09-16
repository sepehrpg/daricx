package com.example.network.datasource.global

import com.example.network.api.ApiService
import com.example.network.model.GlobalCryptoMarketDataDto
import javax.inject.Inject

/**
 * Network-backed implementation of [GlobalDataSource].
 */
class GlobalDataSourceImpl @Inject constructor(
    private val api: ApiService
) : GlobalDataSource {
    override suspend fun getGlobal(): GlobalCryptoMarketDataDto = api.getGlobal()
}