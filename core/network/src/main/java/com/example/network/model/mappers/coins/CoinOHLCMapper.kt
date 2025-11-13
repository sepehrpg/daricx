package com.example.network.model.mappers.coins

import com.example.model.coins.CoinOHLCChartCandle
import com.example.network.model.coins.CoinOHLCChartCandleDto


/**
 * Maps OHLC DTO to domain model.
 */
fun CoinOHLCChartCandleDto.toDomain(): CoinOHLCChartCandle =
    CoinOHLCChartCandle(
        candles = candles.map { it.toDomain() }
    )

/**
 * Maps a single DTO candle to domain candle.
 */
private fun CoinOHLCChartCandleDto.Ohlc.toDomain(): CoinOHLCChartCandle.Candle =
    CoinOHLCChartCandle.Candle(
        timestampMillis = timestampMillis,
        open = open,
        high = high,
        low = low,
        close = close
    )

