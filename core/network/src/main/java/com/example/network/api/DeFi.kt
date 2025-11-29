package com.example.network.api

import com.example.network.model.GlobalDeFiMarketDataDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get



/**
 * Ktor extension for:
 * GET /global/decentralized_finance_defi
 */
suspend fun HttpClient.getGlobalDeFiKtor(): GlobalDeFiMarketDataDto {
    return get("global/decentralized_finance_defi").body()
}