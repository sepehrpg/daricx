package com.example.network.companies


internal object SampleJsonCompanies {

    // Bitcoin sample
    val btcTreasuryResponse = """
        {
          "total_holdings": 264136.0,
          "total_value_usd": 18403306939.1513,
          "market_cap_dominance": 1.34,
          "companies": [
            {
              "name": "MicroStrategy Inc.",
              "symbol": "NASDAQ:MSTR",
              "country": "US",
              "total_holdings": 226164.0,
              "total_entry_value_usd": 8238000000.0,
              "total_current_value_usd": 14678000000.0,
              "percentage_of_total_supply": 1.075,
              "unknown_field": "ignore_me"
            }
          ],
          "unknown_top": true
        }
    """.trimIndent()

    val ethTreasuryResponse = """
        {
          "total_holdings": 915000.5,
          "total_value_usd": 2780033069.12,
          "market_cap_dominance": 0.48,
          "companies": [
            {
              "name": "Some Corp",
              "symbol": "NYSE:SOME",
              "country": "US",
              "total_holdings": 250000.0,
              "total_entry_value_usd": 350000000.0,
              "total_current_value_usd": 420000000.0,
              "percentage_of_total_supply": 0.021
            }
          ]
        }
    """.trimIndent()
}
