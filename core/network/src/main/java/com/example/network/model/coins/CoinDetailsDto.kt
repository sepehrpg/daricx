package com.example.network.model.coins


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
 data class CoinDetailsDto(
    @SerialName("additional_notices")
    val additionalNotices: List<JsonElement?>? = null,
    @SerialName("asset_platform_id")
    val assetPlatformId: JsonElement?  = null,
    @SerialName("block_time_in_minutes")
    val blockTimeInMinutes: Int? = null,
    @SerialName("categories")
    val categories: List<String?>? = null,
    @SerialName("community_data")
    val communityData: CommunityData? = null,
    @SerialName("country_origin")
    val countryOrigin: String? = null,
    @SerialName("description")
    val description: Description? = null,
    @SerialName("detail_platforms")
    val detailPlatforms: DetailPlatforms? = null,
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
    @SerialName("localization")
    val localization: Localization? = null,
    @SerialName("market_cap_rank")
    val marketCapRank: Int? = null,
    @SerialName("market_data")
    val marketData: MarketData? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("platforms")
    val platforms: Platforms? = null,
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
     data class Description(
        val translations: Map<String, String?> = emptyMap()
    )


    @Serializable
     data class DetailPlatforms(
        @SerialName("")
        val x: X?
    ) {
        @Serializable
         data class X(
            @SerialName("contract_address")
            val contractAddress: String? = null,
            @SerialName("decimal_place")
            val decimalPlace: JsonElement? = null
        )
    }

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
        val whitepaper: String?
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
     data class Localization(
        val translations: Map<String, String?> = emptyMap()
    )

    @Serializable
     data class MarketData(
        @SerialName("ath")
        val ath: Ath? = null,
        @SerialName("ath_change_percentage")
        val athChangePercentage: AthChangePercentage? = null,
        @SerialName("ath_date")
        val athDate: AthDate? = null,
        @SerialName("atl")
        val atl: Atl? = null,
        @SerialName("atl_change_percentage")
        val atlChangePercentage: AtlChangePercentage? = null,
        @SerialName("atl_date")
        val atlDate: AtlDate? = null,
        @SerialName("circulating_supply")
        val circulatingSupply: Double? = null,
        @SerialName("current_price")
        val currentPrice: CurrentPrice? = null,
        @SerialName("fdv_to_tvl_ratio")
        val fdvToTvlRatio: JsonElement? = null,
        @SerialName("fully_diluted_valuation")
        val fullyDilutedValuation: FullyDilutedValuation? = null,
        @SerialName("high_24h")
        val high24h: High24h? = null,
        @SerialName("last_updated")
        val lastUpdated: String? = null,
        @SerialName("low_24h")
        val low24h: Low24h? = null,
        @SerialName("market_cap")
        val marketCap: MarketCap? = null,
        @SerialName("market_cap_change_24h")
        val marketCapChange24h: Double? = null,
        @SerialName("market_cap_change_24h_in_currency")
        val marketCapChange24hInCurrency: MarketCapChange24hInCurrency? = null,
        @SerialName("market_cap_change_percentage_24h")
        val marketCapChangePercentage24h: Double? = null,
        @SerialName("market_cap_change_percentage_24h_in_currency")
        val marketCapChangePercentage24hInCurrency: MarketCapChangePercentage24hInCurrency? = null,
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
        val priceChange24hInCurrency: PriceChange24hInCurrency? = null,
        @SerialName("price_change_percentage_14d")
        val priceChangePercentage14d: Double? = null,
        @SerialName("price_change_percentage_14d_in_currency")
        val priceChangePercentage14dInCurrency: PriceChangePercentage14dInCurrency? = null,
        @SerialName("price_change_percentage_1h_in_currency")
        val priceChangePercentage1hInCurrency: PriceChangePercentage1hInCurrency? = null,
        @SerialName("price_change_percentage_1y")
        val priceChangePercentage1y: Double? = null,
        @SerialName("price_change_percentage_1y_in_currency")
        val priceChangePercentage1yInCurrency: PriceChangePercentage1yInCurrency? = null,
        @SerialName("price_change_percentage_200d")
        val priceChangePercentage200d: Double? = null,
        @SerialName("price_change_percentage_200d_in_currency")
        val priceChangePercentage200dInCurrency: PriceChangePercentage200dInCurrency? = null,
        @SerialName("price_change_percentage_24h")
        val priceChangePercentage24h: Double? = null,
        @SerialName("price_change_percentage_24h_in_currency")
        val priceChangePercentage24hInCurrency: PriceChangePercentage24hInCurrency? = null,
        @SerialName("price_change_percentage_30d")
        val priceChangePercentage30d: Double? = null,
        @SerialName("price_change_percentage_30d_in_currency")
        val priceChangePercentage30dInCurrency: PriceChangePercentage30dInCurrency? = null,
        @SerialName("price_change_percentage_60d")
        val priceChangePercentage60d: Double? = null,
        @SerialName("price_change_percentage_60d_in_currency")
        val priceChangePercentage60dInCurrency: PriceChangePercentage60dInCurrency? = null,
        @SerialName("price_change_percentage_7d")
        val priceChangePercentage7d: Double? = null,
        @SerialName("price_change_percentage_7d_in_currency")
        val priceChangePercentage7dInCurrency: PriceChangePercentage7dInCurrency? = null,
        @SerialName("roi")
        val roi: JsonElement? = null,
        @SerialName("total_supply")
        val totalSupply: Double? = null,
        @SerialName("total_value_locked")
        val totalValueLocked: JsonElement? = null,
        @SerialName("total_volume")
        val totalVolume: TotalVolume? = null
    ) {

        @Serializable
         data class Ath(
            val currencies: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class AthChangePercentage(
            val percentages: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class AthDate(
            val dates: Map<String, String?> = emptyMap()
        )

        @Serializable
         data class Atl(
            val values: Map<String, Double?> = emptyMap()
        )


        @Serializable
         data class AtlChangePercentage(
            val changes: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class AtlDate(
            val dates: Map<String, String?> = emptyMap()
        )

        @Serializable
         data class CurrentPrice(
            val prices: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class FullyDilutedValuation(
            val valuations: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class High24h(
            val highs: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class Low24h(
            val lows: Map<String, Int?> = emptyMap()
        )

        @Serializable
         data class MarketCap(
            val caps: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class MarketCapChange24hInCurrency(
            val changes: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class MarketCapChangePercentage24hInCurrency(
            val changesPercent: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class PriceChange24hInCurrency(
            val priceChanges: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class PriceChangePercentage14dInCurrency(
            val priceChangesPercent: Map<String, Double?> = emptyMap()
        )



        @Serializable
         data class PriceChangePercentage1hInCurrency(
            val changes: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class PriceChangePercentage1yInCurrency(
            val changes: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class PriceChangePercentage200dInCurrency(
            val changes: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class PriceChangePercentage24hInCurrency(
            val changes: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class PriceChangePercentage30dInCurrency(
            val changes: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class PriceChangePercentage60dInCurrency(
            val changes: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class PriceChangePercentage7dInCurrency(
            val changes: Map<String, Double?> = emptyMap()
        )

        @Serializable
         data class TotalVolume(
            val volumes: Map<String, Double?> = emptyMap()
        )
    }

    @Serializable
     data class Platforms(
        @SerialName("")
        val x: String? = null
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
            val eth: Int? = null,
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