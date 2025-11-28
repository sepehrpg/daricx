package com.example.network.categories



/**
 * Sample JSON payloads for /coins/categories endpoint.
 */
internal object SampleJsonCategories {

    // Minimal but valid categories payload based on CoinGecko /coins/categories shape
    const val categoriesResponse = """
        [
          {
            "id": "layer-1",
            "name": "Layer 1 (L1)",
            "market_cap": 150000000000.0,
            "market_cap_change_24h": 2.5,
            "volume_24h": 5000000000.0,
            "top_3_coins": [
              "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
              "https://assets.coingecko.com/coins/images/279/large/ethereum.png",
              "https://assets.coingecko.com/coins/images/325/large/Tether-logo.png"
            ],
            "updated_at": "2025-09-01T00:00:00Z"
          },
          {
            "id": "defi",
            "name": "Decentralized Finance (DeFi)",
            "market_cap": 80000000000.0,
            "market_cap_change_24h": -1.2,
            "volume_24h": 3000000000.0,
            "top_3_coins": [
              "https://assets.coingecko.com/coins/images/877/large/chainlink-new-logo.png",
              "https://assets.coingecko.com/coins/images/3406/large/SNX.png",
              "https://assets.coingecko.com/coins/images/12504/large/uni.jpg"
            ],
            "updated_at": "2025-09-01T00:05:00Z"
          }
        ]
    """
}
