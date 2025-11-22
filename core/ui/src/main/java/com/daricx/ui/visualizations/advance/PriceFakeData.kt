package com.daricx.ui.visualizations.advance

import com.example.model.coins.CoinHistoricalChart
import kotlin.math.abs
import kotlin.math.sin


/**
 * Generate fake CoinHistoricalChart data that mimics /coins/{id}/market_chart.
 *
 * - timestamps: Unix millis, uniformly spaced
 * - prices: BTC-like with small drift + intraday wave + micro-noise
 * - marketCaps: price * supply (simple approximation)
 * - totalVolumes: baseVolume + |Δprice| * volumeSensitivity
 */
fun generateFakeCoinHistoricalChart(
    nowMillis: Long = System.currentTimeMillis(),
    days: Int = 1,
    pointsPerDay: Int = 288,
    startPrice: Double = 96_000.0,
    endPrice: Double = 95_000.0,
    circulatingSupply: Double = 19_700_000.0,
    baseVolume: Double = 80_000_000_000.0,
    volumeSensitivity: Double = 1_000_000.0,
): CoinHistoricalChart {
    val total = (days * pointsPerDay).coerceAtLeast(2)
    val stepMillis = 24L * 60 * 60 * 1000 / pointsPerDay
    val startMillis = nowMillis - days * 24L * 60 * 60 * 1000

    val prices = ArrayList<CoinHistoricalChart.Point>(total)
    val marketCaps = ArrayList<CoinHistoricalChart.Point>(total)
    val volumes = ArrayList<CoinHistoricalChart.Point>(total)

    val linearStep = (endPrice - startPrice) / (total - 1)

    fun lcg(i: Int): Double {
        var x = 42L
        repeat(i + 1) { x = (1664525L * x + 1013904223L) and 0xFFFFFFFF }
        val u = x.toDouble() / 0x1_0000_0000L // [0,1)
        return (u - 0.5) * 2.0               // [-1,1]
    }

    var prevPrice = startPrice

    for (i in 0 until total) {
        val t = startMillis + i * stepMillis

        val dayPhase = 2.0 * Math.PI * (i % pointsPerDay) / pointsPerDay
        val intraday = 120.0 * sin(dayPhase)          // ±120$
        val micro = 40.0 * lcg(i)                     // ±40$

        val drift = startPrice + linearStep * i
        val price = (drift + intraday + micro)

        prices += CoinHistoricalChart.Point(
            timestampMillis = t,
            value = price
        )

        val marketCap = price * circulatingSupply
        marketCaps += CoinHistoricalChart.Point(
            timestampMillis = t,
            value = marketCap
        )

        val delta = abs(price - prevPrice)
        val volume = baseVolume + delta * volumeSensitivity
        volumes += CoinHistoricalChart.Point(
            timestampMillis = t,
            value = volume
        )

        prevPrice = price
    }

    return CoinHistoricalChart(
        prices = prices,
        marketCaps = marketCaps,
        totalVolumes = volumes
    )
}













// Generates 84 BTC-like prices (2h intervals over 7 days) with reproducible micro-volatility.
data class TimePrice(val label: String, val price: Double)

fun generateBtcLike2hSeries(
    startUtcMillis: Long = System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000, // a week ago
    days: Int = 60,
    pointsPerDay: Int = 12,           // 12 × 2h = 24h
    startPrice: Double = 106_200.0,   // ~ recent ATH-range in USD
    endPrice: Double = 101_400.0,     // slight weekly drift down
    tz: java.time.ZoneId = java.time.ZoneId.of("UTC"),
): Pair<List<String>, List<Double>> {

    // Small deterministic “noise” without Random: simple LCG.
    fun lcg(n: Int): Double {
        var x = 42L // seed
        repeat(n + 1) { x = (1664525L * x + 1013904223L) and 0xFFFFFFFF }
        val u = (x.toDouble() / 0x1_0000_0000L) // [0,1)
        return (u - 0.5) * 2.0 // [-1, 1)
    }

    val total = days * pointsPerDay
    val labels = ArrayList<String>(total)
    val prices = ArrayList<Double>(total)

    val linearStep = (endPrice - startPrice) / (total - 1).coerceAtLeast(1)
    val twoHours = 2L * 60 * 60 * 1000

    for (i in 0 until total) {
        val t = startUtcMillis + i * twoHours

        // diurnal oscillation (~ intraday wave) + weekly slower wave
        val dayPhase = 2.0 * Math.PI * (i % pointsPerDay) / pointsPerDay
        val weekPhase = 2.0 * Math.PI * i / total

        val intraday = 120.0 * kotlin.math.sin(dayPhase)         // ±$120
        val slowWave = 250.0 * kotlin.math.sin(weekPhase)        // ±$250
        val micro = 40.0 * lcg(i)                                // ±$40 (tiny irregularity)

        val baseline = startPrice + linearStep * i               // gentle drift
        val price = (baseline + intraday + slowWave + micro)

        // Label like "MM-dd HH:mm"
        val label = java.time.format.DateTimeFormatter.ofPattern("MM-dd HH:mm")
            .withZone(tz)
            .format(java.time.Instant.ofEpochMilli(t))

        labels += label
        prices += kotlin.math.round(price * 100.0) / 100.0 // 2 decimals
    }

    return labels to prices
}

// --- Example usage (gives 84 points) ---
//val (labels2h, btcPrices2h) = generateBtcLike2hSeries()
// labels2h.size == 84, btcPrices2h.size == 84