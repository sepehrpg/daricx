package com.example.network.datasource.defi

import com.example.network.model.GlobalDeFiMarketDataDto

/**
 * Abstraction for /global/decentralized_finance_defi data access.
 */
interface GlobalDeFiDataSource {
    suspend fun getGlobalDeFi(): GlobalDeFiMarketDataDto
}