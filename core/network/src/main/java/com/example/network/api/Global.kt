package com.example.network.api

import com.example.network.model.CompaniesTreasuryDto
import com.example.network.model.GlobalCryptoMarketDataDto
import com.example.network.model.GlobalDeFiMarketDataDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * GET /global
 * Returns aggregated global crypto market statistics.
 */
interface Global {

    @GET("global")
    suspend fun getGlobal(): GlobalCryptoMarketDataDto

    @GET("global/decentralized_finance_defi")
    suspend fun getGlobalDeFi(): GlobalDeFiMarketDataDto

    @GET("companies/public_treasury/{coin_id}")
    suspend fun getCompaniesTreasury(
        @Path("coin_id") coinId: String
    ): CompaniesTreasuryDto
}