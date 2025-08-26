package com.example.network.datasource

import com.example.network.api.ApiService
import com.example.network.model.CoinMarketsDto
import javax.inject.Inject

class RemoteCoinsDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteCoinsDataSource {

    override suspend fun getCoinMarkets(vsCurrency:String): CoinMarketsDto {
        return  apiService.getCoinMarkets(vsCurrency)
    }
}