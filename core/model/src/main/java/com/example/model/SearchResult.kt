package com.example.model


/**
 * Domain model for CoinGecko /search results.
 */
data class SearchResult(
    val coins: List<Coin>?,
    val exchanges: List<Exchange>?,
    val icos: List<String>?,            // API returns an array of strings
    val categories: List<Category>?,
    val nfts: List<Nft>?
) {
    /** Coin hit in search results (not the full market model). */
    data class Coin(
        val id: String?,
        val name: String?,
        val apiSymbol: String?,
        val symbol: String?,
        val marketCapRank: Int?,
        val thumb: String?,
        val large: String?
    )

    /** Exchange hit in search results. */
    data class Exchange(
        val id: String?,
        val name: String?,
        val marketType: String?,
        val thumb: String?,
        val large: String?
    )

    /** Category hit in search results. */
    data class Category(
        val id: String?,
        val name: String?
    )

    /** NFT hit in search results. */
    data class Nft(
        val id: String?,
        val name: String?,
        val symbol: String?,
        val thumb: String?
    )
}
