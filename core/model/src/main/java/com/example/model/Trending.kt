package com.example.model


/**
 * Domain model for Trending API (coins, NFTs, categories).
 */
data class Trending(
    val categories: List<Category>?,
    val coins: List<Coin>?,
    val nfts: List<Nft>?
) {
    // ------------------ Categories ------------------
    data class Category(
        val coinsCount: Int?,
        val data: Data?,
        val id: Int?,
        val marketCap1hChange: Double?,
        val name: String?,
        val slug: String?
    ) {
        data class Data(
            val marketCap: Double?,
            val marketCapBtc: Double?,
            val marketCapChangePercentage24h: Map<String, Double>?,
            val sparkline: String?,
            val totalVolume: Double?,
            val totalVolumeBtc: Double?
        )
    }

    // ------------------ Coins ------------------
    data class Coin(
        val item: Item?
    ) {
        data class Item(
            val coinId: Int?,
            val data: Data?,
            val id: String?,
            val large: String?,
            val marketCapRank: Int?,
            val name: String?,
            val priceBtc: Double?,
            val score: Int?,
            val slug: String?,
            val small: String?,
            val symbol: String?,
            val thumb: String?
        ) {
            data class Data(
                val content: Content?,
                val marketCap: String?,
                val marketCapBtc: String?,
                val price: Double?,
                val priceBtc: String?,
                val priceChangePercentage24h: Map<String, Double>?,
                val sparkline: String?,
                val totalVolume: String?,
                val totalVolumeBtc: String?
            ) {
                data class Content(
                    val description: String?,
                    val title: String?
                )
            }
        }
    }

    // ------------------ NFTs ------------------
    data class Nft(
        val data: Data?,
        val floorPrice24hPercentageChange: Double?,
        val floorPriceInNativeCurrency: Double?,
        val id: String?,
        val name: String?,
        val nativeCurrencySymbol: String?,
        val nftContractId: Int?,
        val symbol: String?,
        val thumb: String?
    ) {
        data class Data(
            val content: Content?,
            val floorPrice: String?,
            val floorPriceInUsd24hPercentageChange: String?,
            val h24AverageSalePrice: String?,
            val h24Volume: String?,
            val sparkline: String?
        ) {
            data class Content(
                val description: String?,
                val title: String?
            )
        }
    }
}
