package com.daricx.markets.data

import com.example.model.coins.Coins


object CoinsFakeData {

    val coinsFakeData = listOf(
        coinsFakeModel("btc", "Bitcoin", "BTC", 1, 61234.56, 1_234_000_000_000.0, +2.34),
        coinsFakeModel("eth", "Ethereum", "ETH", 2, 3210.78, 380_000_000_000.0, -1.12),
        coinsFakeModel("sol", "Solana", "SOL", 5, 142.12, 66_000_000_000.0, +5.80),
        coinsFakeModel("xrp", "XRP", "XRP", 7, 0.62, 34_000_000_000.0, +0.45),
    )

    fun coinsFakeModel(
        id: String,
        name: String,
        symbol: String,
        rank: Int,
        price: Double,
        marketCap: Double,
        change24h: Double,
        sparkline: List<Double> = listOf(1.0, 1.1, 0.95, 1.2, 1.05, 1.15)
    ) = Coins(
        ath = null, athChangePercentage = null, athDate = null,
        atl = null, atlChangePercentage = null, atlDate = null,
        circulatingSupply = null, currentPrice = price,
        fullyDilutedValuation = null, high24h = null, id = id,
        image = null,
        lastUpdated = null, low24h = null, marketCap = marketCap,
        marketCapChange24h = null, marketCapChangePercentage24h = null,
        marketCapRank = rank, maxSupply = null, name = name,
        priceChange24h = null, priceChangePercentage24h = change24h, roi = null,
        symbol = symbol, totalSupply = null, totalVolume = null,
        sparklineIn7d = Coins.SparklineIn7d(price = sparkline)
    )
}


