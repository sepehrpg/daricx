package com.example.model.exchanges

/**
 * Domain model for exchange volume chart.
 *
 * Keep timestamps as Long to preserve exact millisecond precision.
 * Convert to Double only when passing to charting layers, if needed.
 */
data class ExchangeVolumeChart(
    val points: List<Point>
) {
    data class Point(
        val timestampMillis: Long,
        val volume: Double?
    )
}