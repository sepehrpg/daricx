package com.example.network.datasource.defi

import com.example.network.api.ApiService
import com.example.network.api.getGlobalDeFiKtor
import com.example.network.model.GlobalDeFiMarketDataDto
import io.ktor.client.HttpClient
import javax.inject.Inject



/**
 * Network-backed implementation of [GlobalDeFiDataSource] using Ktor.
 */
class GlobalDeFiDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : GlobalDeFiDataSource {

    override suspend fun getGlobalDeFi(): GlobalDeFiMarketDataDto {
        return httpClient.getGlobalDeFiKtor()
        // DEPRECATED (Retrofit) api.getGlobalDeFi()
    }
}