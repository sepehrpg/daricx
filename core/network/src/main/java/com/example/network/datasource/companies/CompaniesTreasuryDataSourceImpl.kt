package com.example.network.datasource.companies

import com.example.network.api.ApiService
import com.example.network.model.CompaniesTreasuryDto
import javax.inject.Inject

/**
 * Network-backed implementation of [CompaniesTreasuryDataSource].
 */
class CompaniesTreasuryDataSourceImpl @Inject constructor(
    private val api: ApiService
) : CompaniesTreasuryDataSource {

    override suspend fun getCompaniesTreasury(coinId: String): CompaniesTreasuryDto {
        return api.getCompaniesTreasury(coinId)
    }
}