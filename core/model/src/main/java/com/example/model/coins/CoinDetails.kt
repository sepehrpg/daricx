package com.example.model.coins


/**
 * Full domain model for Coin — mirrors DTO
 */
data class CoinDetails(
    val id: String?,
    val symbol: String?,
    val name: String?,
    val assetPlatformId: String?,
    val blockTimeInMinutes: Int?,
    val hashingAlgorithm: String?,
    val genesisDate: String?,
    val countryOrigin: String?,
    val publicNotice: String?,
    val previewListing: Boolean?,
    val webSlug: String?,
    val lastUpdated: String?,

    val additionalNotices: List<String?>?,
    val categories: List<String?>?,
    val statusUpdates: List<String?>?,

    val image: Image?,
    val links: Links?,
    val description: Description?,
    val localization: Localization?,
    val platforms: Map<String, String?>?,
    val detailPlatforms: Map<String, DetailPlatform?>?,

    val communityData: CommunityData?,
    val developerData: DeveloperData?,

    val marketCapRank: Int?,
    val watchlistPortfolioUsers: Int?,
    val sentimentVotesUpPercentage: Double?,
    val sentimentVotesDownPercentage: Double?,
    val tickers: List<Ticker?>?,
    val marketData: MarketData?
) {
    data class Image(
        val thumb: String?,
        val small: String?,
        val large: String?
    )

    data class Links(
        val homepage: List<String?>?,
        val blockchainSite: List<String?>?,
        val officialForumUrl: List<String?>?,
        val announcementUrl: List<String?>?,
        val chatUrl: List<String?>?,
        val reposUrl: ReposUrl?,
        val bitcointalkThreadIdentifier: String?,
        val telegramChannelIdentifier: String?,
        val telegramChannelUserCount: String?,
        val twitterScreenName: String?,
        val facebookUsername: String?,
        val subredditUrl: String?,
        val snapshotUrl: String?,
        val whitepaper: String?
    ) {
        data class ReposUrl(
            val github: List<String?>?,
            val bitbucket: List<String?>?
        )
    }

    data class Description(
        val translations: Map<String, String?> // language -> html/text
    )

    data class Localization(
        val translations: Map<String, String?>
    )

    data class DetailPlatform(
        val contractAddress: String?,
        val decimalPlace: String? // some APIs return number or string — keep as string
    )

    data class CommunityData(
        val facebookLikes: String?,
        val redditSubscribers: Int?,
        val redditAccountsActive48h: Int?,
        val redditAveragePosts48h: Double?,
        val redditAverageComments48h: Double?,
        val telegramChannelUserCount: String?
    )

    data class DeveloperData(
        val forks: Int?,
        val stars: Int?,
        val subscribers: Int?,
        val totalIssues: Int?,
        val closedIssues: Int?,
        val pullRequestsMerged: Int?,
        val pullRequestContributors: Int?,
        val commitCount4Weeks: Int?,
        val last4WeeksCommitActivitySeries: List<String?>?,
        val codeAdditions: Int?,
        val codeDeletions: Int?
    )

    data class MarketData(
        // Store currency -> value maps so UI can pick desired currency
        val currentPrice: Map<String, Double?>?,
        val ath: Map<String, Double?>?,
        val athChangePercentage: Map<String, Double?>?,
        val athDate: Map<String, String?>?,
        val atl: Map<String, Double?>?,
        val atlChangePercentage: Map<String, Double?>?,
        val atlDate: Map<String, String?>?,

        val marketCap: Map<String, Double?>?,
        val totalVolume: Map<String, Double?>?,
        val high24h: Map<String, Double?>?,
        val low24h: Map<String, Double?>?,

        val priceChange24h: Double?,
        val priceChange24hInCurrency: Map<String, Double?>?,
        val priceChangePercentage24h: Double?,
        val priceChangePercentage24hInCurrency: Map<String, Double?>?,

        val priceChangePercentage7d: Double?,
        val priceChangePercentage14d: Double?,
        val priceChangePercentage30d: Double?,
        val priceChangePercentage60d: Double?,
        val priceChangePercentage200d: Double?,
        val priceChangePercentage1y: Double?,

        val fullyDilutedValuation: Map<String, Double?>?,
        val totalSupply: Double?,
        val circulatingSupply: Double?,
        val maxSupply: Double?,
        val maxSupplyInfinite: Boolean?,
        val marketCapRank: Int?,
        val marketCapChange24h: Double?,
        val marketCapChange24hInCurrency: Map<String, Double?>?,
        val marketCapChangePercentage24hInCurrency: Map<String, Double?>?,
        val lastUpdated: String?
    )

    data class Ticker(
        val base: String?,
        val target: String?,
        val market: Market?,
        val last: Double?,
        val volume: Double?,
        val convertedLast: ConvertedLast?,
        val convertedVolume: ConvertedVolume?,
        val trustScore: String?,
        val bidAskSpreadPercentage: Double?,
        val isAnomaly: Boolean?,
        val isStale: Boolean?,
        val timestamp: String?,
        val lastFetchAt: String?,
        val lastTradedAt: String?,
        val tokenInfoUrl: String?,
        val tradeUrl: String?,
        val tokenInfo: String?,
        val coinId: String?,
        val coinMcapUsd: Double?
    ) {
        data class Market(
            val identifier: String?,
            val name: String?,
            val hasTradingIncentive: Boolean?
        )

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
    }
}
