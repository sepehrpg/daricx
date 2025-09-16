package com.example.model


/**
 * Domain model for Global Crypto Market Data (CoinGecko API).
 */
data class GlobalCryptoMarketData(
    val activeCryptocurrencies: Int?,
    val endedIcos: Int?,
    val marketCapChangePercentage24hUsd: Double?,
    val marketCapPercentage: Map<String, Double>?,
    val markets: Int?,
    val ongoingIcos: Int?,
    val totalMarketCap: Map<String, Double>?,
    val totalVolume: Map<String, Double>?,
    val upcomingIcos: Int?,
    val updatedAt: Long?
)
