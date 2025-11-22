package com.daricx.ui.visualizations.advance

import android.text.Layout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import com.example.designsystem.theme.ThemePreviews
import com.example.model.coins.CoinOHLCChartCandle
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.VicoZoomState
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.absolute
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberCandlestickCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.insets
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.Scroll
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.candlestickSeries
import com.patrykandpatrick.vico.core.cartesian.layer.CandlestickCartesianLayer
import java.time.ZoneId
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider

/**
 * Reusable candlestick (OHLC) chart for a single coin.
 *
 * Horizontal behavior:
 * - Uses index-based X values (0..N-1) to work nicely with Zoom.x().
 * - Shows [initialVisibleCandles] candles on screen by default.
 * - Extra candles are reachable by horizontal scroll.
 */
@Composable
fun CoinCandlestickChart(
    candles: List<CoinOHLCChartCandle.Candle>,
    modifier: Modifier = Modifier,
    height: Dp = 220.dp,
    zoneId: ZoneId = ZoneId.systemDefault(),
    bullishColor: Color = Color(0xFF26A69A),   // green-ish
    bearishColor: Color = Color(0xFFE53935),   // red-ish
    neutralColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    yValueFormatter: CartesianValueFormatter = SmartMoneyFormatter,
    // Scroll is enabled by default for better candle width.
    scrollEnabled: Boolean = true,
    // Target number of candles that should be visible at once (approx).
    initialVisibleCandles: Int = 10,
) {
    if (candles.isEmpty()) {
        // Nothing to draw, but keep the space reserved.
        CartesianChartHost(
            chart = rememberCartesianChart(),
            modelProducer = remember { CartesianChartModelProducer() },
            modifier = modifier
                .fillMaxWidth()
                .height(height),
        )
        return
    }

    val modelProducer = remember { CartesianChartModelProducer() }
    // ----- Y range based on candle lows/highs -----
    val (minPrice, maxPrice) = remember(candles) {
        var minLow = Double.POSITIVE_INFINITY
        var maxHigh = Double.NEGATIVE_INFINITY
        for (candle in candles) {
            if (candle.low < minLow) minLow = candle.low
            if (candle.high > maxHigh) maxHigh = candle.high
        }
        if (minLow == Double.POSITIVE_INFINITY || maxHigh == Double.NEGATIVE_INFINITY) {
            // Fallback range in case of invalid data.
            0.0 to 1.0
        } else {
            val span = (maxHigh - minLow).takeIf { it > 0.0 } ?: 1.0
            val padding = span * 0.05   // 5% padding on each side
            (minLow - padding) to (maxHigh + padding)
        }
    }
    val rangeProvider = remember(minPrice, maxPrice) {
        CartesianLayerRangeProvider.fixed(
            minY = minPrice,
            maxY = maxPrice,
        )
    }





    // Build labels for index-based X axis from timestamps.
    val xLabels = remember(candles, zoneId) {
        val points = candles.map { candle ->
            PricePoint(
                timestampMillis = candle.timestampMillis,
                value = candle.close,
            )
        }
        buildTimeXAxisLabels(points, zoneId)
    }

    // Feed OHLC data using index-based X (0..N-1).
    LaunchedEffect(candles) {
        val opens = candles.map { it.open }
        val closes = candles.map { it.close }
        val lows = candles.map { it.low }
        val highs = candles.map { it.high }

        modelProducer.runTransaction {
            candlestickSeries(
                opening = opens,
                closing = closes,
                low = lows,
                high = highs,
            )
        }
    }

    // ----- Axes -----

    val bottomAxis = com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis.rememberBottom(
        valueFormatter = indexToLabelFormatter(xLabels),
        tick = null,
        guideline = null,
        line = null,
        label = rememberAxisLabelComponent(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textSize = 10.sp,
            margins = insets(top = 15.dp),
        ),
    )

    val yStep = remember(minPrice, maxPrice) {
        val span = (maxPrice - minPrice).takeIf { it > 0.0 } ?: 1.0
        niceStep(span, targetTicks = 5)
    }
    val startAxis = VerticalAxis.rememberStart(
        valueFormatter = yValueFormatter,
        label = rememberAxisLabelComponent(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textSize = 10.sp,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
        ),
        itemPlacer = remember {
            VerticalAxis.ItemPlacer.count( count = {10} )
        },
        /*itemPlacer = remember(yStep) {
            VerticalAxis.ItemPlacer.step(
                step = { yStep }
            )
        },*/
        line = null,
        tick = null,
        guideline = null,
    )

    // ----- Candle body styles -----

    val bullishBody = rememberLineComponent(
        fill = fill(bullishColor),
        thickness = 8.dp, // thicker for better visibility
    )

    val bearishBody = rememberLineComponent(
        fill = fill(bearishColor),
        thickness = 8.dp,
    )

    val neutralBody = rememberLineComponent(
        fill = fill(neutralColor),
        thickness = 8.dp,
    )

    /**
     * CandleProvider that styles candles based on absolute price change:
     * - bullish: close >= open
     * - bearish: close < open
     * - neutral: close == open
     */
    val candleProvider = CandlestickCartesianLayer.CandleProvider.absolute(
        bullish = CandlestickCartesianLayer.Candle(body = bullishBody),
        neutral = CandlestickCartesianLayer.Candle(body = neutralBody),
        bearish = CandlestickCartesianLayer.Candle(body = bearishBody),
    )


    val candlestickLayer = rememberCandlestickCartesianLayer(
        candleProvider = candleProvider,
        candleSpacing = 10.dp, // add spacing between candles
        scaleCandleWicks = false,
        rangeProvider = rangeProvider,
    )

    val chart = rememberCartesianChart(
        candlestickLayer,
        startAxis = startAxis,
        bottomAxis = bottomAxis,
    )

    // ----- Scroll + Zoom -----

    val scrollState = rememberVicoScrollState(
        scrollEnabled = scrollEnabled,
        //initialScroll = Scroll.Absolute.End, // show the latest candles
    )

    val clampedVisibleCount = remember(candles.size, initialVisibleCandles) {
        candles.size
            .coerceAtLeast(1)
            .coerceAtMost(initialVisibleCandles.coerceAtLeast(1))
    }

    val initialZoom = remember(clampedVisibleCount) {
        // Show approximately [clampedVisibleCount] candles on screen.
        Zoom.x(clampedVisibleCount.toDouble())
    }

    val zoomState: VicoZoomState = rememberVicoZoomState(
        zoomEnabled = scrollEnabled,
        initialZoom = initialZoom,
        // Use default minZoom & maxZoom to avoid empty range issues.
    )

    ProvideVicoTheme(rememberM3VicoTheme()) {
        CartesianChartHost(
            chart = chart,
            modelProducer = modelProducer,
            scrollState = scrollState,
            zoomState = zoomState,
            modifier = modifier
                .fillMaxWidth()
                .height(height),
        )
    }
}

