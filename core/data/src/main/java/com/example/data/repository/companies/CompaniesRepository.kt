package com.example.data.repository.companies

import com.example.model.CompaniesTreasury
import com.example.model.option.TreasuryAsset
import kotlinx.coroutines.flow.Flow


/** Repository for public companies’ BTC/ETH treasury holdings. */
interface CompaniesRepository {

    /**
     * Fetch companies treasury for the given asset (BTC/ETH).
     * Emits a single [CompaniesTreasury] object.
     */
    fun getCompaniesTreasury(asset: TreasuryAsset): Flow<CompaniesTreasury>
}