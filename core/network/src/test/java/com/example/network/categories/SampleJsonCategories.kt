package com.example.network.categories


internal object SampleJsonCategories {
    val categoriesResponse = """
        [
          {
            "id": "decentralized-finance-defi",
            "name": "Decentralized Finance (DeFi)",
            "market_cap": 123456789.12,
            "market_cap_change_24h": 1.23,
            "volume_24h": 9876543.21,
            "top_3_coins": [
              "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
              "https://assets.coingecko.com/coins/images/279/large/ethereum.png",
              "https://assets.coingecko.com/coins/images/4128/large/solana.png"
            ],
            "top_3_coins_id": ["bitcoin","ethereum","solana"],
            "content": "Some markdown/html content",
            "updated_at": "2025-09-01T00:00:00Z",
            "unknown_field": "should_be_ignored"
          }
        ]
    """.trimIndent()
}
