package com.example.network.exchanges


internal object SampleJsonExchanges {
    val exchangesResponse = """
        [
          {
            "id": "binance",
            "name": "Binance",
            "year_established": 2017,
            "country": "Cayman Islands",
            "description": "Popular exchange",
            "url": "https://www.binance.com/",
            "image": "https://assets.coingecko.com/markets/images/52/small/binance.jpg",
            "has_trading_incentive": false,
            "trust_score": 10,
            "trust_score_rank": 1,
            "trade_volume_24h_btc": 123456.789,
            "unknown_field": "ignored"
          }
        ]
    """.trimIndent()
}
