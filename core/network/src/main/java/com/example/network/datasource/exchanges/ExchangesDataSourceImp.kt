package com.example.network.datasource.exchanges

import com.example.network.api.ApiService
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.model.ExchangesDto
import javax.inject.Inject

class ExchangesDataSourceImp @Inject constructor(
    private val exchangesApi: ApiService
) : ExchangesDataSource {

    override suspend fun getExchanges(page: Int, perPage: Int): ExchangesDto {
        return exchangesApi.getExchanges(page = page, perPage = perPage)
    }

}