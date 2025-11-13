package com.example.network.model.exchanges

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
 data class ExchangeDetailDto(
    @SerialName("alert_notice")
    val alertNotice: String?,
    @SerialName("centralized")
    val centralized: Boolean?,
    @SerialName("coins")
    val coins: Int?,
    @SerialName("country")
    val country: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("facebook_url")
    val facebookUrl: String?,
    @SerialName("has_trading_incentive")
    val hasTradingIncentive: Boolean?,
    @SerialName("image")
    val image: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("other_url_1")
    val otherUrl1: String?,
    @SerialName("other_url_2")
    val otherUrl2: String?,
    @SerialName("pairs")
    val pairs: Int?,
    @SerialName("public_notice")
    val publicNotice: String?,
    @SerialName("reddit_url")
    val redditUrl: String?,
    @SerialName("slack_url")
    val slackUrl: String?,
    @SerialName("status_updates")
    val statusUpdates: List<StatusUpdate?>?,
    @SerialName("telegram_url")
    val telegramUrl: String?,
    @SerialName("tickers")
    val tickers: List<Ticker?>?,
    @SerialName("trade_volume_24h_btc")
    val tradeVolume24hBtc: Double?,
    @SerialName("trust_score")
    val trustScore: Int?,
    @SerialName("trust_score_rank")
    val trustScoreRank: Int?,
    @SerialName("twitter_handle")
    val twitterHandle: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("year_established")
    val yearEstablished: Int?
) {
    @Serializable
     data class StatusUpdate(
        @SerialName("category")
        val category: String?,
        @SerialName("created_at")
        val createdAt: String?,
        @SerialName("description")
        val description: String?,
        @SerialName("pin")
        val pin: Boolean?,
        @SerialName("project")
        val project: Project?,
        @SerialName("user")
        val user: String?,
        @SerialName("user_title")
        val userTitle: String?
    ) {
        @Serializable
         data class Project(
            @SerialName("id")
            val id: String?,
            @SerialName("image")
            val image: Image?,
            @SerialName("name")
            val name: String?,
            @SerialName("type")
            val type: String?
        ) {
            @Serializable
             data class Image(
                @SerialName("large")
                val large: String?,
                @SerialName("small")
                val small: String?,
                @SerialName("thumb")
                val thumb: String?
            )
        }
    }

    @Serializable
     data class Ticker(
        @SerialName("base")
        val base: String?,
        @SerialName("bid_ask_spread_percentage")
        val bidAskSpreadPercentage: Double?,
        @SerialName("coin_id")
        val coinId: String?,
        @SerialName("coin_mcap_usd")
        val coinMcapUsd: Double?,
        @SerialName("converted_last")
        val convertedLast: ConvertedLast?,
        @SerialName("converted_volume")
        val convertedVolume: ConvertedVolume?,
        @SerialName("is_anomaly")
        val isAnomaly: Boolean?,
        @SerialName("is_stale")
        val isStale: Boolean?,
        @SerialName("last")
        val last: Double?,
        @SerialName("last_fetch_at")
        val lastFetchAt: String?,
        @SerialName("last_traded_at")
        val lastTradedAt: String?,
        @SerialName("market")
        val market: Market?,
        @SerialName("target")
        val target: String?,
        @SerialName("target_coin_id")
        val targetCoinId: String?,
        @SerialName("timestamp")
        val timestamp: String?,
        @SerialName("token_info_url")
        val tokenInfoUrl: JsonElement?,
        @SerialName("trade_url")
        val tradeUrl: String?,
        @SerialName("trust_score")
        val trustScore: String?,
        @SerialName("volume")
        val volume: Double?
    ) {
        @Serializable
         data class ConvertedLast(
            @SerialName("btc")
            val btc: Double?,
            @SerialName("eth")
            val eth: Double?,
            @SerialName("usd")
            val usd: Double?
        )

        @Serializable
         data class ConvertedVolume(
            @SerialName("btc")
            val btc: Double?,
            @SerialName("eth")
            val eth: Double?,
            @SerialName("usd")
            val usd: Double?
        )

        @Serializable
         data class Market(
            @SerialName("has_trading_incentive")
            val hasTradingIncentive: Boolean?,
            @SerialName("identifier")
            val identifier: String?,
            @SerialName("name")
            val name: String?
        )
    }
}