package com.example.model.coins



data class CoinHistoricalChart(
    val prices: List<Point>,
    val marketCaps: List<Point>,
    val totalVolumes: List<Point>
) {
    data class Point(
        val timestampMillis: Long,
        val value: Double?
    )
}