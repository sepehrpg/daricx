package com.example.network.nfts

internal object SampleJsonNfts {

    val listResponse = """
        [
          {
            "id": "bored-ape-yacht-club",
            "name": "Bored Ape Yacht Club",
            "symbol": "BAYC",
            "asset_platform_id": "ethereum",
            "contract_address": "0xbc4ca0eda7647a8ab7c2061c2e118a18a936f13d",
            "unknown": "ignore_me"
          }
        ]
    """.trimIndent()

    // Minimal but representative NFT details payload
    val nftDetailResponse = """
        {
          "id": "bored-ape-yacht-club",
          "name": "Bored Ape Yacht Club",
          "symbol": "BAYC",
          "asset_platform_id": "ethereum",
          "contract_address": "0xbc4ca0eda7647a8ab7c2061c2e118a18a936f13d",
          "description": "Blue-chip NFT collection",
          "image": {
            "small": "https://example.com/bayc-small.png",
            "large": "https://example.com/bayc-large.png"
          },
          "floor_price": {
            "usd": 24.5
          },
          "market_cap": {
            "usd": 3000000000.0
          },
          "total_volume": {
            "usd": 10000000.0
          },
          "number_of_unique_addresses": 6000,
          "total_supply": 10000.0,
          "unknown_field": "ignore_me"
        }
    """.trimIndent()
}
