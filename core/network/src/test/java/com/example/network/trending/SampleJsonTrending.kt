package com.example.network.trending


internal object SampleJsonTrending {
    val response = """
        {
          "categories": [
            {
              "coins_count": 10,
              "data": {
                "market_cap": 1000000.0,
                "market_cap_btc": 50.0,
                "market_cap_change_percentage_24h": { "usd": 2.5 },
                "sparkline": "sparkline-data",
                "total_volume": 200000.0,
                "total_volume_btc": 10.0
              },
              "id": 1,
              "market_cap_1h_change": 0.5,
              "name": "DeFi",
              "slug": "defi"
            }
          ],
          "coins": [
            {
              "item": {
                "coin_id": 1,
                "data": {
                  "content": { "description": "Leading crypto", "title": "Bitcoin" },
                  "market_cap": "1000000000",
                  "market_cap_btc": "50000",
                  "price": 65234.12,
                  "price_btc": "1.0",
                  "price_change_percentage_24h": { "usd": 1.2 },
                  "sparkline": "sparkline",
                  "total_volume": "1000000",
                  "total_volume_btc": "500"
                },
                "id": "bitcoin",
                "large": "large.png",
                "market_cap_rank": 1,
                "name": "Bitcoin",
                "price_btc": 1.0,
                "score": 0,
                "slug": "bitcoin",
                "small": "small.png",
                "symbol": "BTC",
                "thumb": "thumb.png"
              }
            }
          ],
          "nfts": [
            {
              "data": {
                "content": { "description": "Top NFT", "title": "BAYC" },
                "floor_price": "10",
                "floor_price_in_usd_24h_percentage_change": "2.0",
                "h24_average_sale_price": "5",
                "h24_volume": "100",
                "sparkline": "sparkline"
              },
              "floor_price_24h_percentage_change": 1.5,
              "floor_price_in_native_currency": 0.01,
              "id": "bored-ape-yacht-club",
              "name": "Bored Ape Yacht Club",
              "native_currency_symbol": "ETH",
              "nft_contract_id": 123,
              "symbol": "BAYC",
              "thumb": "thumb.png"
            }
          ],
          "unknown": "ignore_me"
        }
    """.trimIndent()
}
