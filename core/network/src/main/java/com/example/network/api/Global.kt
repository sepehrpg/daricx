package com.example.network.api

import com.example.network.model.GlobalCryptoMarketDataDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get



/**
 * Ktor extension for:
 * GET /global
 */
suspend fun HttpClient.getGlobalKtor(): GlobalCryptoMarketDataDto {
    return get("global").body()
}