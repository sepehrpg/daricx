package com.example.network.model.mappers.coins


import com.example.model.coins.CoinHistoricalData
import com.example.network.model.coins.CoinHistoricalDataDto
import kotlinx.serialization.json.*

/** ---------- DTO -> Domain ---------- */
fun CoinHistoricalDataDto.toDomain(): CoinHistoricalData =
    CoinHistoricalData(
        communityData = communityData?.toDomain(),
        developerData = developerData?.toDomain(),
        id = id,
        image = image?.toDomain(),
        localization = localization?.toDomain(),
        marketData = marketData?.toDomain(),
        name = name,
        publicInterestStats = publicInterestStats?.toDomain(),
        symbol = symbol
    )

private fun CoinHistoricalDataDto.CommunityData.toDomain(): CoinHistoricalData.CommunityData =
    CoinHistoricalData.CommunityData(
        facebookLikes = facebookLikes.toAny(),
        redditAccountsActive48h = redditAccountsActive48h.toAny(),
        redditAverageComments48h = redditAverageComments48h,
        redditAveragePosts48h = redditAveragePosts48h,
        redditSubscribers = redditSubscribers.toAny()
    )

private fun CoinHistoricalDataDto.DeveloperData.toDomain(): CoinHistoricalData.DeveloperData =
    CoinHistoricalData.DeveloperData(
        closedIssues = closedIssues,
        codeAdditionsDeletions4Weeks = codeAdditionsDeletions4Weeks?.toDomain(),
        commitCount4Weeks = commitCount4Weeks,
        forks = forks,
        pullRequestContributors = pullRequestContributors,
        pullRequestsMerged = pullRequestsMerged,
        stars = stars,
        subscribers = subscribers,
        totalIssues = totalIssues
    )

private fun CoinHistoricalDataDto.DeveloperData.CodeAdditionsDeletions4Weeks.toDomain():
        CoinHistoricalData.DeveloperData.CodeAdditionsDeletions4Weeks =
    CoinHistoricalData.DeveloperData.CodeAdditionsDeletions4Weeks(
        additions = additions,
        deletions = deletions
    )

private fun CoinHistoricalDataDto.Image.toDomain(): CoinHistoricalData.Image =
    CoinHistoricalData.Image(small = small, thumb = thumb)

private fun CoinHistoricalDataDto.Localization.toDomain(): CoinHistoricalData.Localization =
    CoinHistoricalData.Localization(translations = translations)

private fun CoinHistoricalDataDto.MarketData.toDomain(): CoinHistoricalData.MarketData =
    CoinHistoricalData.MarketData(
        currentPrice = currentPrice?.toDomain(),
        marketCap = marketCap?.toDomain(),
        totalVolume = totalVolume?.toDomain()
    )

private fun CoinHistoricalDataDto.MarketData.CurrentPrice.toDomain():
        CoinHistoricalData.MarketData.CurrentPrice =
    CoinHistoricalData.MarketData.CurrentPrice(currentPrice = currentPrice)

private fun CoinHistoricalDataDto.MarketData.MarketCap.toDomain():
        CoinHistoricalData.MarketData.MarketCap =
    CoinHistoricalData.MarketData.MarketCap(marketCap = marketCap)

private fun CoinHistoricalDataDto.MarketData.TotalVolume.toDomain():
        CoinHistoricalData.MarketData.TotalVolume =
    CoinHistoricalData.MarketData.TotalVolume(totalVolume = totalVolume)

private fun CoinHistoricalDataDto.PublicInterestStats.toDomain():
        CoinHistoricalData.PublicInterestStats =
    CoinHistoricalData.PublicInterestStats(
        alexaRank = alexaRank.toAny(),
        bingMatches = bingMatches.toAny()
    )

/** ---------- JsonElement -> Any? ---------- */
private fun JsonElement?.toAny(): Any? = when (this) {
    null, JsonNull -> null
    is JsonPrimitive -> {
        if (isString) {
            val s = content
            s.toLongOrNull()
                ?: s.toDoubleOrNull()
                ?: s.lowercase().let { if (it == "true" || it == "false") it.toBoolean() else s }
        } else {
            booleanOrNull ?: longOrNull ?: doubleOrNull ?: content
        }
    }
    is JsonArray -> this.map { it.toAny() }
    is JsonObject -> this.mapValues { it.value.toAny() }
}
