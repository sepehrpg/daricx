package com.example.network.api

import com.example.network.model.CompaniesTreasuryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface Companies {

    @GET("companies/public_treasury/{coin_id}")
    suspend fun getCompaniesTreasury(
        @Path("coin_id") coinId: String
    ): CompaniesTreasuryDto

}