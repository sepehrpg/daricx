package com.daricx.markets.ui.data

import com.example.model.CoinMarket

object DemoData {
    fun demoItems(): List<CoinMarket> {
        return listOf(
            CoinMarket(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                marketCapRank = 1,
                marketCap = 1200000000000.0,
                currentPrice = 61234.56,
                priceChangePercentage24h = 2.34,
                image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
                ath = null, athChangePercentage = null, athDate = null,
                atl = null, atlChangePercentage = null, atlDate = null,
                circulatingSupply = null, fullyDilutedValuation = null,
                high24h = null, lastUpdated = null, low24h = null,
                marketCapChange24h = null, marketCapChangePercentage24h = null,
                maxSupply = null, priceChange24h = null, roi = null,
                totalSupply = null, totalVolume = null, sparklineIn7d = null
            )
        )
    }
}