@ThemePreviews
@Composable
private fun CoinCandlestickChartPreview() {
    val candles = remember { generateFakeOhlcCandles(candlesCount = 72, intervalMinutes = 60) }

    CoinCandlestickChart(
        candles = candles,
        height = 260.dp,
        scrollEnabled = true,
    )
}

/**
 * Generates fake OHLC data that roughly mimics crypto candles.
 */
fun generateFakeOhlcCandles(
    nowMillis: Long = System.currentTimeMillis(),
    candlesCount: Int = 60,
    intervalMinutes: Int = 60,
    startPrice: Double = 96_000.0,
): List<CoinOHLCChartCandle.Candle> {
    val result = ArrayList<CoinOHLCChartCandle.Candle>(candlesCount)
    val intervalMillis = intervalMinutes * 60L * 1000L

    // Simple deterministic "noise" using a tiny LCG.
    fun lcg(i: Int): Double {
        var x = 42L
        repeat(i + 1) { x = (1664525L * x + 1013904223L) and 0xFFFFFFFF }
        val u = x.toDouble() / 0x1_0000_0000L // [0,1)
        return (u - 0.5) * 2.0                // [-1,1]
    }

    var lastClose = startPrice

    for (i in 0 until candlesCount) {
        val t = nowMillis - (candlesCount - i) * intervalMillis

        val intraday = 200.0 * sin(i / 6.0)
        val micro = 80.0 * lcg(i)
        val drift = startPrice - i * 15.0

        val open = lastClose
        val rawTarget = drift + intraday + micro
        val close = open + (rawTarget - open) * 0.6

        val high = max(open, close) + abs(30.0 * lcg(i + 100))
        val low = min(open, close) - abs(30.0 * lcg(i + 200))

        result += CoinOHLCChartCandle.Candle(
            timestampMillis = t,
            open = open,
            high = high,
            low = low,
            close = close,
        )

        lastClose = close
    }

    return result.sortedBy { it.timestampMillis }
}
