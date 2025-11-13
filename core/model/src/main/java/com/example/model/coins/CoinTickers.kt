package com.example.model.coins

 data class CoinTickers(
    val name: String?,
    val tickers: List<Ticker?>?
) {
     data class Ticker(
        val base: String?,
        val bidAskSpreadPercentage: Double?,
        val coinId: String?,
        val coinMcapUsd: Double?,
        val convertedLast: ConvertedLast?,
        val convertedVolume: ConvertedVolume?,
        val isAnomaly: Boolean?,
        val isStale: Boolean?,
        val last: Double?,
        val lastFetchAt: String?,
        val lastTradedAt: String?,
        val market: Market?,
        val target: String?,
        val targetCoinId: String?,
        val timestamp: String?,
        val tokenInfoUrl: Any?,
        val tradeUrl: String?,
        val trustScore: String?,
        val volume: Double?
    ) {
         data class ConvertedLast(
            val btc: Double?,
            val eth: Double?,
            val usd: Double?
        )

         data class ConvertedVolume(
            val btc: Double?,
            val eth: Double?,
            val usd: Double?
        )

         data class Market(
            val hasTradingIncentive: Boolean?,
            val identifier: String?,
            val name: String?
        )
    }
}