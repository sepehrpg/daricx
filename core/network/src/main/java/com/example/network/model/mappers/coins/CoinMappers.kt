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

    links = links?.let { linksDto ->
        CoinDetails.Links(
            homepage = linksDto.homepage,
            blockchainSite = linksDto.blockchainSite,
            officialForumUrl = linksDto.officialForumUrl,
            announcementUrl = linksDto.announcementUrl?.map { je -> je?.asStringOrNull() },
            chatUrl = linksDto.chatUrl?.map { je -> je?.asStringOrNull() },
            reposUrl = linksDto.reposUrl?.let { r ->
                CoinDetails.Links.ReposUrl(
                    github = r.github,
                    bitbucket = r.bitbucket?.map { je -> je?.asStringOrNull() }
                )
            },
            bitcointalkThreadIdentifier = linksDto.bitcointalkThreadIdentifier?.asStringOrNull(),
            telegramChannelIdentifier = linksDto.telegramChannelIdentifier,
            telegramChannelUserCount = communityData?.telegramChannelUserCount?.asStringOrNull(),
            twitterScreenName = linksDto.twitterScreenName,
            facebookUsername = linksDto.facebookUsername,
            subredditUrl = linksDto.subredditUrl,
            snapshotUrl = linksDto.snapshotUrl?.asStringOrNull(),
            whitepaper = linksDto.whitepaper
        )
    },

    description = description?.let { CoinDetails.Description(it) },

    localization = localization?.let { CoinDetails.Localization(it.filterValues { v -> v != null } as Map<String, String>) },

    platforms = platforms ?: emptyMap(),

    // detailPlatforms: Map<String, DetailPlatformDto?>
    detailPlatforms = detailPlatforms
        ?.mapValues { (_, dto) ->
            dto?.let { d ->
                CoinDetails.DetailPlatform(
                    contractAddress = d.contractAddress,
                    decimalPlace = d.decimalPlace?.asStringOrNull()
                )
            }
        }
        ?: emptyMap(),

    communityData = communityData?.let { cd ->
        CoinDetails.CommunityData(
            facebookLikes = cd.facebookLikes?.asStringOrNull(),
            redditSubscribers = cd.redditSubscribers,
            redditAccountsActive48h = cd.redditAccountsActive48h,
            redditAveragePosts48h = cd.redditAveragePosts48h,
            redditAverageComments48h = cd.redditAverageComments48h,
            telegramChannelUserCount = cd.telegramChannelUserCount?.asStringOrNull()
        )
    },

    developerData = developerData?.let { dd ->
        CoinDetails.DeveloperData(
            forks = dd.forks,
            stars = dd.stars,
            subscribers = dd.subscribers,
            totalIssues = dd.totalIssues,
            closedIssues = dd.closedIssues,
            pullRequestsMerged = dd.pullRequestsMerged,
            pullRequestContributors = dd.pullRequestContributors,
            commitCount4Weeks = dd.commitCount4Weeks,
            last4WeeksCommitActivitySeries = dd.last4WeeksCommitActivitySeries?.map { je -> je?.asStringOrNull() },
            codeAdditions = dd.codeAdditionsDeletions4Weeks?.additions,
            codeDeletions = dd.codeAdditionsDeletions4Weeks?.deletions
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
                market = it.market?.let { m ->
                    CoinDetails.Ticker.Market(
                        identifier = m.identifier,
                        name = m.name,
                        hasTradingIncentive = m.hasTradingIncentive
                    )
                },
                last = it.last,
                volume = it.volume,
                convertedLast = it.convertedLast?.let { cl ->
                    CoinDetails.Ticker.ConvertedLast(
                        btc = cl.btc,
                        eth = cl.eth,
                        usd = cl.usd
                    )
                },
                convertedVolume = it.convertedVolume?.let { cv ->
                    CoinDetails.Ticker.ConvertedVolume(
                        btc = cv.btc,
                        eth = cv.eth,
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
            currentPrice = md.currentPrice,
            ath = md.ath,
            athChangePercentage = md.athChangePercentage,
            athDate = md.athDate,
            atl = md.atl,
            atlChangePercentage = md.atlChangePercentage,
            atlDate = md.atlDate,

            marketCap = md.marketCap,
            totalVolume = md.totalVolume,
            high24h = md.high24h,
            low24h = md.low24h,

            priceChange24h = md.priceChange24h,
            priceChange24hInCurrency = md.priceChange24hInCurrency,
            priceChangePercentage24h = md.priceChangePercentage24h,
            priceChangePercentage24hInCurrency = md.priceChangePercentage24hInCurrency,

            priceChangePercentage7d = md.priceChangePercentage7d,
            priceChangePercentage14d = md.priceChangePercentage14d,
            priceChangePercentage30d = md.priceChangePercentage30d,
            priceChangePercentage60d = md.priceChangePercentage60d,
            priceChangePercentage200d = md.priceChangePercentage200d,
            priceChangePercentage1y = md.priceChangePercentage1y,

            fullyDilutedValuation = md.fullyDilutedValuation,
            totalSupply = md.totalSupply,
            circulatingSupply = md.circulatingSupply,
            maxSupply = md.maxSupply,
            maxSupplyInfinite = md.maxSupplyInfinite,
            marketCapRank = md.marketCapRank,
            marketCapChange24h = md.marketCapChange24h,
            marketCapChange24hInCurrency = md.marketCapChange24hInCurrency,
            marketCapChangePercentage24hInCurrency = md.marketCapChangePercentage24hInCurrency,
            lastUpdated = md.lastUpdated
        )
    }
)
