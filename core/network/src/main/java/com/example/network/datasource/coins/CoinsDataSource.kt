package com.example.network.datasource.coins

import com.example.model.sort.CoinsSort
import com.example.network.model.CoinsListDto

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
}