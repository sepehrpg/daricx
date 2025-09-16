package com.example.network.datasource.exchanges

import com.example.network.model.ExchangesListDto

interface ExchangesDataSource {

    suspend fun getExchanges(
        page: Int,
        perPage: Int,
    ): ExchangesListDto

}