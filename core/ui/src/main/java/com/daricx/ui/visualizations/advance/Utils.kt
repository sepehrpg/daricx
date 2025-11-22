package com.daricx.ui.visualizations.advance

import com.example.model.coins.CoinHistoricalChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.collections.map
import kotlin.math.roundToInt
import kotlin.math.max

/** X-axis formatter for epoch millis → "HH:mm". */
internal fun timeFormatter(zoneId: ZoneId): CartesianValueFormatter {
    val fmt = DateTimeFormatter.ofPattern("HH:mm").withZone(zoneId)
    return CartesianValueFormatter { _, x, _ ->
        fmt.format(Instant.ofEpochMilli(x.toLong()))
    }
}

/** X-axis formatter when you only have index-based labels. */
internal fun indexToLabelFormatter(labels: List<String>): CartesianValueFormatter =
    CartesianValueFormatter { _, x, _ ->
        if (labels.isEmpty()) {
            return@CartesianValueFormatter " "
        }

        val index = x.roundToInt().coerceIn(0, labels.lastIndex)
        val label = labels[index]

        if (label.isEmpty()) " " else label
    }




fun buildTimeXAxisLabels(
    points: List<PricePoint>,
    zoneId: ZoneId = ZoneId.systemDefault(),
): List<String> {
    if (points.isEmpty()) return emptyList()

    val first = points.first().timestampMillis
    val last = points.last().timestampMillis
    val spanMillis = last - first
    val spanDays = spanMillis / (24.0 * 60 * 60 * 1000.0)

    val pattern = when {
        spanDays <= 1.0  -> "HH:mm"
        spanDays <= 7.0  -> "MMM d"
        spanDays <= 31.0 -> "MMM d"
        else             -> "MMM yy"
    }

    val formatter = DateTimeFormatter.ofPattern(pattern).withZone(zoneId)

    return points.map { p ->
        formatter.format(Instant.ofEpochMilli(p.timestampMillis))
    }
}



