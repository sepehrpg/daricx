package com.example.model.coins


/**
 * Domain model representing an OHLC time series for an per Coin.
 */
data class CoinOHLCChartCandle(
    val candles: List<Candle>
) {
    /**
     * A single OHLC candle.
     */
    data class Candle(
        val timestampMillis: Long,
        val open: Double,
        val high: Double,
        val low: Double,
        val close: Double
    )
}
