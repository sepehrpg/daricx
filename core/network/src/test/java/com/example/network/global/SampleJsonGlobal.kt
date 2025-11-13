package com.example.network.global


internal object SampleJsonGlobal {

    // Minimal but representative response
    val globalResponse = """
        {
          "data": {
            "active_cryptocurrencies": 12045,
            "ongoing_icos": 12,
            "ended_icos": 3400,
            "markets": 830,
            "total_market_cap": {
              "usd": 2350000000000.0,
              "btc": 38000000.123,
              "eth": 1250000000.456
            },
            "total_volume": {
              "usd": 120000000000.0
            },
            "market_cap_percentage": {
              "btc": 52.1,
              "eth": 17.3
            },
            "market_cap_change_percentage_24h_usd": -0.56,
            "updated_at": 1725148800
          }
        }
    """.trimIndent()

    // Same as above + an unknown field to assert ignoreUnknownKeys works
    val globalResponseWithUnknown = """
        {
          "data": {
            "active_cryptocurrencies": 12045,
            "ongoing_icos": 12,
            "ended_icos": 3400,
            "markets": 830,
            "total_market_cap": {
              "usd": 2350000000000.0,
              "btc": 38000000.123
            },
            "total_volume": {
              "usd": 120000000000.0
            },
            "market_cap_percentage": {
              "btc": 52.1,
              "eth": 17.3
            },
            "market_cap_change_percentage_24h_usd": -0.56,
            "updated_at": 1725148800,
            "unknown_field": "ignore_me"
          }
        }
    """.trimIndent()
}
