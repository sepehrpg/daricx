package com.example.network.api

import com.example.network.model.coins.CoinDetailsDto
import com.example.network.model.coins.CoinHistoricalChartDto
import com.example.network.model.coins.CoinHistoricalDataDto
import com.example.network.model.coins.CoinOHLCChartCandleDto
import com.example.network.model.coins.CoinTickersDto
import com.example.network.model.coins.CoinsListDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Ktor extension for "GET /coins/markets".
 *
 * Replaces the Retrofit-based getCoinMarkets endpoint.
 */
suspend fun HttpClient.getCoinMarketsKtor(
    vsCurrency: String,
    ids: String? = null,
    names: String? = null,
    symbols: String? = null,
    includeTokens: String? = null,
    category: String? = null,
    order: String? = null,
    perPage: Int? = null,
    page: Int? = null,
    sparkline: Boolean? = null,
    priceChangePercentage: String? = null,
    locale: String? = null,
    precision: String? = null,
): CoinsListDto {
    return get("coins/markets") {
        parameter("vs_currency", vsCurrency)
        if (ids != null) parameter("ids", ids)
        if (names != null) parameter("names", names)
        if (symbols != null) parameter("symbols", symbols)
        if (includeTokens != null) parameter("include_tokens", includeTokens)
        if (category != null) parameter("category", category)
        if (order != null) parameter("order", order)
        if (perPage != null) parameter("per_page", perPage)
        if (page != null) parameter("page", page)
        if (sparkline != null) parameter("sparkline", sparkline)
        if (priceChangePercentage != null) parameter("price_change_percentage", priceChangePercentage)
        if (locale != null) parameter("locale", locale)
        if (precision != null) parameter("precision", precision)
    }.body()
}

/**
 * Ktor extension for "GET /coins/{id}".
 */
suspend fun HttpClient.getCoinDetailKtor(
    id: String,
    localization: Boolean? = true,
    tickers: Boolean? = true,
    marketData: Boolean? = true,
    communityData: Boolean? = true,
    developerData: Boolean? = true,
    sparkline: Boolean? = false,
    dexPairFormat: String? = "contract_address",
): CoinDetailsDto {
    return get("coins/$id") {
        if (localization != null) parameter("localization", localization)
        if (tickers != null) parameter("tickers", tickers)
        if (marketData != null) parameter("market_data", marketData)
        if (communityData != null) parameter("community_data", communityData)
        if (developerData != null) parameter("developer_data", developerData)
        if (sparkline != null) parameter("sparkline", sparkline)
        if (dexPairFormat != null) parameter("dex_pair_format", dexPairFormat)
    }.body()
}

/**
 * Ktor extension for "GET /coins/{id}/tickers".
 */
suspend fun HttpClient.getCoinTickersByIdKtor(
    id: String,
    exchangeIds: String? = null,
    includeExchangeLogo: Boolean? = true,
    depth: Boolean? = null,
    dexPairFormat: String? = null,
    page: Int? = null,
    order: String? = null,
): CoinTickersDto {
    return get("coins/$id/tickers") {
        if (exchangeIds != null) parameter("exchange_ids", exchangeIds)
        if (includeExchangeLogo != null) parameter("include_exchange_logo", includeExchangeLogo)
        if (depth != null) parameter("depth", depth)
        if (dexPairFormat != null) parameter("dex_pair_format", dexPairFormat)
        if (page != null) parameter("page", page)
        if (order != null) parameter("order", order)
    }.body()
}

/**
 * Ktor extension for "GET /coins/{id}/history".
 *
 * Returns a historical snapshot for a coin at a given date.
 *
 * @param id   Coin ID (e.g., "bitcoin").
 * @param date Snapshot date in dd-MM-yyyy format.
 */
suspend fun HttpClient.getCoinHistoricalDataByIdKtor(
    id: String,
    date: String,
    localization: Boolean = true,
): CoinHistoricalDataDto {
    return get("coins/$id/history") {
        parameter("date", date)
        parameter("localization", localization)
    }.body()
}

/**
 * Ktor extension for "GET /coins/{id}/market_chart".
 */
suspend fun HttpClient.getCoinHistoricalChartKtor(
    id: String,
    vsCurrency: String,
    days: String,
    interval: String? = null,
    precision: String? = null,
): CoinHistoricalChartDto {
    return get("coins/$id/market_chart") {
        parameter("vs_currency", vsCurrency)
        parameter("days", days)
        if (interval != null) parameter("interval", interval)
        if (precision != null) parameter("precision", precision)
    }.body()
}

/**
 * Ktor extension for "GET /coins/{id}/market_chart/range".
 */
suspend fun HttpClient.getCoinHistoricalChartWithTimeRangeKtor(
    id: String,
    vsCurrency: String,
    from: Long,
    to: Long,
    precision: String? = null,
): CoinHistoricalChartDto {
    return get("coins/$id/market_chart/range") {
        parameter("vs_currency", vsCurrency)
        parameter("from", from)
        parameter("to", to)
        if (precision != null) parameter("precision", precision)
    }.body()
}

/**
 * Ktor extension for "GET /coins/{id}/ohlc".
 *
 * Returns OHLC candles as:
 * [timestampMillis, open, high, low, close].
 */
suspend fun HttpClient.getCoinOHLCChartCandleKtor(
    id: String,
    vsCurrency: String,
    days: String,
    precision: String? = null,
): CoinOHLCChartCandleDto {
    return get("coins/$id/ohlc") {
        parameter("vs_currency", vsCurrency)
        parameter("days", days)
        if (precision != null) parameter("precision", precision)
    }.body()
}
