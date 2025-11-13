package com.example.network.model.coins


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
 data class CoinHistoricalDataDto(
    @SerialName("community_data")
    val communityData: CommunityData?,
    @SerialName("developer_data")
    val developerData: DeveloperData?,
    @SerialName("id")
    val id: String?,
    @SerialName("image")
    val image: Image?,
    @SerialName("localization")
    val localization: Localization?,
    @SerialName("market_data")
    val marketData: MarketData?,
    @SerialName("name")
    val name: String?,
    @SerialName("public_interest_stats")
    val publicInterestStats: PublicInterestStats?,
    @SerialName("symbol")
    val symbol: String?
) {
    @Serializable
     data class CommunityData(
        @SerialName("facebook_likes")
        val facebookLikes: JsonElement?,
        @SerialName("reddit_accounts_active_48h")
        val redditAccountsActive48h: JsonElement?,
        @SerialName("reddit_average_comments_48h")
        val redditAverageComments48h: Double?,
        @SerialName("reddit_average_posts_48h")
        val redditAveragePosts48h: Double?,
        @SerialName("reddit_subscribers")
        val redditSubscribers: JsonElement?
    )

    @Serializable
     data class DeveloperData(
        @SerialName("closed_issues")
        val closedIssues: Int?,
        @SerialName("code_additions_deletions_4_weeks")
        val codeAdditionsDeletions4Weeks: CodeAdditionsDeletions4Weeks?,
        @SerialName("commit_count_4_weeks")
        val commitCount4Weeks: Int?,
        @SerialName("forks")
        val forks: Int?,
        @SerialName("pull_request_contributors")
        val pullRequestContributors: Int?,
        @SerialName("pull_requests_merged")
        val pullRequestsMerged: Int?,
        @SerialName("stars")
        val stars: Int?,
        @SerialName("subscribers")
        val subscribers: Int?,
        @SerialName("total_issues")
        val totalIssues: Int?
    ) {
        @Serializable
         data class CodeAdditionsDeletions4Weeks(
            @SerialName("additions")
            val additions: Int?,
            @SerialName("deletions")
            val deletions: Int?
        )
    }

    @Serializable
     data class Image(
        @SerialName("small")
        val small: String?,
        @SerialName("thumb")
        val thumb: String?
    )

    @Serializable
    data class Localization(
        val translations: Map<String, String?> = emptyMap()
    )

    @Serializable
     data class MarketData(
        @SerialName("current_price")
        val currentPrice: CurrentPrice?,
        @SerialName("market_cap")
        val marketCap: MarketCap?,
        @SerialName("total_volume")
        val totalVolume: TotalVolume?
    ) {

        @Serializable
        data class CurrentPrice(
            val currentPrice: Map<String, Double?> = emptyMap()
        )

        @Serializable
        data class MarketCap(
            val marketCap: Map<String, Double?> = emptyMap()
        )

        @Serializable
        data class TotalVolume(
            val totalVolume: Map<String, Double?> = emptyMap()
        )
    }

    @Serializable
     data class PublicInterestStats(
        @SerialName("alexa_rank")
        val alexaRank: JsonElement?,
        @SerialName("bing_matches")
        val bingMatches: JsonElement?
    )
}