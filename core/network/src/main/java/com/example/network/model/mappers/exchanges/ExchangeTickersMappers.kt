package com.example.network.model.mappers.exchanges


import com.example.model.exchanges.ExchangeTickers
import com.example.network.model.exchanges.ExchangeTickersDto

/** Root mapper */
fun ExchangeTickersDto.toDomain(): ExchangeTickers =
    ExchangeTickers(
        name = name,
        tickers = tickers?.map { it?.toDomain() }
    )

/** Ticker mapper */
private fun ExchangeTickersDto.Ticker.toDomain(): ExchangeTickers.Ticker =
    ExchangeTickers.Ticker(
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
        tokenInfoUrl = tokenInfoUrl,
        tradeUrl = tradeUrl,
        trustScore = trustScore,
        volume = volume
    )

/** Nested mappers */
private fun ExchangeTickersDto.Ticker.ConvertedLast.toDomain(): ExchangeTickers.Ticker.ConvertedLast =
    ExchangeTickers.Ticker.ConvertedLast(
        btc = btc,
        eth = eth,
        usd = usd
    )

private fun ExchangeTickersDto.Ticker.ConvertedVolume.toDomain(): ExchangeTickers.Ticker.ConvertedVolume =
    ExchangeTickers.Ticker.ConvertedVolume(
        btc = btc,
        eth = eth,
        usd = usd
    )

private fun ExchangeTickersDto.Ticker.Market.toDomain(): ExchangeTickers.Ticker.Market =
    ExchangeTickers.Ticker.Market(
        hasTradingIncentive = hasTradingIncentive,
        identifier = identifier,
        name = name
    )
