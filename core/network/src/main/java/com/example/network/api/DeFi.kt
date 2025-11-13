package com.example.network.api

import com.example.network.model.GlobalDeFiMarketDataDto
import retrofit2.http.GET

interface DeFi {

    @GET("global/decentralized_finance_defi")
    suspend fun getGlobalDeFi(): GlobalDeFiMarketDataDto
}