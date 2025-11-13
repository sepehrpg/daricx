package com.example.network.model.mappers.exchanges

import com.example.model.exchanges.ExchangeDetail
import com.example.network.model.exchanges.ExchangeDetailDto
import kotlinx.serialization.json.*
import kotlin.math.roundToLong

/** Map root DTO → Domain */
fun ExchangeDetailDto.toDomain(): ExchangeDetail =
    ExchangeDetail(
        alertNotice = alertNotice,
        centralized = centralized,
        coins = coins,
        country = country,
        description = description,
        facebookUrl = facebookUrl,
        hasTradingIncentive = hasTradingIncentive,
        image = image,
        name = name,
        otherUrl1 = otherUrl1,
        otherUrl2 = otherUrl2,
        pairs = pairs,
        publicNotice = publicNotice,
        redditUrl = redditUrl,
        slackUrl = slackUrl,
        statusUpdates = statusUpdates?.map { it?.toDomain() },
        telegramUrl = telegramUrl,
        tickers = tickers?.map { it?.toDomain() },
        tradeVolume24hBtc = tradeVolume24hBtc,
        trustScore = trustScore,
        trustScoreRank = trustScoreRank,
        twitterHandle = twitterHandle,
        url = url,
        yearEstablished = yearEstablished
    )

/** Map StatusUpdate DTO → Domain */
private fun ExchangeDetailDto.StatusUpdate.toDomain(): ExchangeDetail.StatusUpdate =
    ExchangeDetail.StatusUpdate(
        category = category,
        createdAt = createdAt,
        description = description,
        pin = pin,
        project = project?.toDomain(),
        user = user,
        userTitle = userTitle
    )

/** Map Project DTO → Domain */
private fun ExchangeDetailDto.StatusUpdate.Project.toDomain(): ExchangeDetail.StatusUpdate.Project =
    ExchangeDetail.StatusUpdate.Project(
        id = id,
        image = image?.toDomain(),
        name = name,
        type = type
    )

/** Map Project.Image DTO → Domain */
private fun ExchangeDetailDto.StatusUpdate.Project.Image.toDomain(): ExchangeDetail.StatusUpdate.Project.Image =
    ExchangeDetail.StatusUpdate.Project.Image(
        large = large,
        small = small,
        thumb = thumb
    )

/** Map Ticker DTO → Domain */
private fun ExchangeDetailDto.Ticker.toDomain(): ExchangeDetail.Ticker =
    ExchangeDetail.Ticker(
        base = base,
        bidAskSpreadPercentage = bidAskSpreadPercentage,
        coinId = coinId,
        coinMcapUsd = coinMcapUsd,
        convertedLast = convertedLast?.toDomain(),
        convertedVolume = convertedVolume?.toDomain(),
        isAnomaly = isAnomaly,
        isStale = isStale,
        last = last,
        lastFetchAt = lastFetchAt,
        lastTradedAt = lastTradedAt,
        market = market?.toDomain(),
        target = target,
        targetCoinId = targetCoinId,
        timestamp = timestamp,
        tokenInfoUrl = tokenInfoUrl?.toAnyOrNull(),
        tradeUrl = tradeUrl,
        trustScore = trustScore,
        volume = volume
    )

/** Map Market DTO → Domain */
private fun ExchangeDetailDto.Ticker.Market.toDomain(): ExchangeDetail.Ticker.Market =
    ExchangeDetail.Ticker.Market(
        hasTradingIncentive = hasTradingIncentive,
        identifier = identifier,
        name = name
    )

/** Map ConvertedLast DTO → Domain */
private fun ExchangeDetailDto.Ticker.ConvertedLast.toDomain(): ExchangeDetail.Ticker.ConvertedLast =
    ExchangeDetail.Ticker.ConvertedLast(
        btc = btc,
        eth = eth,
        usd = usd
    )

/** Map ConvertedVolume DTO → Domain (usd: Double? → Long?) */
private fun ExchangeDetailDto.Ticker.ConvertedVolume.toDomain(): ExchangeDetail.Ticker.ConvertedVolume =
    ExchangeDetail.Ticker.ConvertedVolume(
        btc = btc,
        eth = eth,
        usd = usd
    )

/** JsonElement → Any? (String/Number/Boolean/Stringified Object/Array) */
private fun JsonElement.toAnyOrNull(): Any? = when (this) {
    is JsonNull -> null
    is JsonPrimitive -> when {
        this.isString -> this.content
        this.booleanOrNull != null -> this.boolean
        this.longOrNull != null -> this.long
        this.doubleOrNull != null -> this.double
        else -> this.contentOrNull
    }
    is JsonObject, is JsonArray -> this.toString()
    else -> null
}
