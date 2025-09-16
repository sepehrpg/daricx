package com.example.network.model.mappers

import com.example.model.Coins
import com.example.model.sort.CoinsSort
import com.example.network.model.CoinsDto

/**
 * Maps the network DTO for ROI to the domain model for ROI.
 * It provides default values for any null fields from the API.
 */
fun CoinsDto.Roi.toDomain(): Coins.Roi {
    return Coins.Roi(
        currency = this.currency ?: "",
        percentage = this.percentage ?: 0.0,
        times = this.times ?: 0.0
    )
}


fun CoinsDto.SparklineIn7d.toDomain(): Coins.SparklineIn7d {
    return Coins.SparklineIn7d(
        price = this.price
    )
}

/**
 * Maps the main network DTO for a coin's market data to the domain model.
 *
 * This function is designed to be safe:
 * - It returns `null` if the DTO is considered invalid (e.g., if the `id` is missing).
 * - It provides sensible default values for any other nullable fields.
 */
fun CoinsDto.toDomain(): Coins {
    return Coins(
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
        totalVolume = this.totalVolume,
        sparklineIn7d = this.sparklineIn7d?.toDomain()

    )
}

/**
 * Maps a list of network DTOs to a list of domain models.
 * It uses `mapNotNull` to automatically filter out any invalid DTOs that result in a null domain model.
 */
fun List<CoinsDto>.toDomain(): List<Coins> {
    return this.map { it.toDomain() }
}






