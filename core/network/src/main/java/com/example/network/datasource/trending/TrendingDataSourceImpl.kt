package com.example.network.datasource.trending

import com.example.network.api.getTrendingKtor
import com.example.network.model.TrendingDto
import io.ktor.client.HttpClient
import org.koin.core.annotation.Single

/**
 * Network-backed implementation of [TrendingDataSource] using Ktor.
 */
@Single(binds = [TrendingDataSource::class])
class TrendingDataSourceImpl (
    private val httpClient: HttpClient,
) : TrendingDataSource {

    override suspend fun getTrending(): TrendingDto {
        return httpClient.getTrendingKtor()
        // DEPRECATED (Retrofit) api.getTrending()
    }
}