package com.example.network.datasource.exchanges

import com.example.model.sort.DexPairFormat
import com.example.model.sort.ExchangeTickersOrder
import com.example.network.api.ApiService
import com.example.network.model.exchanges.ExchangeDetailDto
import com.example.network.model.exchanges.ExchangeTickersDto
import com.example.network.model.exchanges.ExchangeVolumeChartDto
import com.example.network.model.exchanges.ExchangesListDto
import com.example.network.options.toApiOrderParam
import com.example.network.options.toDomain
import javax.inject.Inject

class ExchangesDataSourceImpl @Inject constructor(
    private val exchangesApi: ApiService
) : ExchangesDataSource {

    override suspend fun getExchanges(page: Int, perPage: Int): ExchangesListDto {
        return exchangesApi.getExchanges(page = page, perPage = perPage)
    }

    override suspend fun getExchangeById(
        id: String,
        dexPairFormat: DexPairFormat?
    ): ExchangeDetailDto {
        return exchangesApi.getExchangeById(id = id, dexPairFormat = dexPairFormat?.toDomain())
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
        return exchangesApi.getExchangeTickersById(
            id = id,
            coinIds = coinIds,
            includeExchangeLogo = includeExchangeLogo,
            depth = depth,
            dexPairFormat = dexPairFormat?.toDomain(),
            page = page,
            order = order?.toApiOrderParam()
        )
    }

    override suspend fun getExchangeVolumeChart(
        id: String,
        days: String
    ): ExchangeVolumeChartDto {
        return exchangesApi.exchangeVolumeChart(
            id = id,
            days = days
        )
    }

}