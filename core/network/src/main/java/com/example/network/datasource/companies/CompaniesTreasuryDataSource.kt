package com.example.network.datasource.companies

import com.example.model.option.TreasuryAsset
import com.example.network.model.CompaniesTreasuryDto


/** Abstraction for /companies/public_treasury/{coin_id}. */
interface CompaniesTreasuryDataSource {
    /** @param asset Bitcoin or Ethereum (domain enum) */
    suspend fun getCompaniesTreasury(asset: TreasuryAsset): CompaniesTreasuryDto
}