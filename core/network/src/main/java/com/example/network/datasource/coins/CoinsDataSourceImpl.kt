package com.example.network.datasource.coins

import com.example.network.api.ApiService
import com.example.network.model.CoinMarketsDto
import javax.inject.Inject


class CoinsDataSourceImpl @Inject constructor(
    private val coinsApi: ApiService
) : CoinsDataSource {

    override suspend fun getCoinMarkets(
        vsCurrency: String,
        page: Int,
        perPage: Int,
        order: String?,
        sparkline: Boolean?,
        priceChangePercentage: String?,
        locale: String?,
        precision: String?
    ): CoinMarketsDto {
        return coinsApi.getCoinMarkets(
            vsCurrency = vsCurrency,
            page = page,
            perPage = perPage,
            order = order,
            sparkline = sparkline,
            priceChangePercentage = priceChangePercentage,
            locale = locale,
            precision = precision
        )
    }
}