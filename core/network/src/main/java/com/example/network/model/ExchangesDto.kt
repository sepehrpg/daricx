package com.example.network.model


import com.example.model.Exchange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


typealias ExchangesDto = List<ExchangeDto>

@Serializable
data class ExchangeDto(
    @SerialName("country")
    val country: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("has_trading_incentive")
    val hasTradingIncentive: Boolean?,
    @SerialName("id")
    val id: String?,
    @SerialName("image")
    val image: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("trade_volume_24h_btc")
    val tradeVolume24hBtc: Double?,
    @SerialName("trust_score")
    val trustScore: Int?,
    @SerialName("trust_score_rank")
    val trustScoreRank: Int?,
    @SerialName("url")
    val url: String?,
    @SerialName("year_established")
    val yearEstablished: Int?
)


fun ExchangeDto.toDomain(): Exchange {
    return Exchange(
        country = this.country,
        description = this.description,
        hasTradingIncentive = this.hasTradingIncentive,
        id = this.id,
        image = this.image,
        name = this.name,
        tradeVolume24hBtc = this.tradeVolume24hBtc,
        trustScore = this.trustScore,
        trustScoreRank = this.trustScoreRank,
        url = this.url,
        yearEstablished = this.yearEstablished,
    )
}