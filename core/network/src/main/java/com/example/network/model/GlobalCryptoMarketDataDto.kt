package com.example.network.model


import com.example.model.GlobalCryptoMarketData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for Global Crypto Stats (CoinGecko Global API).
 */
@Serializable
data class GlobalCryptoMarketDataDto(
    val data: Data?
) {
    @Serializable
    data class Data(
        @SerialName("active_cryptocurrencies")
        val activeCryptocurrencies: Int?,
        @SerialName("ended_icos")
        val endedIcos: Int?,
        @SerialName("market_cap_change_percentage_24h_usd")
        val marketCapChangePercentage24hUsd: Double?,
        @SerialName("market_cap_percentage")
        val marketCapPercentage: Map<String, Double>?,
        val markets: Int?,
        @SerialName("ongoing_icos")
        val ongoingIcos: Int?,
        @SerialName("total_market_cap")
        val totalMarketCap: Map<String, Double>?,
        @SerialName("total_volume")
        val totalVolume: Map<String, Double>?,
        @SerialName("upcoming_icos")
        val upcomingIcos: Int?,
        @SerialName("updated_at")
        val updatedAt: Long?
    )
}

