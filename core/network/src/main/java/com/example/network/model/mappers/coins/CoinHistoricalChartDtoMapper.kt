package com.example.network.model.mappers.coins

import com.example.model.coins.CoinHistoricalChart
import com.example.network.model.coins.CoinHistoricalChartDto
import com.example.network.model.coins.CoinHistoricalChartPointDto
fun CoinHistoricalChartDto.toDomain(): CoinHistoricalChart =
    CoinHistoricalChart(
        prices       = (prices ?: emptyList()).map { it.toDomain() },
        marketCaps   = (marketCaps ?: emptyList()).map { it.toDomain() },
        totalVolumes = (totalVolumes ?: emptyList()).map { it.toDomain() }
    )

private fun CoinHistoricalChartPointDto.toDomain(): CoinHistoricalChart.Point =
    CoinHistoricalChart.Point(
        timestampMillis = timestampMillis,
        value = value
    )
