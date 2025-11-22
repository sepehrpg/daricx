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
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
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
 * Market-style area chart with a baseline-friendly scaling strategy.
 *
 * Scaling modes via [mode]:
 * - [ChartScaleMode.DeltaPercent]: percentage change vs. first value (Y axis in %)
 * - [ChartScaleMode.MinMaxPercent]: min–max normalization to 0..100 (%)
 * - [ChartScaleMode.Offset]: plot deltas around a baseline (First/Min/Last/Custom),
 *   but format Y as absolute values (e.g. 102k$, 103k$)
 * - [ChartScaleMode.Raw]: raw values.
 */
@Composable
fun MarketAreaChart(
    points: List<PricePoint>,
    modifier: Modifier = Modifier,
    height: Dp = 220.dp,
    lineColor: Color = Color(0xFFE53935),
    zoneId: ZoneId = ZoneId.systemDefault(),
    xLabelsOverride: List<String>? = null,
    mode: ChartScaleMode = ChartScaleMode.DeltaPercent,
    yValueFormatter: CartesianValueFormatter? = null,
) {
    val modelProducer = remember { CartesianChartModelProducer() }

    // Choose scaling strategy and transform Y.
    val yRaw = remember(points) { points.map { it.value } }
    val strategy = remember(mode, yRaw) {
        pickStrategy(mode, yRaw)
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
            scrollState = rememberVicoScrollState(scrollEnabled = false),
            modelProducer = modelProducer,
            modifier = modifier.fillMaxWidth().height(height),
        )
    }
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




