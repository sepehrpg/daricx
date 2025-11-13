// File: com/daricx/ui/visualizations/advance/ScaleStrategy.kt
package com.daricx.ui.visualizations.advance

import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.pow

enum class OffsetBaseline { First, Min, Last, Custom }

sealed interface ScaleStrategy {
    fun transform(values: List<Double>): List<Double>
    val yFormatter: CartesianValueFormatter?
    val rangeProvider: CartesianLayerRangeProvider?
}

private object RawStrategy : ScaleStrategy {
    override fun transform(values: List<Double>) = values
    override val yFormatter: CartesianValueFormatter? = null
    override val rangeProvider: CartesianLayerRangeProvider? = null
}

private object PercentDeltaStrategy : ScaleStrategy {
    private val df = DecimalFormat("#,##0.##'%'")
    private val formatter = CartesianValueFormatter.decimal(df)

    override fun transform(values: List<Double>): List<Double> {
        if (values.isEmpty()) return values
        val base = values.first().takeIf { it != 0.0 } ?: return List(values.size) { 0.0 }
        return values.map { ((it - base) / base) * 100.0 }
    }

    override val yFormatter = formatter
    override val rangeProvider: CartesianLayerRangeProvider? = null
}

private object MinMaxStrategy : ScaleStrategy {
    private val df = DecimalFormat("#,##0.##'%'")
    private val formatter = CartesianValueFormatter.decimal(df)

    override fun transform(values: List<Double>): List<Double> {
        if (values.isEmpty()) return values
        val min = values.minOrNull() ?: 0.0
        val max = values.maxOrNull() ?: min
        val span = (max - min).takeIf { it != 0.0 } ?: 1.0
        return values.map { ((it - min) / span) * 100.0 }
    }

    override val yFormatter = formatter
    override val rangeProvider = CartesianLayerRangeProvider.fixed(0.0, 100.0)
}

private class OffsetStrategy(
    private val baseline: Double,
) : ScaleStrategy {
    override fun transform(values: List<Double>) = values.map { it - baseline }

    override val yFormatter: CartesianValueFormatter =
        CartesianValueFormatter { _, yPrime, _ ->
            compactMoney(yPrime + baseline, symbol = "$", decimals = 2)
        }

    override val rangeProvider: CartesianLayerRangeProvider? = null
}

private fun computeBaseline(values: List<Double>, kind: OffsetBaseline, custom: Double?): Double =
    when (kind) {
        OffsetBaseline.First -> values.firstOrNull() ?: 0.0
        OffsetBaseline.Min   -> values.minOrNull() ?: 0.0
        OffsetBaseline.Last  -> values.lastOrNull() ?: 0.0
        OffsetBaseline.Custom -> custom ?: (values.firstOrNull() ?: 0.0)
    }

fun pickStrategy(
    normalize: Boolean,
    mode: String,
    yRaw: List<Double>,
    offsetBaseline: OffsetBaseline,
    offsetCustomValue: Double?
): ScaleStrategy {
    if (!normalize) return RawStrategy
    return when (mode.lowercase()) {
        "delta"  -> PercentDeltaStrategy
        "scale"  -> MinMaxStrategy
        "offset" -> OffsetStrategy(computeBaseline(yRaw, offsetBaseline, offsetCustomValue))
        else     -> RawStrategy
    }
}

/* --------------------------- Helpers: money formatting --------------------------- */

private fun Double.format(decimals: Int): String =
    DecimalFormat("#,##0." + "#".repeat(decimals)).format(this)

private fun compactMoney(value: Double, symbol: String = "$", decimals: Int = 2): String {
    val absV = abs(value)
    return when {
        absV >= 1e12 -> symbol + (value / 1e12).format(decimals) + "T"
        absV >= 1e9  -> symbol + (value / 1e9 ).format(decimals) + "B"
        absV >= 1e6  -> symbol + (value / 1e6 ).format(decimals) + "M"
        absV >= 1e3  -> symbol + (value / 1e3 ).format(decimals) + "K"
        else         -> symbol + value.format(decimals)
    }
}

val SmartMoneyFormatter: CartesianValueFormatter =
    CartesianValueFormatter { _, v, _ -> compactMoney(v, symbol = "$", decimals = 2) }

/* --------------------------- Nice-step helper (optional public) --------------------------- */

fun niceStep(span: Double, targetTicks: Int = 6): Double {
    if (span <= 0.0 || targetTicks <= 0) return 1.0
    val raw = span / targetTicks
    val pow = 10.0.pow(floor(ln(raw) / ln(10.0)))
    val candidates = doubleArrayOf(1.0, 2.0, 2.5, 5.0, 10.0)
    val norm = raw / pow
    val chosen = candidates.firstOrNull { it >= norm } ?: 10.0
    return chosen * pow
}
