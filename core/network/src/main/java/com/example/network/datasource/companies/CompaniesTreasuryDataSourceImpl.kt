package com.example.network.datasource.companies

import com.example.model.option.TreasuryAsset
import com.example.network.api.ApiService
import com.example.network.api.getCompaniesTreasuryKtor
import com.example.network.model.CompaniesTreasuryDto
import com.example.network.options.toApiCoinId
import io.ktor.client.HttpClient
import javax.inject.Inject

/** Network-backed implementation of [CompaniesTreasuryDataSource] using Ktor. */
class CompaniesTreasuryDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : CompaniesTreasuryDataSource {

    override suspend fun getCompaniesTreasury(asset: TreasuryAsset): CompaniesTreasuryDto {
        val coinId = asset.toApiCoinId() // "bitcoin" | "ethereum"
        return httpClient.getCompaniesTreasuryKtor(coinId)
        // DEPRECATED (Retrofit) api.getCompaniesTreasury(coinId)
    }
}