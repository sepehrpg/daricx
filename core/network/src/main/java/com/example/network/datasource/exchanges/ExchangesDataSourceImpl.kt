package com.example.network.datasource.exchanges

import com.example.network.api.ApiService
import com.example.network.model.ExchangesListDto
import javax.inject.Inject

class ExchangesDataSourceImpl @Inject constructor(
    private val exchangesApi: ApiService
) : ExchangesDataSource {

    override suspend fun getExchanges(page: Int, perPage: Int): ExchangesListDto {
        return exchangesApi.getExchanges(page = page, perPage = perPage)
    }

}