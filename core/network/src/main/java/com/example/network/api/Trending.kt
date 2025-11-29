package com.example.network.api

import com.example.network.model.TrendingDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * Ktor extension for:
 * GET /search/trending
 */
suspend fun HttpClient.getTrendingKtor(): TrendingDto {
    return get("search/trending").body()
}