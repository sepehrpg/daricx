package com.example.network.datasource.coins

import com.example.model.sort.CoinsSort
import com.example.network.api.ApiService
import com.example.network.model.CoinsListDto
import com.example.network.model.mappers.toApiOrderParam
import com.example.network.options.toApiOrderParam
import javax.inject.Inject


class CoinsDataSourceImpl @Inject constructor(
    private val coinsApi: ApiService
) : CoinsDataSource {

    override suspend fun getCoinMarkets(
        vsCurrency: String,
        page: Int,
        perPage: Int,
        order: CoinsSort?,
        sparkline: Boolean?,
        priceChangePercentage: String?,
        locale: String?,
        precision: String?
    ): CoinsListDto {
        return coinsApi.getCoinMarkets(
            vsCurrency = vsCurrency,
            page = page,
            perPage = perPage,
            order = order?.toApiOrderParam(),
            sparkline = sparkline,
            priceChangePercentage = priceChangePercentage,
            locale = locale,
            precision = precision
        )
    }
}