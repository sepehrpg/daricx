package com.example.network.model.mappers.exchanges

import com.example.model.exchanges.ExchangeVolumeChart
import com.example.network.model.exchanges.ExchangeVolumeChartDto



/**
 * Mapping:
 * ExchangeVolumeChartDto -> ExchangeVolumeChart
 */
fun ExchangeVolumeChartDto.toDomain(): ExchangeVolumeChart =
    ExchangeVolumeChart(
        points = points.map { it.toDomain() }
    )

private fun ExchangeVolumeChartDto.Point.toDomain(): ExchangeVolumeChart.Point =
    ExchangeVolumeChart.Point(
        timestampMillis = timestampMillis,
        volume = volume
    )
