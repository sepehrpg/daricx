package com.example.network.datasource.exchanges

import com.example.network.model.ExchangesDto

interface ExchangesDataSource {

    suspend fun getExchanges(
        page: Int,
        perPage: Int,
    ): ExchangesDto

}