package com.example.network.datasource.defi

import com.example.network.api.getGlobalDeFiKtor
import com.example.network.model.GlobalDeFiMarketDataDto
import io.ktor.client.HttpClient
import org.koin.core.annotation.Single



/**
 * Network-backed implementation of [GlobalDeFiDataSource] using Ktor.
 */
@Single(binds = [GlobalDeFiDataSource::class])
class GlobalDeFiDataSourceImpl (
    private val httpClient: HttpClient,
) : GlobalDeFiDataSource {

    override suspend fun getGlobalDeFi(): GlobalDeFiMarketDataDto {
        return httpClient.getGlobalDeFiKtor()
        // DEPRECATED (Retrofit) api.getGlobalDeFi()
    }
}