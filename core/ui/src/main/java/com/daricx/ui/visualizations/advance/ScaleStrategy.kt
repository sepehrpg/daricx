package com.daricx.ui.visualizations.advance

import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.pow


// --------------------------- Baseline & Mode ---------------------------

enum class OffsetBaseline { First, Min, Last, Custom }


sealed class ChartScaleMode {
    data object Raw : ChartScaleMode()
    data object DeltaPercent : ChartScaleMode()
    data object MinMaxPercent : ChartScaleMode()
    data class Offset(
        val baseline: OffsetBaseline,
        val customValue: Double? = null,
    ) : ChartScaleMode()
}

// --------------------------- Strategy interface ---------------------------

sealed interface ScaleStrategy {
    /** Transform raw Y-values to scaled Y-values. */
    fun transform(values: List<Double>): List<Double>

    /** Optional custom Y-axis formatter. */
    val yFormatter: CartesianValueFormatter?

    /** Optional custom range provider. */
    val rangeProvider: CartesianLayerRangeProvider?
}

// --------------------------- Raw (no scaling) ---------------------------

private object RawStrategy : ScaleStrategy {
    override fun transform(values: List<Double>): List<Double> = values
    override val yFormatter: CartesianValueFormatter? = null
    override val rangeProvider: CartesianLayerRangeProvider? = null
}

// --------------------------- Percent Δ vs first ---------------------------

private object PercentDeltaStrategy : ScaleStrategy {
    private val df = DecimalFormat("#,##0.##'%'")
    private val formatter = CartesianValueFormatter.decimal(df)

    override fun transform(values: List<Double>): List<Double> {
        if (values.isEmpty()) return emptyList()
        val base = values.first().takeIf { it != 0.0 } ?: return List(values.size) { 0.0 }
        return values.map { ((it - base) / base) * 100.0 }
    }

    override val yFormatter: CartesianValueFormatter = formatter
    override val rangeProvider: CartesianLayerRangeProvider? = null
}

// --------------------------- Min–Max 0..100% ---------------------------

private object MinMaxStrategy : ScaleStrategy {
    private val df = DecimalFormat("#,##0.##'%'")
    private val formatter = CartesianValueFormatter.decimal(df)

    override fun transform(values: List<Double>): List<Double> {
        if (values.isEmpty()) return emptyList()

        var min = Double.POSITIVE_INFINITY
        var max = Double.NEGATIVE_INFINITY
        for (v in values) {
            if (v < min) min = v
            if (v > max) max = v
        }
        if (min == Double.POSITIVE_INFINITY) return List(values.size) { 0.0 }

        val span = (max - min).takeIf { it != 0.0 } ?: 1.0
        return values.map { ((it - min) / span) * 100.0 }
    }

    override val yFormatter: CartesianValueFormatter = formatter
    override val rangeProvider: CartesianLayerRangeProvider =
        CartesianLayerRangeProvider.fixed(0.0, 100.0)
}

// --------------------------- Offset baseline ---------------------------

private class OffsetStrategy(
    private val baseline: Double,
) : ScaleStrategy {

    override fun transform(values: List<Double>): List<Double> =
        values.map { it - baseline }

    override val yFormatter: CartesianValueFormatter =
        CartesianValueFormatter { _, yPrime, _ ->
            val absolute = yPrime + baseline
            compactMoney(absolute, symbol = "$", decimals = 2)
        }

    override val rangeProvider: CartesianLayerRangeProvider? = null
}

// --------------------------- Strategy picker ---------------------------

private fun computeBaseline(
    values: List<Double>,
    kind: OffsetBaseline,
    custom: Double?,
): Double = when (kind) {
    OffsetBaseline.First  -> values.firstOrNull() ?: 0.0
    OffsetBaseline.Min    -> values.minOrNull() ?: 0.0
    OffsetBaseline.Last   -> values.lastOrNull() ?: 0.0
    OffsetBaseline.Custom -> custom ?: (values.firstOrNull() ?: 0.0)
}
/**
 * Map high-level [ChartScaleMode] to a concrete [ScaleStrategy].
 */
fun pickStrategy(
    mode: ChartScaleMode,
    yRaw: List<Double>,
): ScaleStrategy = when (mode) {
    is ChartScaleMode.Raw          -> RawStrategy
    is ChartScaleMode.DeltaPercent -> PercentDeltaStrategy
    is ChartScaleMode.MinMaxPercent -> MinMaxStrategy
    is ChartScaleMode.Offset       -> {
        val baseline = computeBaseline(yRaw, mode.baseline, mode.customValue)
        OffsetStrategy(baseline)
    }
}
// --------------------------- Money formatting helpers ---------------------------
private fun Double.format(decimals: Int): String {
    val pattern = "#,##0." + "#".repeat(decimals)
    return DecimalFormat(pattern).format(this)
}
private fun compactMoney(
    value: Double,
    symbol: String = "$",
    decimals: Int = 2,
): String {
    val absV = abs(value)
    val core = when {
        absV >= 1e12 -> (value / 1e12).format(decimals) + "T"
        absV >= 1e9  -> (value / 1e9 ).format(decimals) + "B"
        absV >= 1e6  -> (value / 1e6 ).format(decimals) + "M"
        absV >= 1e3  -> (value / 1e3 ).format(decimals) + "K"
        else         -> value.format(decimals)
    }
    return symbol + core
}
val SmartMoneyFormatter: CartesianValueFormatter =
    CartesianValueFormatter { _, v, _ -> compactMoney(v, symbol = "$", decimals = 2) }

// --------------------------- Nice-step helper ---------------------------

fun niceStep(span: Double, targetTicks: Int = 6): Double {
    if (span <= 0.0 || targetTicks <= 0) return 1.0
    val raw = span / targetTicks
    val pow = 10.0.pow(floor(ln(raw) / ln(10.0)))
    val candidates = doubleArrayOf(1.0, 2.0, 2.5, 5.0, 10.0)
    val norm = raw / pow
    val chosen = candidates.firstOrNull { it >= norm } ?: 10.0
    return chosen * pow
}
