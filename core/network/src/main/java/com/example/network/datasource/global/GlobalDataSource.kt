package com.example.network.datasource.global

import com.example.network.model.GlobalCryptoMarketDataDto

/**
 * Abstraction for /global data access.
 */
interface GlobalDataSource {
    suspend fun getGlobal(): GlobalCryptoMarketDataDto
}