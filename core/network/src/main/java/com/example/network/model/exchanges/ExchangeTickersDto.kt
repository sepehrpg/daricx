package com.example.network.model.exchanges


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
 data class ExchangeTickersDto(
    @SerialName("name")
    val name: String?,
    @SerialName("tickers")
    val tickers: List<Ticker?>?
) {
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