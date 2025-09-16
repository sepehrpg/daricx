package com.example.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingDto(
    val categories: List<Category>?,
    val coins: List<Coin>?,
    val nfts: List<Nft>?
) {
    // ------------------ Categories ------------------
    @Serializable
    data class Category(
        @SerialName("coins_count")
        val coinsCount: Int?,
        val data: Data?,
        val id: Int?,
        @SerialName("market_cap_1h_change")
        val marketCap1hChange: Double?,
        val name: String?,
        val slug: String?
    ) {
        @Serializable
        data class Data(
            @SerialName("market_cap")
            val marketCap: Double?,
            @SerialName("market_cap_btc")
            val marketCapBtc: Double?,
            @SerialName("market_cap_change_percentage_24h")
            val marketCapChangePercentage24h: Map<String, Double>?,
            val sparkline: String?,
            @SerialName("total_volume")
            val totalVolume: Double?,
            @SerialName("total_volume_btc")
            val totalVolumeBtc: Double?
        )
    }

    // ------------------ Coins ------------------
    @Serializable
    data class Coin(
        val item: Item?
    ) {
        @Serializable
        data class Item(
            @SerialName("coin_id")
            val coinId: Int?,
            val data: Data?,
            val id: String?,
            val large: String?,
            @SerialName("market_cap_rank")
            val marketCapRank: Int?,
            val name: String?,
            @SerialName("price_btc")
            val priceBtc: Double?,
            val score: Int?,
            val slug: String?,
            val small: String?,
            val symbol: String?,
            val thumb: String?
        ) {
            @Serializable
            data class Data(
                val content: Content?,
                @SerialName("market_cap")
                val marketCap: String?,
                @SerialName("market_cap_btc")
                val marketCapBtc: String?,
                val price: Double?,
                @SerialName("price_btc")
                val priceBtc: String?,
                @SerialName("price_change_percentage_24h")
                val priceChangePercentage24h: Map<String, Double>?,
                val sparkline: String?,
                @SerialName("total_volume")
                val totalVolume: String?,
                @SerialName("total_volume_btc")
                val totalVolumeBtc: String?
            ) {
                @Serializable
                data class Content(
                    val description: String?,
                    val title: String?
                )
            }
        }
    }
    // ------------------ NFTs ------------------
    @Serializable
    data class Nft(
        val data: Data?,
        @SerialName("floor_price_24h_percentage_change")
        val floorPrice24hPercentageChange: Double?,
        @SerialName("floor_price_in_native_currency")
        val floorPriceInNativeCurrency: Double?,
        val id: String?,
        val name: String?,
        @SerialName("native_currency_symbol")
        val nativeCurrencySymbol: String?,
        @SerialName("nft_contract_id")
        val nftContractId: Int?,
        val symbol: String?,
        val thumb: String?
    ) {
        @Serializable
        data class Data(
            val content: Content? = null,
            @SerialName("floor_price")
            val floorPrice: String?,
            @SerialName("floor_price_in_usd_24h_percentage_change")
            val floorPriceInUsd24hPercentageChange: String?,
            @SerialName("h24_average_sale_price")
            val h24AverageSalePrice: String?,
            @SerialName("h24_volume")
            val h24Volume: String?,
            val sparkline: String?
        ) {
            @Serializable
            data class Content(
                val description: String? = null,
                val title: String? = null
            )
        }
    }
}



