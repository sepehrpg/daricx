package com.example.network.model


import com.example.model.Exchanges
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/** DTO for Exchange item from CoinGecko API */
typealias ExchangesListDto = List<ExchangesDto>

@Serializable
data class ExchangesDto(
    /** Country where the exchange is registered (e.g., "US") */
    val country: String?,

    /** Description text (may be null/empty) */
    val description: String?,

    /** Whether the exchange offers trading incentives */
    @SerialName("has_trading_incentive")
    val hasTradingIncentive: Boolean?,

    /** Exchange unique identifier (slug) */
    val id: String?,

    /** Logo image URL */
    val image: String?,

    /** Exchange display name (e.g., "Binance") */
    val name: String?,

    /** 24h trading volume in BTC */
    @SerialName("trade_volume_24h_btc")
    val tradeVolume24hBtc: Double?,

    /** Trust score (0–10) */
    @SerialName("trust_score")
    val trustScore: Int?,

    /** Rank by trust score */
    @SerialName("trust_score_rank")
    val trustScoreRank: Int?,

    /** Official website URL */
    val url: String?,

    /** Year of establishment (e.g., 2017) */
    @SerialName("year_established")
    val yearEstablished: Int?
)


