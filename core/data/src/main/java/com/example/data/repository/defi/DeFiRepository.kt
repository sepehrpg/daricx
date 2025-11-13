package com.example.data.repository.defi


import com.example.model.GlobalDeFiMarketData
import kotlinx.coroutines.flow.Flow

/** Repository for global DeFi market data. */
interface DeFiRepository {

    /**
     * Fetch global DeFi market data (market cap, volume, dominance, etc.).
     * Emits a single [GlobalDeFiMarketData] object.
     */
    fun getGlobalDeFi(): Flow<GlobalDeFiMarketData?>
}
