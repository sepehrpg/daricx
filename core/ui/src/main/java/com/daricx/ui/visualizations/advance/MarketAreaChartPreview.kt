package com.daricx.ui.visualizations.advance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.ThemePreviews




@ThemePreviews
@Composable
fun ExampleMarket_OffsetFirst() {
    val fakeChart = remember {
        generateFakeCoinHistoricalChart(
            days = 1,
            pointsPerDay = 288,
            startPrice = 96_000.0,
            endPrice = 95_500.0
        )
    }

    val points = remember(fakeChart) { fakeChart.prices.toPricePoints() }

    Box(Modifier.padding(16.dp)) {
        MarketAreaChart(
            points = points,
            mode = ChartScaleMode.Offset(
                baseline = OffsetBaseline.Min,
                customValue = null
            ),
            lineColor = Color(0xFFE53935),
        )
    }
}




@ThemePreviews
@Composable
fun ExampleMarket_OffsetFirst2() {
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
            mode = ChartScaleMode.Offset(
                baseline = OffsetBaseline.Min,
                customValue = null
            ),
            xLabelsOverride = labels,
            lineColor = Color(0xFFE53935),
        )

       /* MarketAreaChart(
            points = points,
            mode = ChartScaleMode.DeltaPercent
        )*/
    }
}