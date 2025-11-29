package com.example.network.datasource.exchanges

import com.example.model.sort.DexPairFormat
import com.example.model.sort.ExchangeTickersOrder
import com.example.network.api.getExchangeByIdKtor
import com.example.network.api.getExchangeTickersByIdKtor
import com.example.network.api.getExchangeVolumeChartKtor
import com.example.network.api.getExchangesKtor
import com.example.network.model.exchanges.ExchangeDetailDto
import com.example.network.model.exchanges.ExchangeTickersDto
import com.example.network.model.exchanges.ExchangeVolumeChartDto
import com.example.network.model.exchanges.ExchangesListDto
import com.example.network.options.toApiOrderParam
import com.example.network.options.toDomain
import io.ktor.client.HttpClient
import javax.inject.Inject

class ExchangesDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : ExchangesDataSource {

    override suspend fun getExchanges(page: Int, perPage: Int): ExchangesListDto {
        return httpClient.getExchangesKtor(
            perPage = perPage,
            page = page,
        )
        // DEPRECATED (Retrofit) exchangesApi.getExchanges(...)
    }

    override suspend fun getExchangeById(
        id: String,
        dexPairFormat: DexPairFormat?,
    ): ExchangeDetailDto {
        val dexParam = dexPairFormat?.toDomain()
        return httpClient.getExchangeByIdKtor(
            id = id,
            dexPairFormat = dexParam,
        )
    }

    override suspend fun getExchangeTickersById(
        id: String,
        coinIds: String?,
        includeExchangeLogo: Boolean?,
        depth: Boolean?,
        dexPairFormat: DexPairFormat?,
        page: Int?,
        order: ExchangeTickersOrder?,
    ): ExchangeTickersDto {
        val dexParam = dexPairFormat?.toDomain()
        val orderParam = order?.toApiOrderParam()
        return httpClient.getExchangeTickersByIdKtor(
            id = id,
            coinIds = coinIds,
            includeExchangeLogo = includeExchangeLogo,
            depth = depth,
            dexPairFormat = dexParam,
            page = page,
            order = orderParam,
        )
    }

    override suspend fun getExchangeVolumeChart(
        id: String,
        days: String,
    ): ExchangeVolumeChartDto {
        return httpClient.getExchangeVolumeChartKtor(
            id = id,
            days = days,
        )
    }
}
