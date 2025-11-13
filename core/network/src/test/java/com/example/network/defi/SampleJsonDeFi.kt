package com.example.network.defi


internal object SampleJsonDeFi {
    val defiResponse = """
        {
          "data": {
            "defi_market_cap": "105273842288.229620442228701667",
            "eth_market_cap": "406184911478.5772415794509920285",
            "defi_to_eth_ratio": "25.91771366026773",
            "trading_volume_24h": "5046503746.288261",
            "defi_dominance": "3.86765030846147",
            "top_coin_name": "Lido Staked Ether",
            "top_coin_defi_dominance": 30.589442518868,
            "unknown_field_child": "ignored"
          },
          "unknown_top": true
        }
    """.trimIndent()
}
