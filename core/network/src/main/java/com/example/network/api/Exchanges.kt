package com.example.network.api

import com.example.network.model.ExchangesListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface Exchanges {

    //https://api.coingecko.com/api/v3/exchanges
    @GET("exchanges")
    suspend fun getExchanges(
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
    ): ExchangesListDto

}