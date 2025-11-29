package com.example.network.api

import com.example.network.model.CompaniesTreasuryDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


/**
 * Ktor extension for:
 * GET /companies/public_treasury/{coin_id}
 */
suspend fun HttpClient.getCompaniesTreasuryKtor(
    coinId: String,
): CompaniesTreasuryDto {
    return get("companies/public_treasury/$coinId").body()
}