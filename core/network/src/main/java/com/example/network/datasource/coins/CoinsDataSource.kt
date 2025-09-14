package com.example.network.datasource.coins

import com.example.network.model.CoinMarketsDto

interface CoinsDataSource {

    suspend fun getCoinMarkets(
        vsCurrency: String,
        page: Int,
        perPage: Int,
        order: String? = null,
        sparkline: Boolean? = true,
        priceChangePercentage: String? = null,
        locale: String? = null,
        precision: String? = null
    ): CoinMarketsDto

}