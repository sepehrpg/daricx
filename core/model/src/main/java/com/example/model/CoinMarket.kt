package com.example.model


 data class CoinMarket(
    val ath: Double?,
    val athChangePercentage: Double?,
    val athDate: String?,
    val atl: Double?,
    val atlChangePercentage: Double?,
    val atlDate: String?,
    val circulatingSupply: Double?,
    val currentPrice: Double?,
    val fullyDilutedValuation: Double?,
    val high24h: Double?,
    val id: String?,
    val image: String?,
    val lastUpdated: String?,
    val low24h: Double?,
    val marketCap: Double?,
    val marketCapChange24h: Double?,
    val marketCapChangePercentage24h: Double?,
    val marketCapRank: Int?,
    val maxSupply: Double?,
    val name: String?,
    val priceChange24h: Double?,
    val priceChangePercentage24h: Double?,
    val roi: Roi?,
    val symbol: String?,
    val totalSupply: Double?,
    val totalVolume: Double?,
    val sparklineIn7d: SparklineIn7d?
) {
     data class Roi(
        val currency: String?,
        val percentage: Double?,
        val times: Double?
    )

    data class SparklineIn7d(
       val price: List<Double>
    )
}


enum class MarketSortColumn {
   RANK,
   MARKET_CAP,
   VOLUME,
   ID,
   PRICE, // Not Supported Server Side
   CHANGE_24H // Not Supported Server Side
}
enum class SortDirection { ASC, DESC }

data class SortSpec(
   val column: MarketSortColumn,
   val direction: SortDirection
)