package com.example.network.datasource.companies

import com.example.network.model.CompaniesTreasuryDto

/**
 * Abstraction for /companies/public_treasury/{coin_id}.
 */
interface CompaniesTreasuryDataSource {
    /**
     * @param coinId "bitcoin" or "ethereum"
     */
    suspend fun getCompaniesTreasury(coinId: String): CompaniesTreasuryDto
}