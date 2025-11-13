package com.example.model.coins

data class CoinHistoricalData(
    val communityData: CommunityData?,
    val developerData: DeveloperData?,
    val id: String?,
    val image: Image?,
    val localization: Localization?,
    val marketData: MarketData?,
    val name: String?,
    val publicInterestStats: PublicInterestStats?,
    val symbol: String?
) {
     data class CommunityData(
        val facebookLikes: Any?,
        val redditAccountsActive48h: Any?,
        val redditAverageComments48h: Double?,
        val redditAveragePosts48h: Double?,
        val redditSubscribers: Any?
    )

     data class DeveloperData(
        val closedIssues: Int?,
        val codeAdditionsDeletions4Weeks: CodeAdditionsDeletions4Weeks?,
        val commitCount4Weeks: Int?,
        val forks: Int?,
        val pullRequestContributors: Int?,
        val pullRequestsMerged: Int?,
        val stars: Int?,
        val subscribers: Int?,
        val totalIssues: Int?
    ) {
         data class CodeAdditionsDeletions4Weeks(
            val additions: Int?,
            val deletions: Int?
        )
    }

     data class Image(
        val small: String?,
        val thumb: String?
    )

   data class Localization(
      val translations: Map<String, String?> = emptyMap()
   )

     data class MarketData(
        val currentPrice: CurrentPrice?,
        val marketCap: MarketCap?,
        val totalVolume: TotalVolume?
    ) {
        data class CurrentPrice(
           val currentPrice: Map<String, Double?> = emptyMap()
        )

        data class MarketCap(
           val marketCap: Map<String, Double?> = emptyMap()
        )

        data class TotalVolume(
           val totalVolume: Map<String, Double?> = emptyMap()
        )
    }

     data class PublicInterestStats(
        val alexaRank: Any?,
        val bingMatches: Any?
    )
}