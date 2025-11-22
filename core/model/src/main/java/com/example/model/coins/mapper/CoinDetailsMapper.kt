package com.example.model.coins.mapper

import com.example.model.coins.CoinDetails
import com.example.model.coins.FavoriteCoin
import com.example.model.option.CryptoTimeRange

fun CoinDetails.toFavoriteCoin(): FavoriteCoin {
    return FavoriteCoin(
        id = id?:"",
        symbol = symbol,
        name = name,
        imageUrl = image?.small,
    )
}

fun CoinDetails.percentChangeFor(range: CryptoTimeRange): Double {
    val data = this.marketData ?: return 0.0

    return when (range) {
        CryptoTimeRange.H24 -> data.priceChangePercentage24h ?: 0.0
        CryptoTimeRange.D7  -> data.priceChangePercentage7d ?: 0.0
        CryptoTimeRange.M1  -> data.priceChangePercentage30d ?: 0.0
        CryptoTimeRange.M3  -> data.priceChangePercentage60d ?: 0.0
        CryptoTimeRange.Y1  -> data.priceChangePercentage1y ?: 0.0
    }
}