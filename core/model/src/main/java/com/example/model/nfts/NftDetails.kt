package com.example.model.nfts

 data class NftDetails(
    val assetPlatformId: String?,
    val ath: Ath?,
    val athChangePercentage: AthChangePercentage?,
    val athDate: AthDate?,
    val bannerImage: String?,
    val contractAddress: String?,
    val description: String?,
    val explorers: List<Explorer?>?,
    val floorPrice: FloorPrice?,
    val floorPrice14dPercentageChange: FloorPrice14dPercentageChange?,
    val floorPrice1yPercentageChange: FloorPrice1yPercentageChange?,
    val floorPrice24hPercentageChange: FloorPrice24hPercentageChange?,
    val floorPrice30dPercentageChange: FloorPrice30dPercentageChange?,
    val floorPrice60dPercentageChange: FloorPrice60dPercentageChange?,
    val floorPrice7dPercentageChange: FloorPrice7dPercentageChange?,
    val floorPriceInUsd24hPercentageChange: Double?,
    val id: String?,
    val image: Image?,
    val links: Links?,
    val marketCap: MarketCap?,
    val marketCap24hPercentageChange: MarketCap24hPercentageChange?,
    val marketCapRank: Int?,
    val name: String?,
    val nativeCurrency: String?,
    val nativeCurrencySymbol: String?,
    val numberOfUniqueAddresses: Double?,
    val numberOfUniqueAddresses24hPercentageChange: Double?,
    val oneDayAverageSalePrice: Double?,
    val oneDayAverageSalePrice24hPercentageChange: Double?,
    val oneDaySales: Double?,
    val oneDaySales24hPercentageChange: Double?,
    val symbol: String?,
    val totalSupply: Double?,
    val userFavoritesCount: Int?,
    val volume24h: Volume24h?,
    val volume24hPercentageChange: Volume24hPercentageChange?,
    val volumeInUsd24hPercentageChange: Double?,
    val webSlug: String?
) {
     data class Ath(
        val nativeCurrency: Double?,
        val usd: Int?
    )

     data class AthChangePercentage(
        val nativeCurrency: Double?,
        val usd: Double?
    )

     data class AthDate(
        val nativeCurrency: String?,
        val usd: String?
    )

     data class Explorer(
        val link: String?,
        val name: String?
    )

     data class FloorPrice(
        val nativeCurrency: Double?,
        val usd: Double?
    )

     data class FloorPrice14dPercentageChange(
        val nativeCurrency: Double?,
        val usd: Double?
    )

     data class FloorPrice1yPercentageChange(
        val nativeCurrency: Double?,
        val usd: Double?
    )

     data class FloorPrice24hPercentageChange(
        val nativeCurrency: Double?,
        val usd: Double?
    )

     data class FloorPrice30dPercentageChange(
        val nativeCurrency: Double?,
        val usd: Double?
    )

     data class FloorPrice60dPercentageChange(
        val nativeCurrency: Double?,
        val usd: Double?
    )

     data class FloorPrice7dPercentageChange(
        val nativeCurrency: Double?,
        val usd: Double?
    )

     data class Image(
        val small: String?,
        val small2x: String?
    )

     data class Links(
        val discord: String?,
        val homepage: String?,
        val twitter: String?
    )

     data class MarketCap(
        val nativeCurrency: Int?,
        val usd: Double?
    )

     data class MarketCap24hPercentageChange(
        val nativeCurrency: Double?,
        val usd: Double?
    )

     data class Volume24h(
        val nativeCurrency: Double?,
        val usd: Int?
    )

     data class Volume24hPercentageChange(
        val nativeCurrency: Double?,
        val usd: Double?
    )
}