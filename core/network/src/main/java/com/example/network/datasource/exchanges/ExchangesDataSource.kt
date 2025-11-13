package com.example.network.datasource.exchanges

import com.example.model.sort.DexPairFormat
import com.example.model.sort.ExchangeTickersOrder
import com.example.network.model.exchanges.ExchangeDetailDto
import com.example.network.model.exchanges.ExchangeTickersDto
import com.example.network.model.exchanges.ExchangeVolumeChartDto
import com.example.network.model.exchanges.ExchangesListDto

interface ExchangesDataSource {

    suspend fun getExchanges(
        page: Int,
        perPage: Int,
    ): ExchangesListDto

    suspend fun getExchangeById(
        id: String,
        dexPairFormat: DexPairFormat?,
    ): ExchangeDetailDto

    suspend fun getExchangeTickersById(
        id: String,
        coinIds: String? = null,
        includeExchangeLogo: Boolean? = true,
        depth: Boolean? = null,
        dexPairFormat: DexPairFormat? = null,
        page: Int? = null,
        order: ExchangeTickersOrder? = null,
    ): ExchangeTickersDto

    suspend fun getExchangeVolumeChart(
        id: String,
        days: String
    ): ExchangeVolumeChartDto

}