package com.example.model


/**
 * Domain model for market categories
 */
data class Categories(
    /** Unique identifier of the category */
    val id: String?,

    /** Category display name */
    val name: String?,

    /** Market capitalization in USD */
    val marketCap: Double?,

    /** 24h change in market cap (%) */
    val marketCapChange24h: Double?,

    /** 24h trading volume */
    val volume24h: Double?,

    /** Top 3 coins image URLs */
    val top3Coins: List<String?>?,

    /** Top 3 coins IDs */
    val top3CoinsId: List<String?>?,

    /** Extra content/description (optional) */
    val content: String?,

    /** Last update timestamp */
    val updatedAt: String?
)
