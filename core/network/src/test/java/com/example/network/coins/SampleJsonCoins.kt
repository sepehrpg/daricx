package com.example.network.coins


internal object SampleJsonCoins {
    // Minimal but realistic payload for /coins/markets
    val marketsResponse = """
        [
          {
            "id": "bitcoin",
            "symbol": "btc",
            "name": "Bitcoin",
            "image": "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
            "current_price": 65234.12,
            "market_cap": 1287654321987,
            "market_cap_rank": 1,
            "fully_diluted_valuation": 1360000000000,
            "total_volume": 2854321987,
            "high_24h": 66000.00,
            "low_24h": 64000.00,
            "price_change_24h": -123.456,
            "price_change_percentage_24h": -0.189,
            "market_cap_change_24h": -1000000000.0,
            "market_cap_change_percentage_24h": -0.08,
            "circulating_supply": 19500000.0,
            "total_supply": 21000000.0,
            "max_supply": 21000000.0,
            "ath": 73738.0,
            "ath_change_percentage": -11.54,
            "ath_date": "2024-03-14T12:00:00Z",
            "atl": 67.81,
            "atl_change_percentage": 96000.0,
            "atl_date": "2013-07-06T00:00:00Z",
            "roi": null,
            "last_updated": "2025-09-01T00:00:00Z",
            "sparkline_in_7d": { "price": [65000.0, 65100.5, 65234.12] },
            "unknown_field": "should_be_ignored"
          }
        ]
    """.trimIndent()
}
