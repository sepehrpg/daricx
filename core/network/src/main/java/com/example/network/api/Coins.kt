package com.example.network.api

import com.example.model.sort.CoinTickersOrder
import com.example.model.sort.DexPairFormat
import com.example.network.model.coins.CoinDetailsDto
import com.example.network.model.coins.CoinHistoricalChartDto
import com.example.network.model.coins.CoinHistoricalDataDto
import com.example.network.model.coins.CoinOHLCChartCandleDto
import com.example.network.model.coins.CoinTickersDto
import com.example.network.model.coins.CoinsListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Coins {

    /**
     * Fetches a list of coins with their market data.
     *
     * @param vsCurrency The target currency of market data (e.g., "usd", "eur").
     * @param ids A comma-separated string of coin ids to filter by.
     * @param names A comma-separated string of coin names to filter by.
     * @param symbols A comma-separated string of coin symbols to filter by.
     * @param includeTokens Specifies whether to include all matching tokens for symbol lookups.
     * @param category Filter by coin category.
     * @param order The sort order for the results. Default is market_cap_desc.
     * @param perPage The number of results per page (valid values: 1-250).
     * @param page The page number to retrieve.
     * @param sparkline Include sparkline 7-day data in the response.
     * @param priceChangePercentage Include price change percentage timeframes.
     * @param locale The language for the response data.
     * @param precision The number of decimal places for currency price values.
     * @return A [CoinsListDto] which is a list of coin market data.
     */
    @GET("coins/markets")
    suspend fun getCoinMarkets(
        @Query("vs_currency") vsCurrency: String,
        @Query("ids") ids: String? = null,
        @Query("names") names: String? = null,
        @Query("symbols") symbols: String? = null,
        @Query("include_tokens") includeTokens: String? = null,
        @Query("category") category: String? = null,
        @Query("order") order: String? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null,
        @Query("sparkline") sparkline: Boolean? = null,
        @Query("price_change_percentage") priceChangePercentage: String? = null,
        @Query("locale") locale: String? = null,
        @Query("precision") precision: String? = null
    ): CoinsListDto


    @GET("coins/{id}")
    suspend fun getCoinDetail(
        @Path("id") id: String,
        @Query("localization") localization: Boolean? = true,
        @Query("tickers") tickers: Boolean? = true,
        @Query("market_data") marketData: Boolean? = true,
        @Query("community_data") communityData: Boolean? = true,
        @Query("developer_data") developerData: Boolean? = true,
        @Query("sparkline") sparkline: Boolean? = false,
        @Query("dex_pair_format") dexPairFormat: String? = "contract_address" //ENUM
    ): CoinDetailsDto



    //https://api.coingecko.com/api/v3/coins/{id}/tickers
    @GET("coins/{id}/tickers")
    suspend fun getCoinTickersById(
        @Path("id") ids: String ,
        @Query("exchange_ids") exchangeIds: String? = null,
        @Query("include_exchange_logo") includeExchangeLogo: Boolean? = true,
        @Query("depth") depth: Boolean? = null,
        @Query("dex_pair_format") dexPairFormat: String? = null,
        @Query("page") page: Int? = null,
        @Query("order") order: String? = null,
    ): CoinTickersDto



    /**
     * GET https://api.coingecko.com/api/v3/coins/{id}/history
     *
     * Returns historical snapshot for a coin at a given date.
     * Docs: https://api.coingecko.com/api/v3/coins/{id}/history
     *
     * @param id            Coin ID (e.g., "bitcoin"). See /coins/list for valid IDs.
     * @param date          Snapshot date in dd-MM-yyyy format (e.g., "30-12-2023").
     * @param localization  Whether to include localized languages in response. Default: true.
     *
     * @return CoinHistoricalDataDto (name, symbol, image, market_data, community_data, developer_data, public_interest_stats, localization, etc.)
     */
    @GET("coins/{id}/history")
    suspend fun coinHistoricalDataByID(
        @Path("id") id: String,
        @Query("date") date: String,  // Format: dd-mm-yyyy
        @Query("localization") localization: Boolean = true
    ): CoinHistoricalDataDto



    /**
     * GET https://api.coingecko.com/api/v3/coins/{id}/market_chart
     *
     * Returns market chart data (prices, market_caps, total_volumes).
     * Docs: https://docs.coingecko.com/v3.0.1/reference/coins-id-market-chart
     *
     * @param id           Coin ID (e.g., "bitcoin"). See /coins/list for valid IDs.
     * @param vsCurrency   Target currency of market data (e.g., "usd"). See /simple/supported_vs_currencies.
     * @param days         Data up to N days ago. Accepts either an integer (as string) or keywords like "max".
     * @param interval     Optional. Leave null for auto granularity. Possible value: "daily".
     * @param precision    Optional. One of: "full" or digits "0".."18".
     */
    @GET("coins/{id}/market_chart")
    suspend fun coinHistoricalChart(
        @Path("id") id: String,
        @Query("vs_currency") vsCurrency: String,
        @Query("days") days: String,
        @Query("interval") interval: String? = null,   // e.g., "daily" or null
        @Query("precision") precision: String? = null  // e.g., "full" or "2"
    ): CoinHistoricalChartDto



    /**
     * GET https://api.coingecko.com/api/v3/coins/{id}/market_chart/range
     *
     * Returns market chart data within a specific time range.
     * Docs: https://docs.coingecko.com/v3.0.1/reference/coins-id-market-chart-range
     *
     * @param id          Coin ID (e.g., "bitcoin"). See /coins/list for valid IDs.
     * @param vsCurrency  Target currency (e.g., "usd"). See /simple/supported_vs_currencies.
     * @param from        Start time, UNIX timestamp in SECONDS (e.g., 1711929600).
     * @param to          End time, UNIX timestamp in SECONDS (e.g., 1712275200).
     * @param precision   Optional. One of: "full" or digits "0".."18".
     *
     * @return CoinMarketChartRangeDto with arrays: prices, market_caps, total_volumes.
     */
    @GET("coins/{id}/market_chart/range")
    suspend fun coinHistoricalChartWithTimeRange(
        @Path("id") id: String,
        @Query("vs_currency") vsCurrency: String,
        @Query("from") from: Long,           // seconds since epoch
        @Query("to") to: Long,               // seconds since epoch
        @Query("precision") precision: String? = null
    ): CoinHistoricalChartDto



    /**
     * GET https://api.coingecko.com/api/v3/coins/{id}/ohlc
     *
     * Returns OHLC data for a coin.
     * Docs: https://docs.coingecko.com/v3.0.1/reference/coins-id-ohlc
     *
     * @param id          Coin ID (e.g., "bitcoin"). See /coins/list for valid IDs.
     * @param vsCurrency  Target price currency (e.g., "usd"). See /simple/supported_vs_currencies.
     * @param days        Number of days back (one of: 1, 7, 14, 30, 90, 180, 365).
     * @param precision   Optional decimal precision: "full" or digits "0".."18".
     *
     * @return A list of 5-tuples as OHLC candles: [timestampMillis, open, high, low, close].
     */
    @GET("coins/{id}/ohlc")
    suspend fun coinOHLCChartCandle(
        @Path("id") id: String,
        @Query("vs_currency") vsCurrency: String,
        @Query("days") days: String,               // API expects enum-like strings e.g., "1","7","30",...
        @Query("precision") precision: String? = null
    ): CoinOHLCChartCandleDto

}

