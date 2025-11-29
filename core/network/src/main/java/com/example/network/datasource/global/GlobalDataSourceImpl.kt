package com.example.network.datasource.global

import com.example.network.api.getGlobalKtor
import com.example.network.model.GlobalCryptoMarketDataDto
import io.ktor.client.HttpClient
import javax.inject.Inject

/**
 * Network-backed implementation of [GlobalDataSource] using Ktor.
 */
class GlobalDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : GlobalDataSource {

    override suspend fun getGlobal(): GlobalCryptoMarketDataDto {
        return httpClient.getGlobalKtor()
        // DEPRECATED (Retrofit) api.getGlobal()
    }
}