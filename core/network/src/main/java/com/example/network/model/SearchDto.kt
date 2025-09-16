package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for search endpoint.
 */
@Serializable
data class SearchDto(
    val coins: List<Coin>?,
    val exchanges: List<Exchange>?,
    val icos: List<String>?,
    val categories: List<Category>?,
    val nfts: List<Nft>?
) {
    @Serializable
    data class Coin(
        val id: String?,
        val name: String?,
        @SerialName("api_symbol") val apiSymbol: String?,
        val symbol: String?,
        @SerialName("market_cap_rank") val marketCapRank: Int?,
        val thumb: String?,
        val large: String?
    )

    @Serializable
    data class Exchange(
        val id: String?,
        val name: String?,
        @SerialName("market_type") val marketType: String?,
        val thumb: String?,
        val large: String?
    )

    @Serializable
    data class Category(
        val id: String?,
        val name: String?
    )

    @Serializable
    data class Nft(
        val id: String?,
        val name: String?,
        val symbol: String?,
        val thumb: String?
    )
}
