package com.example.network.model.nfts


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
 data class NftDetailsDto(
    @SerialName("asset_platform_id")
    val assetPlatformId: String?,
    @SerialName("ath")
    val ath: Ath?,
    @SerialName("ath_change_percentage")
    val athChangePercentage: AthChangePercentage?,
    @SerialName("ath_date")
    val athDate: AthDate?,
    @SerialName("banner_image")
    val bannerImage: String?,
    @SerialName("contract_address")
    val contractAddress: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("explorers")
    val explorers: List<Explorer?>?,
    @SerialName("floor_price")
    val floorPrice: FloorPrice?,
    @SerialName("floor_price_14d_percentage_change")
    val floorPrice14dPercentageChange: FloorPrice14dPercentageChange?,
    @SerialName("floor_price_1y_percentage_change")
    val floorPrice1yPercentageChange: FloorPrice1yPercentageChange?,
    @SerialName("floor_price_24h_percentage_change")
    val floorPrice24hPercentageChange: FloorPrice24hPercentageChange?,
    @SerialName("floor_price_30d_percentage_change")
    val floorPrice30dPercentageChange: FloorPrice30dPercentageChange?,
    @SerialName("floor_price_60d_percentage_change")
    val floorPrice60dPercentageChange: FloorPrice60dPercentageChange?,
    @SerialName("floor_price_7d_percentage_change")
    val floorPrice7dPercentageChange: FloorPrice7dPercentageChange?,
    @SerialName("floor_price_in_usd_24h_percentage_change")
    val floorPriceInUsd24hPercentageChange: Double?,
    @SerialName("id")
    val id: String?,
    @SerialName("image")
    val image: Image?,
    @SerialName("links")
    val links: Links?,
    @SerialName("market_cap")
    val marketCap: MarketCap?,
    @SerialName("market_cap_24h_percentage_change")
    val marketCap24hPercentageChange: MarketCap24hPercentageChange?,
    @SerialName("market_cap_rank")
    val marketCapRank: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("native_currency")
    val nativeCurrency: String?,
    @SerialName("native_currency_symbol")
    val nativeCurrencySymbol: String?,
    @SerialName("number_of_unique_addresses")
    val numberOfUniqueAddresses: Double?,
    @SerialName("number_of_unique_addresses_24h_percentage_change")
    val numberOfUniqueAddresses24hPercentageChange: Double?,
    @SerialName("one_day_average_sale_price")
    val oneDayAverageSalePrice: Double?,
    @SerialName("one_day_average_sale_price_24h_percentage_change")
    val oneDayAverageSalePrice24hPercentageChange: Double?,
    @SerialName("one_day_sales")
    val oneDaySales: Double?,
    @SerialName("one_day_sales_24h_percentage_change")
    val oneDaySales24hPercentageChange: Double?,
    @SerialName("symbol")
    val symbol: String?,
    @SerialName("total_supply")
    val totalSupply: Double?,
    @SerialName("user_favorites_count")
    val userFavoritesCount: Int?,
    @SerialName("volume_24h")
    val volume24h: Volume24h?,
    @SerialName("volume_24h_percentage_change")
    val volume24hPercentageChange: Volume24hPercentageChange?,
    @SerialName("volume_in_usd_24h_percentage_change")
    val volumeInUsd24hPercentageChange: Double?,
    @SerialName("web_slug")
    val webSlug: String?
) {
    @Serializable
     data class Ath(
        @SerialName("native_currency")
        val nativeCurrency: Double?,
        @SerialName("usd")
        val usd: Int?
    )

    @Serializable
     data class AthChangePercentage(
        @SerialName("native_currency")
        val nativeCurrency: Double?,
        @SerialName("usd")
        val usd: Double?
    )

    @Serializable
     data class AthDate(
        @SerialName("native_currency")
        val nativeCurrency: String?,
        @SerialName("usd")
        val usd: String?
    )

    @Serializable
     data class Explorer(
        @SerialName("link")
        val link: String?,
        @SerialName("name")
        val name: String?
    )

    @Serializable
     data class FloorPrice(
        @SerialName("native_currency")
        val nativeCurrency: Double?,
        @SerialName("usd")
        val usd: Int?
    )

    @Serializable
     data class FloorPrice14dPercentageChange(
        @SerialName("native_currency")
        val nativeCurrency: Double?,
        @SerialName("usd")
        val usd: Double?
    )

    @Serializable
     data class FloorPrice1yPercentageChange(
        @SerialName("native_currency")
        val nativeCurrency: Double?,
        @SerialName("usd")
        val usd: Double?
    )

    @Serializable
     data class FloorPrice24hPercentageChange(
        @SerialName("native_currency")
        val nativeCurrency: Double?,
        @SerialName("usd")
        val usd: Double?
    )

    @Serializable
     data class FloorPrice30dPercentageChange(
        @SerialName("native_currency")
        val nativeCurrency: Double?,
        @SerialName("usd")
        val usd: Double?
    )

    @Serializable
     data class FloorPrice60dPercentageChange(
        @SerialName("native_currency")
        val nativeCurrency: Double?,
        @SerialName("usd")
        val usd: Double?
    )

    @Serializable
     data class FloorPrice7dPercentageChange(
        @SerialName("native_currency")
        val nativeCurrency: Double?,
        @SerialName("usd")
        val usd: Double?
    )

    @Serializable
     data class Image(
        @SerialName("small")
        val small: String?,
        @SerialName("small_2x")
        val small2x: String?
    )

    @Serializable
     data class Links(
        @SerialName("discord")
        val discord: String?,
        @SerialName("homepage")
        val homepage: String?,
        @SerialName("twitter")
        val twitter: String?
    )

    @Serializable
     data class MarketCap(
        @SerialName("native_currency")
        val nativeCurrency: Int?,
        @SerialName("usd")
        val usd: Int?
    )

    @Serializable
     data class MarketCap24hPercentageChange(
        @SerialName("native_currency")
        val nativeCurrency: Double?,
        @SerialName("usd")
        val usd: Double?
    )

    @Serializable
     data class Volume24h(
        @SerialName("native_currency")
        val nativeCurrency: Double?,
        @SerialName("usd")
        val usd: Int?
    )

    @Serializable
     data class Volume24hPercentageChange(
        @SerialName("native_currency")
        val nativeCurrency: Double?,
        @SerialName("usd")
        val usd: Double?
    )
}