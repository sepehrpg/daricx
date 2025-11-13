package com.example.network.search


internal object SampleJsonSearch {
    val response = """
        {
          "coins": [
            {
              "id": "bitcoin",
              "name": "Bitcoin",
              "api_symbol": "bitcoin",
              "symbol": "btc",
              "market_cap_rank": 1,
              "thumb": "https://assets.coingecko.com/coins/images/1/thumb/bitcoin.png",
              "large": "https://assets.coingecko.com/coins/images/1/large/bitcoin.png"
            }
          ],
          "exchanges": [
            {
              "id": "binance",
              "name": "Binance",
              "market_type": "spot",
              "thumb": "t",
              "large": "l"
            }
          ],
          "icos": ["ico1"],
          "categories": [
            {
              "id": "defi",
              "name": "DeFi"
            }
          ],
          "nfts": [
            {
              "id": "bored-ape-yacht-club",
              "name": "Bored Ape Yacht Club",
              "symbol": "BAYC",
              "thumb": "thumb.png"
            }
          ],
          "unknown": "ignore_me"
        }
    """.trimIndent()
}
