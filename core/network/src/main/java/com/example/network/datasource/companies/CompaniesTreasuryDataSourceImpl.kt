package com.example.network.datasource.companies

import com.example.model.option.TreasuryAsset
import com.example.network.api.getCompaniesTreasuryKtor
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.model.CompaniesTreasuryDto
import com.example.network.options.toApiCoinId
import io.ktor.client.HttpClient
import org.koin.core.annotation.Single

/** Network-backed implementation of [CompaniesTreasuryDataSource] using Ktor. */
@Single(binds = [CompaniesTreasuryDataSource::class])
class CompaniesTreasuryDataSourceImpl (
    private val httpClient: HttpClient,
) : CompaniesTreasuryDataSource {

    override suspend fun getCompaniesTreasury(asset: TreasuryAsset): CompaniesTreasuryDto {
        val coinId = asset.toApiCoinId() // "bitcoin" | "ethereum"
        return httpClient.getCompaniesTreasuryKtor(coinId)
        // DEPRECATED (Retrofit) api.getCompaniesTreasury(coinId)
    }
}