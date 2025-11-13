package com.example.network.api

import com.example.model.sort.DexPairFormat
import com.example.model.sort.ExchangeTickersOrder
import com.example.network.model.exchanges.ExchangeDetailDto
import com.example.network.model.exchanges.ExchangeTickersDto
import com.example.network.model.exchanges.ExchangeVolumeChartDto
import com.example.network.model.exchanges.ExchangesListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Exchanges {

    //https://api.coingecko.com/api/v3/exchanges
    @GET("exchanges")
    suspend fun getExchanges(
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
    ): ExchangesListDto


    //https://api.coingecko.com/api/v3/exchanges/{id}
    @GET("exchanges/{id}")
    suspend fun getExchangeById(
        @Path("id") id: String,
        @Query("dex_pair_format") dexPairFormat: String? = null,
    ): ExchangeDetailDto


    //https://api.coingecko.com/api/v3/exchanges/{id}/tickers
    @GET("exchanges/{id}/tickers")
    suspend fun getExchangeTickersById(
        @Path("id") id: String ,
        @Query("coin_ids") coinIds: String? = null,
        @Query("include_exchange_logo") includeExchangeLogo: Boolean? = true,
        @Query("depth") depth: Boolean? = null,
        @Query("dex_pair_format") dexPairFormat: String? = null,
        @Query("page") page: Int? = null,
        @Query("order") order: String? = null,
    ): ExchangeTickersDto



    /**
     * GET https://api.coingecko.com/api/v3/exchanges/{id}/volume_chart
     *
     * Returns exchange volume chart data as an array of 2-tuples:
     *   [timestampMillis, volume]
     * Docs: https://docs.coingecko.com/v3.0.1/reference/exchanges-id-volume-chart
     *
     * @param id    Exchange ID (e.g., "binance"). See /exchanges/list.
     * @param days  One of: "1","7","14","30","90","180","365".
     *
     * @return List of points where each item is [timestampMillis, volume].
     */
    @GET("exchanges/{id}/volume_chart")
    suspend fun exchangeVolumeChart(
        @Path("id") id: String,
        @Query("days") days: String
    ): ExchangeVolumeChartDto

}

