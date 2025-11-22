package com.example.network.model.coins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class CoinDetailsDto(
    @SerialName("additional_notices")
    val additionalNotices: List<JsonElement?>? = null,
    @SerialName("asset_platform_id")
    val assetPlatformId: JsonElement? = null,
    @SerialName("block_time_in_minutes")
    val blockTimeInMinutes: Int? = null,
    @SerialName("categories")
    val categories: List<String?>? = null,
    @SerialName("community_data")
    val communityData: CommunityData? = null,
    @SerialName("country_origin")
    val countryOrigin: String? = null,

    // description: {"en": "...", "de": "...", ...}
    @SerialName("description")
    val description: Map<String, String?>? = null,

    // detail_platforms: {"ethereum": { contract_address, decimal_place }, ...}
    @SerialName("detail_platforms")
    val detailPlatforms: Map<String, DetailPlatformDto?>? = null,

    @SerialName("developer_data")
    val developerData: DeveloperData? = null,
    @SerialName("genesis_date")
    val genesisDate: String? = null,
    @SerialName("hashing_algorithm")
    val hashingAlgorithm: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("image")
    val image: Image? = null,
    @SerialName("last_updated")
    val lastUpdated: String? = null,
    @SerialName("links")
    val links: Links? = null,

    // localization: {"en": "Bitcoin", "de": "Bitcoin", ...}
    @SerialName("localization")
    val localization: Map<String, String?>? = null,

    @SerialName("market_cap_rank")
    val marketCapRank: Int? = null,
    @SerialName("market_data")
    val marketData: MarketData? = null,
    @SerialName("name")
    val name: String? = null,

    // platforms: {"ethereum": "0x...", "binance-smart-chain": "0x...", ...}
    @SerialName("platforms")
    val platforms: Map<String, String?>? = null,

    @SerialName("preview_listing")
    val previewListing: Boolean? = null,
    @SerialName("public_notice")
    val publicNotice: JsonElement? = null,
    @SerialName("sentiment_votes_down_percentage")
    val sentimentVotesDownPercentage: Double? = null,
    @SerialName("sentiment_votes_up_percentage")
    val sentimentVotesUpPercentage: Double? = null,
    @SerialName("status_updates")
    val statusUpdates: List<JsonElement?>? = null,
    @SerialName("symbol")
    val symbol: String? = null,
    @SerialName("tickers")
    val tickers: List<Ticker?>? = null,
    @SerialName("watchlist_portfolio_users")
    val watchlistPortfolioUsers: Int? = null,
    @SerialName("web_slug")
    val webSlug: String? = null
) {

    @Serializable
    data class CommunityData(
        @SerialName("facebook_likes")
        val facebookLikes: JsonElement? = null,
        @SerialName("reddit_accounts_active_48h")
        val redditAccountsActive48h: Int? = null,
        @SerialName("reddit_average_comments_48h")
        val redditAverageComments48h: Double? = null,
        @SerialName("reddit_average_posts_48h")
        val redditAveragePosts48h: Double? = null,
        @SerialName("reddit_subscribers")
        val redditSubscribers: Int? = null,
        @SerialName("telegram_channel_user_count")
        val telegramChannelUserCount: JsonElement? = null
    )

    @Serializable
    data class DetailPlatformDto(
        @SerialName("contract_address")
        val contractAddress: String? = null,
        @SerialName("decimal_place")
        val decimalPlace: JsonElement? = null
    )

    @Serializable
    data class DeveloperData(
        @SerialName("closed_issues")
        val closedIssues: Int? = null,
        @SerialName("code_additions_deletions_4_weeks")
        val codeAdditionsDeletions4Weeks: CodeAdditionsDeletions4Weeks? = null,
        @SerialName("commit_count_4_weeks")
        val commitCount4Weeks: Int? = null,
        @SerialName("forks")
        val forks: Int? = null,
        @SerialName("last_4_weeks_commit_activity_series")
        val last4WeeksCommitActivitySeries: List<JsonElement?>? = null,
        @SerialName("pull_request_contributors")
        val pullRequestContributors: Int? = null,
        @SerialName("pull_requests_merged")
        val pullRequestsMerged: Int? = null,
        @SerialName("stars")
        val stars: Int? = null,
        @SerialName("subscribers")
        val subscribers: Int? = null,
        @SerialName("total_issues")
        val totalIssues: Int? = null
    ) {
        @Serializable
        data class CodeAdditionsDeletions4Weeks(
            @SerialName("additions")
            val additions: Int? = null,
            @SerialName("deletions")
            val deletions: Int? = null
        )
    }

    @Serializable
    data class Image(
        @SerialName("large")
        val large: String? = null,
        @SerialName("small")
        val small: String? = null,
        @SerialName("thumb")
        val thumb: String? = null
    )

    @Serializable
    data class Links(
        @SerialName("announcement_url")
        val announcementUrl: List<JsonElement?>? = null,
        @SerialName("bitcointalk_thread_identifier")
        val bitcointalkThreadIdentifier: JsonElement? = null,
        @SerialName("blockchain_site")
        val blockchainSite: List<String?>? = null,
        @SerialName("chat_url")
        val chatUrl: List<JsonElement?>? = null,
        @SerialName("facebook_username")
        val facebookUsername: String? = null,
        @SerialName("homepage")
        val homepage: List<String?>? = null,
        @SerialName("official_forum_url")
        val officialForumUrl: List<String?>? = null,
        @SerialName("repos_url")
        val reposUrl: ReposUrl? = null,
        @SerialName("snapshot_url")
        val snapshotUrl: JsonElement? = null,
        @SerialName("subreddit_url")
        val subredditUrl: String? = null,
        @SerialName("telegram_channel_identifier")
        val telegramChannelIdentifier: String? = null,
        @SerialName("twitter_screen_name")
        val twitterScreenName: String? = null,
        @SerialName("whitepaper")
        val whitepaper: String? = null
    ) {
        @Serializable
        data class ReposUrl(
            @SerialName("bitbucket")
            val bitbucket: List<JsonElement?>? = null,
            @SerialName("github")
            val github: List<String?>? = null
        )
    }

    @Serializable
    data class MarketData(
        @SerialName("ath")
        val ath: Map<String, Double?>? = null,
        @SerialName("ath_change_percentage")
        val athChangePercentage: Map<String, Double?>? = null,
        @SerialName("ath_date")
        val athDate: Map<String, String?>? = null,
        @SerialName("atl")
        val atl: Map<String, Double?>? = null,
        @SerialName("atl_change_percentage")
        val atlChangePercentage: Map<String, Double?>? = null,
        @SerialName("atl_date")
        val atlDate: Map<String, String?>? = null,
        @SerialName("circulating_supply")
        val circulatingSupply: Double? = null,
        @SerialName("current_price")
        val currentPrice: Map<String, Double?>? = null,
        @SerialName("fdv_to_tvl_ratio")
        val fdvToTvlRatio: JsonElement? = null,
        @SerialName("fully_diluted_valuation")
        val fullyDilutedValuation: Map<String, Double?>? = null,
        @SerialName("high_24h")
        val high24h: Map<String, Double?>? = null,
        @SerialName("last_updated")
        val lastUpdated: String? = null,
        @SerialName("low_24h")
        val low24h: Map<String, Double?>? = null,
        @SerialName("market_cap")
        val marketCap: Map<String, Double?>? = null,
        @SerialName("market_cap_change_24h")
        val marketCapChange24h: Double? = null,
        @SerialName("market_cap_change_24h_in_currency")
        val marketCapChange24hInCurrency: Map<String, Double?>? = null,
        @SerialName("market_cap_change_percentage_24h")
        val marketCapChangePercentage24h: Double? = null,
        @SerialName("market_cap_change_percentage_24h_in_currency")
        val marketCapChangePercentage24hInCurrency: Map<String, Double?>? = null,
        @SerialName("market_cap_fdv_ratio")
        val marketCapFdvRatio: Double? = null,
        @SerialName("market_cap_rank")
        val marketCapRank: Int? = null,
        @SerialName("max_supply")
        val maxSupply: Double? = null,
        @SerialName("max_supply_infinite")
        val maxSupplyInfinite: Boolean? = null,
        @SerialName("mcap_to_tvl_ratio")
        val mcapToTvlRatio: JsonElement? = null,
        @SerialName("price_change_24h")
        val priceChange24h: Double? = null,
        @SerialName("price_change_24h_in_currency")
        val priceChange24hInCurrency: Map<String, Double?>? = null,
        @SerialName("price_change_percentage_14d")
        val priceChangePercentage14d: Double? = null,
        @SerialName("price_change_percentage_14d_in_currency")
        val priceChangePercentage14dInCurrency: Map<String, Double?>? = null,
        @SerialName("price_change_percentage_1h_in_currency")
        val priceChangePercentage1hInCurrency: Map<String, Double?>? = null,
        @SerialName("price_change_percentage_1y")
        val priceChangePercentage1y: Double? = null,
        @SerialName("price_change_percentage_1y_in_currency")
        val priceChangePercentage1yInCurrency: Map<String, Double?>? = null,
        @SerialName("price_change_percentage_200d")
        val priceChangePercentage200d: Double? = null,
        @SerialName("price_change_percentage_200d_in_currency")
        val priceChangePercentage200dInCurrency: Map<String, Double?>? = null,
        @SerialName("price_change_percentage_24h")
        val priceChangePercentage24h: Double? = null,
        @SerialName("price_change_percentage_24h_in_currency")
        val priceChangePercentage24hInCurrency: Map<String, Double?>? = null,
        @SerialName("price_change_percentage_30d")
        val priceChangePercentage30d: Double? = null,
        @SerialName("price_change_percentage_30d_in_currency")
        val priceChangePercentage30dInCurrency: Map<String, Double?>? = null,
        @SerialName("price_change_percentage_60d")
        val priceChangePercentage60d: Double? = null,
        @SerialName("price_change_percentage_60d_in_currency")
        val priceChangePercentage60dInCurrency: Map<String, Double?>? = null,
        @SerialName("price_change_percentage_7d")
        val priceChangePercentage7d: Double? = null,
        @SerialName("price_change_percentage_7d_in_currency")
        val priceChangePercentage7dInCurrency: Map<String, Double?>? = null,
        @SerialName("roi")
        val roi: JsonElement? = null,
        @SerialName("total_supply")
        val totalSupply: Double? = null,
        @SerialName("total_value_locked")
        val totalValueLocked: JsonElement? = null,
        @SerialName("total_volume")
        val totalVolume: Map<String, Double?>? = null
    )

    @Serializable
    data class Ticker(
        @SerialName("base")
        val base: String? = null,
        @SerialName("bid_ask_spread_percentage")
        val bidAskSpreadPercentage: Double? = null,
        @SerialName("coin_id")
        val coinId: String? = null,
        @SerialName("coin_mcap_usd")
        val coinMcapUsd: Double? = null,
        @SerialName("converted_last")
        val convertedLast: ConvertedLast? = null,
        @SerialName("converted_volume")
        val convertedVolume: ConvertedVolume? = null,
        @SerialName("is_anomaly")
        val isAnomaly: Boolean? = null,
        @SerialName("is_stale")
        val isStale: Boolean? = null,
        @SerialName("last")
        val last: Double? = null,
        @SerialName("last_fetch_at")
        val lastFetchAt: String? = null,
        @SerialName("last_traded_at")
        val lastTradedAt: String? = null,
        @SerialName("market")
        val market: Market? = null,
        @SerialName("target")
        val target: String? = null,
        @SerialName("target_coin_id")
        val targetCoinId: String? = null,
        @SerialName("timestamp")
        val timestamp: String? = null,
        @SerialName("token_info_url")
        val tokenInfoUrl: JsonElement? = null,
        @SerialName("trade_url")
        val tradeUrl: String? = null,
        @SerialName("trust_score")
        val trustScore: String? = null,
        @SerialName("volume")
        val volume: Double? = null
    ) {
        @Serializable
        data class ConvertedLast(
            @SerialName("btc")
            val btc: Double? = null,
            @SerialName("eth")
            val eth: Double? = null,
            @SerialName("usd")
            val usd: Double? = null
        )

        @Serializable
        data class ConvertedVolume(
            @SerialName("btc")
            val btc: Double? = null,
            @SerialName("eth")
            val eth: Double? = null,
            @SerialName("usd")
            val usd: Double? = null
        )

        @Serializable
        data class Market(
            @SerialName("has_trading_incentive")
            val hasTradingIncentive: Boolean? = null,
            @SerialName("identifier")
            val identifier: String? = null,
            @SerialName("name")
            val name: String? = null
        )
    }
}
