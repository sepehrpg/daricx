package com.example.network.datasource.coins

import com.example.model.sort.CoinTickersOrder
import com.example.model.sort.CoinsSort
import com.example.model.sort.DexPairFormat
import com.example.network.model.coins.CoinDetailsDto
import com.example.network.model.coins.CoinHistoricalChartDto
import com.example.network.model.coins.CoinHistoricalDataDto
import com.example.network.model.coins.CoinOHLCChartCandleDto
import com.example.network.model.coins.CoinTickersDto
import com.example.network.model.coins.CoinsListDto

interface CoinsDataSource {

    suspend fun getCoinMarkets(
        vsCurrency: String,
        page: Int,
        perPage: Int,
        order: CoinsSort? = null,
        sparkline: Boolean? = true,
        priceChangePercentage: String? = null,
        locale: String? = null,
        precision: String? = null
    ): CoinsListDto



    /**
     * Fetch detailed data for a specific coin by its ID.
     *
     * @param id The unique ID of the coin (e.g., "bitcoin").
     * @param localization Include all localized languages. Default is true.
     * @param tickers Include tickers data. Default is true.
     * @param marketData Include market data. Default is true.
     * @param communityData Include community data. Default is true.
     * @param developerData Include developer data. Default is true.
     * @param sparkline Include sparkline 7-day data. Default is false.
     * @param dexPairFormat Display DEX pair as symbol or contract_address.
     */
    suspend fun getCoinDetail(
        id: String,
        localization: Boolean? = true,
        tickers: Boolean? = true,
        marketData: Boolean? = true,
        communityData: Boolean? = true,
        developerData: Boolean? = true,
        sparkline: Boolean? = false,
        dexPairFormat: DexPairFormat = DexPairFormat.CONTRACT_ADDRESS
    ): CoinDetailsDto


    suspend fun getCoinTickersById(
        id: String,
        exchangeIds: String? = null,
        includeExchangeLogo: Boolean? = true,
        depth: Boolean? = null,
        dexPairFormat: DexPairFormat? = null,
        page: Int? = null,
        order: CoinTickersOrder? = null
    ): CoinTickersDto

    suspend fun getCoinHistoricalDataById(
        id: String,
        date: String,
        localization: Boolean = true
    ): CoinHistoricalDataDto

    suspend fun getCoinHistoricalChart(
        id: String,
        vsCurrency: String,
        days: String,
        interval: String? = null,
        precision: String? = null
    ): CoinHistoricalChartDto


    suspend fun getCoinHistoricalChartWithTimeRange(
        id: String,
        vsCurrency: String,
        from: Long,
        to: Long,
        precision: String? = null
    ): CoinHistoricalChartDto


    suspend fun getCoinOHLCChartCandle(
        id: String,
        vsCurrency: String,
        days: String,
        precision: String? = null
    ): CoinOHLCChartCandleDto
}