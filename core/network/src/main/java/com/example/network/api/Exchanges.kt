package com.example.network.api

import com.example.network.model.exchanges.ExchangeDetailDto
import com.example.network.model.exchanges.ExchangeTickersDto
import com.example.network.model.exchanges.ExchangeVolumeChartDto
import com.example.network.model.exchanges.ExchangesListDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Ktor extension for:
 * GET /exchanges
 */
suspend fun HttpClient.getExchangesKtor(
    perPage: Int = 100,
    page: Int = 1,
): ExchangesListDto {
    return get("exchanges") {
        parameter("per_page", perPage)
        parameter("page", page)
    }.body()
}

/**
 * Ktor extension for:
 * GET /exchanges/{id}
 */
suspend fun HttpClient.getExchangeByIdKtor(
    id: String,
    dexPairFormat: String? = null,
): ExchangeDetailDto {
    return get("exchanges/$id") {
        if (dexPairFormat != null) {
            parameter("dex_pair_format", dexPairFormat)
        }
    }.body()
}

/**
 * Ktor extension for:
 * GET /exchanges/{id}/tickers
 */
suspend fun HttpClient.getExchangeTickersByIdKtor(
    id: String,
    coinIds: String? = null,
    includeExchangeLogo: Boolean? = true,
    depth: Boolean? = null,
    dexPairFormat: String? = null,
    page: Int? = null,
    order: String? = null,
): ExchangeTickersDto {
    return get("exchanges/$id/tickers") {
        if (coinIds != null) {
            parameter("coin_ids", coinIds)
        }
        if (includeExchangeLogo != null) {
            parameter("include_exchange_logo", includeExchangeLogo)
        }
        if (depth != null) {
            parameter("depth", depth)
        }
        if (dexPairFormat != null) {
            parameter("dex_pair_format", dexPairFormat)
        }
        if (page != null) {
            parameter("page", page)
        }
        if (order != null) {
            parameter("order", order)
        }
    }.body()
}

/**
 * Ktor extension for:
 * GET /exchanges/{id}/volume_chart
 */
suspend fun HttpClient.getExchangeVolumeChartKtor(
    id: String,
    days: String,
): ExchangeVolumeChartDto {
    return get("exchanges/$id/volume_chart") {
        parameter("days", days)
    }.body()
}

