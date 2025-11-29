package com.example.network.datasource.trending

import com.example.network.api.ApiService
import com.example.network.api.getTrendingKtor
import com.example.network.model.TrendingDto
import io.ktor.client.HttpClient
import javax.inject.Inject

/**
 * Network-backed implementation of [TrendingDataSource] using Ktor.
 */
class TrendingDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : TrendingDataSource {

    override suspend fun getTrending(): TrendingDto {
        return httpClient.getTrendingKtor()
        // DEPRECATED (Retrofit) api.getTrending()
    }
}