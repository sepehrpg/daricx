package com.example.network.model.mappers.coins



import com.example.model.coins.CoinTickers
import com.example.network.model.coins.CoinTickersDto
import kotlinx.serialization.json.*

/** Root mapper */
fun CoinTickersDto.toDomain(): CoinTickers =
    CoinTickers(
        name = name,
        tickers = tickers?.map { it?.toDomain() }
    )

/** Ticker mapper */
private fun CoinTickersDto.Ticker.toDomain(): CoinTickers.Ticker =
    CoinTickers.Ticker(
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

/** Market mapper */
private fun CoinTickersDto.Ticker.Market.toDomain(): CoinTickers.Ticker.Market =
    CoinTickers.Ticker.Market(
        hasTradingIncentive = hasTradingIncentive,
        identifier = identifier,
        name = name
    )

/** ConvertedLast mapper (usd: Int? مطابق DTO/Domain) */
private fun CoinTickersDto.Ticker.ConvertedLast.toDomain(): CoinTickers.Ticker.ConvertedLast =
    CoinTickers.Ticker.ConvertedLast(
        btc = btc,
        eth = eth,
        usd = usd
    )

/** ConvertedVolume mapper */
private fun CoinTickersDto.Ticker.ConvertedVolume.toDomain(): CoinTickers.Ticker.ConvertedVolume =
    CoinTickers.Ticker.ConvertedVolume(
        btc = btc,
        eth = eth,
        usd = usd
    )

/** JsonElement → Any? (String/Number/Boolean  Stringified  Object/Array) */
private fun JsonElement.toAnyOrNull(): Any? = when (this) {
    is JsonNull -> null
    is JsonPrimitive -> when {
        isString -> content
        booleanOrNull != null -> boolean
        longOrNull != null -> long
        doubleOrNull != null -> double
        else -> contentOrNull
    }
    is JsonObject, is JsonArray -> this.toString()
}
