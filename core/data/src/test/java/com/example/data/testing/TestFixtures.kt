package com.example.data.testing

/** Simple JSON fixtures used in repository tests. */
object TestFixtures {
    const val coinMarketsJson = """
        [
          {
            "id":"bitcoin",
            "symbol":"btc",
            "name":"Bitcoin",
            "image":"img",
            "current_price":1.23
          }
        ]
    """

    const val exchangesJson = """
        [
          {
            "id":"binance",
            "name":"Binance",
            "country":"US",
            "description":"desc",
            "has_trading_incentive":false,
            "image":"img",
            "trade_volume_24h_btc":10.0,
            "trust_score":9,
            "trust_score_rank":1,
            "url":"https://binance.com",
            "year_established":2017
          }
        ]
    """
}
