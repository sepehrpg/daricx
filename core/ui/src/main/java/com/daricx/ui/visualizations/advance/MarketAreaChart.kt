// File: com/daricx/ui/visualizations/advance/MarketAreaChart.kt
package com.daricx.ui.visualizations.advance

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.ThemePreviews
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.auto
import com.patrykandpatrick.vico.compose.cartesian.axis.fixed
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.continuous
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.insets
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.BaseAxis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider.Companion.verticalGradient
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

/**
 * Domain-friendly point for price/time series.
 *
 * @property timestampMillis X value as epoch millis (use 0-based index if you pass `xLabelsOverride`).
 * @property value Y value as a raw price/metric (Double).
 */
data class PricePoint(
    val timestampMillis: Long,
    val value: Double
)

/** X-axis formatter for epoch millis → "HH:mm". */
private fun timeFormatter(zoneId: ZoneId): CartesianValueFormatter =
    CartesianValueFormatter { _, x, _ ->
        val fmt = DateTimeFormatter.ofPattern("HH:mm").withZone(zoneId)
        fmt.format(Instant.ofEpochMilli(x.toLong()))
    }

/** X-axis formatter when you only have index-based labels. */
private fun indexToLabelFormatter(labels: List<String>): CartesianValueFormatter =
    CartesianValueFormatter { _, x, _ ->
        val i = x.roundToInt()
        if (i in labels.indices) labels[i] else ""
    }

/**
 * Builds a red line spec with a soft vertical gradient area beneath the line.
 *
 * - No points/dots for a clean look.
 * - Sharp connector to match the market look.
 */
@Composable
private fun rememberRedLine(
    lineColor: Color,
    strokeThickness: Dp = 2.dp
): LineCartesianLayer.Line {
    val areaShader = verticalGradient(
        colors = intArrayOf(
            lineColor.copy(alpha = 0.35f).toArgb(),
            lineColor.copy(alpha = 0f).toArgb(),
        )
    )
    val areaFill = LineCartesianLayer.AreaFill.single(fill(areaShader))
    val lineFill = LineCartesianLayer.LineFill.single(fill(lineColor))
    return LineCartesianLayer.rememberLine(
        fill = lineFill,
        stroke = LineCartesianLayer.LineStroke.continuous(thickness = strokeThickness),
        areaFill = areaFill,
        pointProvider = null,
        pointConnector = LineCartesianLayer.PointConnector.Sharp
    )
}

/**
 * Market-style area chart with a baseline-friendly scaling strategy.
 *
 * Scaling modes (via [normalize] & [mode]):
 * - "delta": percentage change vs. first value (Y axis shows %).
 * - "scale": min–max normalization to 0..100 (Y axis shows %).
 * - "offset": plot deltas around a baseline (First/Min/Last/Custom) but format Y as absolute values.
 * - any other / normalize=false: raw values.
 *
 * @param yValueFormatter optional override for Y labels; otherwise uses strategy formatter,
 *                        then falls back to [SmartMoneyFormatter].
 */
@Composable
fun MarketAreaChart(
    points: List<PricePoint>,
    modifier: Modifier = Modifier,
    height: Dp = 220.dp,
    lineColor: Color = Color(0xFFE53935),
    zoneId: ZoneId = ZoneId.systemDefault(),
    xLabelsOverride: List<String>? = null,
    // scaling API
    normalize: Boolean = true,
    mode: String = "delta",                 // "delta" | "scale" | "offset" | other=raw
    offsetBaseline: OffsetBaseline = OffsetBaseline.Min,
    offsetCustomValue: Double? = null,
    yValueFormatter: CartesianValueFormatter? = null,
) {
    val modelProducer = remember { CartesianChartModelProducer() }

    // Choose scaling strategy and transform Y.
    val yRaw = remember(points) { points.map { it.value } }
    val strategy = remember(normalize, mode, yRaw, offsetBaseline, offsetCustomValue) {
        pickStrategy(normalize, mode, yRaw, offsetBaseline, offsetCustomValue)
    }
    val yScaled = remember(yRaw, strategy) { strategy.transform(yRaw) }

    // Feed chart model.
    LaunchedEffect(points, yScaled, xLabelsOverride) {
        modelProducer.runTransaction {
            lineSeries {
                if (xLabelsOverride == null) {
                    series(
                        x = points.map { it.timestampMillis },
                        y = yScaled,
                    )
                } else {
                    series(
                        x = points.indices.map { it.toDouble() },
                        y = yScaled,
                    )
                }
            }
        }
    }

    // Bottom (X) axis — clean: no gridlines/ticks/axis line.
    val bottomAxis = if (xLabelsOverride == null) {
        HorizontalAxis.rememberBottom(
            valueFormatter = timeFormatter(zoneId),
            tick = null, guideline = null, line = null,
            label = rememberAxisLabelComponent(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textSize = 10.sp,
                margins = insets(top = 15.dp)
            )
        )
    } else {
        HorizontalAxis.rememberBottom(
            valueFormatter = indexToLabelFormatter(xLabelsOverride),
            tick = null, line = null,guideline = null,
            label = rememberAxisLabelComponent(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textSize = 10.sp,
                margins = insets(top = 15.dp)
            )
        )
    }

    // Start (Y) axis — prefer strategy formatter → yValueFormatter override → smart fallback.
    val yFormatter = yValueFormatter ?: strategy.yFormatter ?: SmartMoneyFormatter

    val startAxis = VerticalAxis.rememberStart(
        valueFormatter = yFormatter,
        label = rememberAxisLabelComponent(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textSize = 10.sp,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            padding = insets(horizontal = (-40).dp),
        ),
         line = null, tick = null , guideline = null,
        itemPlacer = remember {
            VerticalAxis.ItemPlacer.count( count = {10} )
        }
    )

    // Layer + chart assembly.
    val range = strategy.rangeProvider ?: remember { CartesianLayerRangeProvider.auto() }
    val line = rememberRedLine(lineColor)
    val lineLayer = rememberLineCartesianLayer(
        lineProvider = LineCartesianLayer.LineProvider.series(line),
        rangeProvider = range,
        pointSpacing = 1.dp
    )
    val chart = rememberCartesianChart(
        lineLayer,
        startAxis = startAxis,
        bottomAxis = bottomAxis,
    )

    ProvideVicoTheme(rememberM3VicoTheme()) {
        CartesianChartHost(
            chart = chart,
            modelProducer = modelProducer,
            modifier = modifier.fillMaxWidth().height(height),
        )
    }
}

/* --------------------------- Preview --------------------------- */

@ThemePreviews
@Composable
fun ExampleMarket_OffsetFirst() {
    val labels = listOf("02:00","16:00","20:00","00:00","04:00","08:00","08:30","09:00")
    val values = listOf(
        100_000.0, 100_001.0, 100_002.0, 100_003.5, 100_002.8, 100_004.2, 100_003.7, 100_005.1
    )
    val points = remember { values.map { PricePoint(0L, it) } }

    Box(Modifier.padding(16.dp)) {
        val (labels2h, btcPrices2h) = generateBtcLike2hSeries()
        //val points = btcPrices2h.map { PricePoint(timestampMillis = 0L, value = it) }
        MarketAreaChart(
            points = points,
            xLabelsOverride = labels,
            lineColor = Color(0xFFE53935),
            normalize = true,
            mode = "offset",
            offsetBaseline = OffsetBaseline.Min
        )
    }
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
