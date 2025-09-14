package com.example.model


data class Exchange(
    val id: String?,
    val name: String?,
    val country: String?,
    val description: String?,
    val hasTradingIncentive: Boolean?,
    val image: String?,
    val tradeVolume24hBtc: Double?,
    val trustScore: Int?,
    val trustScoreRank: Int?,
    val url: String?,
    val yearEstablished: Int?
)

