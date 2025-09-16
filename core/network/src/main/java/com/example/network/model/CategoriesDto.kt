package com.example.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias CategoriesListDto = List<CategoriesDto>

@Serializable
data class CategoriesDto(
    @SerialName("content")
    val content: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("market_cap")
    val marketCap: Double?,
    @SerialName("market_cap_change_24h")
    val marketCapChange24h: Double?,
    @SerialName("name")
    val name: String?,
    @SerialName("top_3_coins")
    val top3Coins: List<String?>?,
    @SerialName("top_3_coins_id")
    val top3CoinsId: List<String?>?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("volume_24h")
    val volume24h: Double?
)


