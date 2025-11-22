package com.daricx.ui.visualizations.advance

import com.example.model.coins.CoinHistoricalChart
import com.example.model.exchanges.ExchangeVolumeChart

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


fun List<CoinHistoricalChart.Point>.toPricePoints(): List<PricePoint> =
    mapNotNull { point ->
        point.value?.let { v ->
            PricePoint(
                timestampMillis = point.timestampMillis,
                value = v
            )
        }
    }

fun List<ExchangeVolumeChart.Point>.toExchangePricePoints(): List<PricePoint> =
    mapNotNull { point ->
        point.volume?.let { v ->
            PricePoint(
                timestampMillis = point.timestampMillis,
                value = v
            )
        }
    }




