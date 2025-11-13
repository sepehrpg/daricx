package com.example.data.repository.global


import com.example.model.GlobalCryptoMarketData
import kotlinx.coroutines.flow.Flow

/** Repository for global crypto market data (/global). */
interface GlobalRepository {

    /**
     * Fetch global cryptocurrency stats (total market cap, volume, market cap %, etc.).
     */
    fun getGlobal(): Flow<GlobalCryptoMarketData?>
}
