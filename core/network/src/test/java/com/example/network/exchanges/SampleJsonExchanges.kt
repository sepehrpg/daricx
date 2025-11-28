package com.example.network.exchanges

internal object SampleJsonExchanges {

    // /exchanges
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

    // /exchanges/{id}
    val exchangeDetailResponse = """
        {
          "id": "binance",
          "name": "Binance",
          "year_established": 2017,
          "country": "Cayman Islands",
          "description": "Popular exchange",
          "url": "https://www.binance.com/",
          "image": "https://assets.coingecko.com/markets/images/52/small/binance.jpg",
          "facebook_url": "https://facebook.com/binance",
          "reddit_url": "https://reddit.com/r/binance",
          "telegram_url": "https://t.me/binanceexchange",
          "twitter_handle": "binance",
          "centralized": true,
          "trust_score": 10,
          "trust_score_rank": 1,
          "trade_volume_24h_btc": 123456.789,
          "unknown_field": "ignored"
        }
    """.trimIndent()

    // /exchanges/{id}/tickers
    val exchangeTickersResponse = """
        {
          "name": "Binance",
          "tickers": [
            {
              "base": "BTC",
              "target": "USDT",
              "market": {
                "name": "Binance",
                "identifier": "binance",
                "has_trading_incentive": false
              },
              "last": 50000.0,
              "volume": 100000.0,
              "trust_score": "green",
              "bid_ask_spread_percentage": 0.01,
              "is_anomaly": false,
              "is_stale": false,
              "timestamp": "2025-01-01T00:00:00Z",
              "last_traded_at": "2025-01-01T00:01:00Z",
              "last_fetch_at": "2025-01-01T00:02:00Z",
              "trade_url": "https://binance.com/trade/BTC_USDT",
              "token_info_url": null,
              "coin_id": "bitcoin",
              "target_coin_id": "tether"
            }
          ]
        }
    """.trimIndent()

    // /exchanges/{id}/volume_chart
    val exchangeVolumeChartResponse = """
        [
          [1711929600000, 123456.789],
          [1712016000000, 234567.890]
        ]
    """.trimIndent()
}
