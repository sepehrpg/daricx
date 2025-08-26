package com.example.network.model


import com.example.model.CoinMarket
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias CoinMarketsDto = List<CoinMarketDto>

@Serializable
data class CoinMarketDto(
   @SerialName("ath")
   val ath: Double?,
   @SerialName("ath_change_percentage")
   val athChangePercentage: Double?,
   @SerialName("ath_date")
   val athDate: String?,
   @SerialName("atl")
   val atl: Double?,
   @SerialName("atl_change_percentage")
   val atlChangePercentage: Double?,
   @SerialName("atl_date")
   val atlDate: String?,
   @SerialName("circulating_supply")
   val circulatingSupply: Double?,
   @SerialName("current_price")
   val currentPrice: Double?,
   @SerialName("fully_diluted_valuation")
   val fullyDilutedValuation: Long?,
   @SerialName("high_24h")
   val high24h: Double?,
   @SerialName("id")
   val id: String?,
   @SerialName("image")
   val image: String?,
   @SerialName("last_updated")
   val lastUpdated: String?,
   @SerialName("low_24h")
   val low24h: Double?,
   @SerialName("market_cap")
   val marketCap: Long?,
   @SerialName("market_cap_change_24h")
   val marketCapChange24h: Double?,
   @SerialName("market_cap_change_percentage_24h")
   val marketCapChangePercentage24h: Double?,
   @SerialName("market_cap_rank")
   val marketCapRank: Int?,
   @SerialName("max_supply")
   val maxSupply: Double?,
   @SerialName("name")
   val name: String?,
   @SerialName("price_change_24h")
   val priceChange24h: Double?,
   @SerialName("price_change_percentage_24h")
   val priceChangePercentage24h: Double?,
   @SerialName("roi")
   val roi: Roi?,
   @SerialName("symbol")
   val symbol: String?,
   @SerialName("total_supply")
   val totalSupply: Double?,
   @SerialName("total_volume")
   val totalVolume: Double?
) {
   @Serializable
   data class Roi(
      @SerialName("currency")
      val currency: String?,
      @SerialName("percentage")
      val percentage: Double?,
      @SerialName("times")
      val times: Double?
   )
}

/**
 * Maps the network DTO for ROI to the domain model for ROI.
 * It provides default values for any null fields from the API.
 */
fun CoinMarketDto.Roi.toDomain(): CoinMarket.Roi {
   return CoinMarket.Roi(
      currency = this.currency ?: "",
      percentage = this.percentage ?: 0.0,
      times = this.times ?: 0.0
   )
}

/**
 * Maps the main network DTO for a coin's market data to the domain model.
 *
 * This function is designed to be safe:
 * - It returns `null` if the DTO is considered invalid (e.g., if the `id` is missing).
 * - It provides sensible default values for any other nullable fields.
 */
fun CoinMarketDto.toDomain(): CoinMarket {
   return CoinMarket(
      ath = this.ath,
      athChangePercentage = this.athChangePercentage,
      athDate = this.athDate,
      atl = this.atl,
      atlChangePercentage = this.atlChangePercentage,
      atlDate = this.atlDate,
      circulatingSupply = this.circulatingSupply,
      currentPrice = this.currentPrice,
      fullyDilutedValuation = this.fullyDilutedValuation,
      high24h = this.high24h,
      id = this.id,
      image = this.image,
      lastUpdated = this.lastUpdated,
      low24h = this.low24h,
      marketCap = this.marketCap,
      marketCapChange24h = this.marketCapChange24h,
      marketCapChangePercentage24h = this.marketCapChangePercentage24h,
      marketCapRank = this.marketCapRank,
      maxSupply = this.maxSupply,
      name = this.name,
      priceChange24h = this.priceChange24h,
      priceChangePercentage24h = this.priceChangePercentage24h,
      // Call the mapper for the nested Roi object, providing a default if the DTO's roi is null.
      roi = this.roi?.toDomain(),
      symbol = this.symbol,
      totalSupply = this.totalSupply,
      totalVolume = this.totalVolume
   )
}

/**
 * Maps a list of network DTOs to a list of domain models.
 * It uses `mapNotNull` to automatically filter out any invalid DTOs that result in a null domain model.
 */
fun List<CoinMarketDto>.toDomain(): List<CoinMarket> {
   return this.map { it.toDomain() }
}