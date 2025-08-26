package com.example.model




 data class CoinMarket(
    val ath: Double?,
    val athChangePercentage: Double?,
    val athDate: String?,
    val atl: Double?,
    val atlChangePercentage: Double?,
    val atlDate: String?,
    val circulatingSupply: Double?,
    val currentPrice: Double?,
    val fullyDilutedValuation: Long?,
    val high24h: Double?,
    val id: String?,
    val image: String?,
    val lastUpdated: String?,
    val low24h: Double?,
    val marketCap: Long?,
    val marketCapChange24h: Double?,
    val marketCapChangePercentage24h: Double?,
    val marketCapRank: Int?,
    val maxSupply: Double?,
    val name: String?,
    val priceChange24h: Double?,
    val priceChangePercentage24h: Double?,
    val roi: Roi?,
    val symbol: String?,
    val totalSupply: Double?,
    val totalVolume: Double?
) {
     data class Roi(
        val currency: String?,
        val percentage: Double?,
        val times: Double?
    )
}