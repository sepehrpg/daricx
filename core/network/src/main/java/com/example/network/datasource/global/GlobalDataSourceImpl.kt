package com.example.network.datasource.global

import com.example.network.api.getGlobalKtor
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.model.GlobalCryptoMarketDataDto
import io.ktor.client.HttpClient
import org.koin.core.annotation.Single

/**
 * Network-backed implementation of [GlobalDataSource] using Ktor.
 */
@Single(binds = [GlobalDataSource::class])
class GlobalDataSourceImpl (
    private val httpClient: HttpClient,
) : GlobalDataSource {

    override suspend fun getGlobal(): GlobalCryptoMarketDataDto {
        return httpClient.getGlobalKtor()
        // DEPRECATED (Retrofit) api.getGlobal()
    }
}