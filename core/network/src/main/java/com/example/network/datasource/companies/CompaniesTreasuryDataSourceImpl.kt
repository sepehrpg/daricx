package com.example.network.datasource.companies

import com.example.model.option.TreasuryAsset
import com.example.network.api.ApiService
import com.example.network.model.CompaniesTreasuryDto
import com.example.network.options.toApiCoinId
import javax.inject.Inject

/** Network-backed implementation of [CompaniesTreasuryDataSource]. */
class CompaniesTreasuryDataSourceImpl @Inject constructor(
    private val api: ApiService
) : CompaniesTreasuryDataSource {

    override suspend fun getCompaniesTreasury(asset: TreasuryAsset): CompaniesTreasuryDto {
        val coinId = asset.toApiCoinId() // "bitcoin" | "ethereum"
        return api.getCompaniesTreasury(coinId)
    }
}