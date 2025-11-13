package com.example.network.model.mappers.coins


import com.example.model.coins.CoinDetails
import com.example.network.model.coins.CoinDetailsDto
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.longOrNull

/**
 * Mapping helpers for JSONObject primitives (best-effort conversions).
 */
private fun JsonElement?.asStringOrNull(): String? {
    if (this == null || this is JsonNull) return null
    return when (this) {
        is JsonPrimitive -> this.content
        else -> this.toString()
    }
}

private fun JsonElement?.asDoubleOrNull(): Double? {
    if (this == null || this is JsonNull) return null
    return when (this) {
        is JsonPrimitive -> this.doubleOrNull
        else -> null
    }
}

private fun JsonElement?.asLongOrNull(): Long? {
    if (this == null || this is JsonNull) return null
    return when (this) {
        is JsonPrimitive -> this.longOrNull
        else -> null
    }
}

/** Convert Map-like DTO wrappers to simple maps where present. */
private fun <T> Map<String, T?>?.safe(): Map<String, T?>? = this ?: emptyMap()

/** Top-level mapper */
fun CoinDetailsDto.toDomain(): CoinDetails = CoinDetails(
    id = id,
    symbol = symbol,
    name = name,
    assetPlatformId = assetPlatformId?.asStringOrNull(),
    blockTimeInMinutes = blockTimeInMinutes,
    hashingAlgorithm = hashingAlgorithm,
    genesisDate = genesisDate,
    countryOrigin = countryOrigin,
    publicNotice = publicNotice?.asStringOrNull(),
    previewListing = previewListing,
    webSlug = webSlug,
    lastUpdated = lastUpdated,

    additionalNotices = additionalNotices?.map { it?.asStringOrNull() },
    categories = categories,
    statusUpdates = statusUpdates?.map { it?.asStringOrNull() },

    image = image?.let { CoinDetails.Image(it.thumb, it.small, it.large) },

    links = links?.let {
        CoinDetails.Links(
            homepage = it.homepage,
            blockchainSite = it.blockchainSite,
            officialForumUrl = it.officialForumUrl,
            announcementUrl = it.announcementUrl?.map { je -> je?.asStringOrNull() },
            chatUrl = it.chatUrl?.map { je -> je?.asStringOrNull() },
            reposUrl = it.reposUrl?.let { r ->
                CoinDetails.Links.ReposUrl(
                    github = r.github,
                    bitbucket = r.bitbucket?.map { je -> je?.asStringOrNull() }
                )
            },
            bitcointalkThreadIdentifier = it.bitcointalkThreadIdentifier?.asStringOrNull(),
            telegramChannelIdentifier = it.telegramChannelIdentifier,
            telegramChannelUserCount = it.telegramChannelIdentifier,
            twitterScreenName = it.twitterScreenName,
            facebookUsername = it.facebookUsername,
            subredditUrl = it.subredditUrl,
            snapshotUrl = it.snapshotUrl?.asStringOrNull(),
            whitepaper = it.whitepaper
        )
    },

    description = description?.let { CoinDetails.Description(it.translations) },

    localization = localization?.let { CoinDetails.Localization(it.translations) },

    // platforms: DTO has dynamic keys; DTO uses `Platforms` with `@SerialName("") val x: String?`
    // In DTO you had Platforms(val x: String?) - best effort: map to key "" -> value
    platforms = platforms?.let { mapOf("" to it.x) } ?: emptyMap(),

    // detailPlatforms: DTO had DetailPlatforms(@SerialName("") val x: X?)
    detailPlatforms = detailPlatforms?.let {
        mapOf("" to it.x?.let { x ->
            CoinDetails.DetailPlatform(
                contractAddress = x.contractAddress,
                decimalPlace = x.decimalPlace?.asStringOrNull()
            )
        })
    } ?: emptyMap(),

    communityData = communityData?.let {
        CoinDetails.CommunityData(
            facebookLikes = it.facebookLikes?.asStringOrNull(),
            redditSubscribers = it.redditSubscribers,
            redditAccountsActive48h = it.redditAccountsActive48h,
            redditAveragePosts48h = it.redditAveragePosts48h,
            redditAverageComments48h = it.redditAverageComments48h,
            telegramChannelUserCount = it.telegramChannelUserCount?.asStringOrNull()
        )
    },

    developerData = developerData?.let {
        CoinDetails.DeveloperData(
            forks = it.forks,
            stars = it.stars,
            subscribers = it.subscribers,
            totalIssues = it.totalIssues,
            closedIssues = it.closedIssues,
            pullRequestsMerged = it.pullRequestsMerged,
            pullRequestContributors = it.pullRequestContributors,
            commitCount4Weeks = it.commitCount4Weeks,
            last4WeeksCommitActivitySeries = it.last4WeeksCommitActivitySeries?.map { je -> je?.asStringOrNull() },
            codeAdditions = it.codeAdditionsDeletions4Weeks?.additions,
            codeDeletions = it.codeAdditionsDeletions4Weeks?.deletions
        )
    },

    marketCapRank = marketCapRank,
    watchlistPortfolioUsers = watchlistPortfolioUsers,
    sentimentVotesUpPercentage = sentimentVotesUpPercentage,
    sentimentVotesDownPercentage = sentimentVotesDownPercentage,

    tickers = tickers?.map { t ->
        t?.let {
            CoinDetails.Ticker(
                base = it.base,
                target = it.target,
                market = it.market?.let { m -> CoinDetails.Ticker.Market(m.identifier, m.name, m.hasTradingIncentive) },
                last = it.last,
                volume = it.volume,
                convertedLast = it.convertedLast?.let { cl ->
                    CoinDetails.Ticker.ConvertedLast(
                        btc = cl.btc,
                        eth = cl.eth,
                        usd = cl.usd?.toDouble()
                    )
                },
                convertedVolume = it.convertedVolume?.let { cv ->
                    CoinDetails.Ticker.ConvertedVolume(
                        btc = cv.btc,
                        eth = cv.eth?.toDouble(),
                        usd = cv.usd
                    )
                },
                trustScore = it.trustScore,
                bidAskSpreadPercentage = it.bidAskSpreadPercentage,
                isAnomaly = it.isAnomaly,
                isStale = it.isStale,
                timestamp = it.timestamp,
                lastFetchAt = it.lastFetchAt,
                lastTradedAt = it.lastTradedAt,
                tokenInfoUrl = it.tokenInfoUrl?.asStringOrNull(),
                tradeUrl = it.tradeUrl,
                tokenInfo = it.tokenInfoUrl?.asStringOrNull(),
                coinId = it.coinId,
                coinMcapUsd = it.coinMcapUsd
            )
        }
    },

    marketData = marketData?.let { md ->
        CoinDetails.MarketData(
            currentPrice = md.currentPrice?.prices?.toMap(),
            ath = md.ath?.currencies?.toMap(),
            athChangePercentage = md.athChangePercentage?.percentages?.toMap(),
            athDate = md.athDate?.dates?.toMap(),
            atl = md.atl?.values?.toMap(),
            atlChangePercentage = md.atlChangePercentage?.changes?.toMap(),
            atlDate = md.atlDate?.dates?.toMap(),

            marketCap = md.marketCap?.caps?.toMap(),
            totalVolume = md.totalVolume?.volumes?.toMap(),
            high24h = md.high24h?.highs?.toMap(),
            low24h = md.low24h?.lows?.mapValues { it.value?.toDouble() } ?: emptyMap(),

            priceChange24h = md.priceChange24h,
            priceChange24hInCurrency = md.priceChange24hInCurrency?.priceChanges?.toMap(),
            priceChangePercentage24h = md.priceChangePercentage24h,
            priceChangePercentage24hInCurrency = md.priceChangePercentage24hInCurrency?.changes?.toMap(),

            priceChangePercentage7d = md.priceChangePercentage7d,
            priceChangePercentage14d = md.priceChangePercentage14d,
            priceChangePercentage30d = md.priceChangePercentage30d,
            priceChangePercentage60d = md.priceChangePercentage60d,
            priceChangePercentage200d = md.priceChangePercentage200d,
            priceChangePercentage1y = md.priceChangePercentage1y,

            fullyDilutedValuation = md.fullyDilutedValuation?.valuations?.toMap(),
            totalSupply = md.totalSupply,
            circulatingSupply = md.circulatingSupply,
            maxSupply = md.maxSupply,
            maxSupplyInfinite = md.maxSupplyInfinite,
            marketCapRank = md.marketCapRank,
            marketCapChange24h = md.marketCapChange24h,
            marketCapChange24hInCurrency = md.marketCapChange24hInCurrency?.changes?.toMap(),
            marketCapChangePercentage24hInCurrency = md.marketCapChangePercentage24hInCurrency?.changesPercent?.toMap(),
            lastUpdated = md.lastUpdated
        )
    }
)
