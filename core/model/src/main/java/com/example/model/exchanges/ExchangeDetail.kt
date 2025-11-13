package com.example.model.exchanges

 data class ExchangeDetail(
    val alertNotice: String?,
    val centralized: Boolean?,
    val coins: Int?,
    val country: String?,
    val description: String?,
    val facebookUrl: String?,
    val hasTradingIncentive: Boolean?,
    val image: String?,
    val name: String?,
    val otherUrl1: String?,
    val otherUrl2: String?,
    val pairs: Int?,
    val publicNotice: String?,
    val redditUrl: String?,
    val slackUrl: String?,
    val statusUpdates: List<StatusUpdate?>?,
    val telegramUrl: String?,
    val tickers: List<Ticker?>?,
    val tradeVolume24hBtc: Double?,
    val trustScore: Int?,
    val trustScoreRank: Int?,
    val twitterHandle: String?,
    val url: String?,
    val yearEstablished: Int?
) {
     data class StatusUpdate(
        val category: String?,
        val createdAt: String?,
        val description: String?,
        val pin: Boolean?,
        val project: Project?,
        val user: String?,
        val userTitle: String?
    ) {
         data class Project(
            val id: String?,
            val image: Image?,
            val name: String?,
            val type: String?
        ) {
             data class Image(
                val large: String?,
                val small: String?,
                val thumb: String?
            )
        }
    }

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