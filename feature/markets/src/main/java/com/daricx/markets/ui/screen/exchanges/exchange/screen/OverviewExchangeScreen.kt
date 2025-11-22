package com.daricx.markets.ui.screen.exchanges.exchange.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.daricx.markets.data.ExchangeDetailsFakes
import com.daricx.markets.ui.mapper.ExchangeLinkItemsMapper
import com.daricx.markets.ui.model.LinkItem
import com.daricx.markets.ui.screen.ErrorBox
import com.daricx.markets.ui.screen.LoadingBox
import com.daricx.markets.ui.screen.exchanges.exchange.ExchangeDetailsUiState
import com.daricx.ui.visualizations.advance.ChartScaleMode
import com.daricx.ui.visualizations.advance.MarketAreaChart
import com.daricx.ui.visualizations.advance.OffsetBaseline
import com.daricx.ui.visualizations.advance.buildTimeXAxisLabels
import com.daricx.ui.visualizations.advance.toExchangePricePoints
import com.example.common.currency.CurrencyStyle
import com.example.common.numbers.facade.prettyPrice
import com.example.designsystem.component.AppPullToRefresh
import com.example.designsystem.component.chips.AppElevatedAssistChip
import com.example.designsystem.component.icons.AppIcon
import com.example.designsystem.component.tabs.AppPillTabs
import com.example.designsystem.component.text.AppText
import com.example.designsystem.component.text.AppTextReadMoreHtmlFormat
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import com.example.model.exchanges.ExchangeDetail
import com.example.model.exchanges.ExchangeVolumeChart
import com.example.model.option.CryptoTimeRange
import kotlin.collections.forEach


@Composable
fun OverviewExchangeRoute(
    uiState: ExchangeDetailsUiState,
    onRefresh: () -> Unit,
    onTimeRangeSelected: (CryptoTimeRange) -> Unit,
) {
    OverviewExchangeScreen(
        uiState = uiState,
        onRefresh = onRefresh,
        onTimeRangeSelected = onTimeRangeSelected
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewExchangeScreen(
    modifier: Modifier = Modifier,
    uiState: ExchangeDetailsUiState,
    onTimeRangeSelected: (CryptoTimeRange) -> Unit,
    onRefresh: () -> Unit,
) {
    val refreshState = rememberPullToRefreshState()
    // Show pull-to-refresh spinner only when we are refreshing over existing content.
    val isRefreshing = uiState.isLoading && uiState.exchangeDetails != null

    Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        AppPullToRefresh(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            state = refreshState,
            modifier = modifier.fillMaxSize(),
        ) {
            when {

                uiState.isLoading && uiState.exchangeDetails == null -> {
                    LoadingBox(modifier = modifier.height(2.dp))
                }

                uiState.error != null && uiState.exchangeDetails == null -> {
                    ErrorBox(
                        message = uiState.error,
                        onRetry = onRefresh,
                    )
                }

                uiState.exchangeDetails == null -> {
                    ErrorBox(
                        message = "Empty List",
                        onRetry = onRefresh,
                    )
                }

                else -> {
                    OverviewExchangeScreenContent(
                        uiState = uiState,
                        exchangeDetail = uiState.exchangeDetails,
                        exchangeVolumeChart = uiState.exchangeVolumeChart,
                        onTimeRangeSelected = onTimeRangeSelected
                    )
                }
            }
        }
    }
}


@Composable
private fun OverviewExchangeScreenContent(
    exchangeDetail: ExchangeDetail,
    uiState: ExchangeDetailsUiState,
    exchangeVolumeChart: ExchangeVolumeChart?,
    modifier: Modifier = Modifier,
    onTimeRangeSelected: (CryptoTimeRange) -> Unit,
){
    LazyColumn(modifier.fillMaxSize().padding(top=15.dp, end = 8.dp, start = 8.dp)) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(horizontal = 2.dp)){
                AppText(
                    "Trading Volume 24H",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            Row(modifier.fillMaxWidth()){
                val tradeVolume24hBtc: Double? = exchangeDetail.tradeVolume24hBtc
                AppText(
                    tradeVolume24hBtc?.prettyPrice(CurrencyStyle.btcSuffixCode())?:"",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.padding(vertical = 3.dp, horizontal = 3.dp)
                )
            }
        }


        item {
            val isChartLoading = uiState.isChartLoading
            val chartError = uiState.chartError
            val hasChartData = exchangeVolumeChart != null

            Box(
                Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth()
                    .height(220.dp)
            ) {

                if (hasChartData) {
                    exchangeVolumeChart.let { chart ->
                        val points = remember(chart) {
                            chart.points.toExchangePricePoints()
                        }
                        val xLabels = remember(points) {
                            buildTimeXAxisLabels(points)
                        }
                        val ascendingColor = MaterialTheme.colorScheme.secondary
                        val descendingColor = MaterialTheme.colorScheme.tertiary
                        val unknowColor = MaterialTheme.colorScheme.onSurfaceVariant

                        val chartColor = remember(points) {
                            val first = points.first().value
                            val last = points.last().value

                            when {
                                last > first -> ascendingColor          // up
                                last < first -> descendingColor          // down
                                else -> unknowColor
                            }
                        }

                        MarketAreaChart(
                            points = points,
                            xLabelsOverride = xLabels,
                            mode = ChartScaleMode.Offset(
                                baseline = OffsetBaseline.Min,
                                customValue = null
                            ),
                            lineColor = chartColor,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                if (isChartLoading && !hasChartData) {
                    LoadingBoxChart(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                if (isChartLoading && hasChartData) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingBoxChart(
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp)
                        )
                    }
                }

                if (chartError != null && !hasChartData && !isChartLoading) {
                    ErrorBoxChart(
                        message = chartError,
                        onRetry = { onTimeRangeSelected(uiState.selectedRange) }
                    )
                }
            }
        }

        item {
            val cryptoTimeRange = CryptoTimeRange.entries
            val labels = cryptoTimeRange.map { it.label }

            val selectedIdx = cryptoTimeRange.indexOf(uiState.selectedRange)
                .takeIf { it >= 0 } ?: 0

            Box(
                modifier
                    .fillMaxWidth()
                    .padding(start = 1.dp, end = 4.dp, top = 2.dp)
            ) {
                AppPillTabs(
                    items = labels,
                    selectedIndex = selectedIdx,
                    onSelected = { idx ->
                        val range = cryptoTimeRange[idx]
                        onTimeRangeSelected(range)
                    },
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        item {
            AboutExchange(exchangeDetail = exchangeDetail)
        }

        item{
            LinksChip(
                links = ExchangeLinkItemsMapper.buildCoinCommunityLinks(exchangeDetail) ,
                title = ""
            )
            Spacer(modifier.height(2.dp))
        }
    }
}


@Composable
private fun AboutExchange(
    modifier: Modifier= Modifier,
    exchangeDetail: ExchangeDetail
) {
    val exchangeName = exchangeDetail.name
    val description = exchangeDetail.description

    Column(modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, top = 30.dp, bottom = 2.dp)){

        AppText(
            "About $exchangeName",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = modifier.height(10.dp))

        AppTextReadMoreHtmlFormat(
            modifier = modifier,
            text = description?:"",
            collapsedMaxLines = 5,
            readMoreText = "Show more",
            readLessText = "Show less",
            readMoreColor = MaterialTheme.colorScheme.inversePrimary
        )
    }

}



@Composable
private fun LinksChip(
    modifier: Modifier= Modifier,
    links: List<LinkItem>,
    title: String
){
    val uriHandler = LocalUriHandler.current

    Column(
        modifier =modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, top = 0.dp)
    ){
        AppText(
            title,
            style = MaterialTheme.typography.titleMedium,
        )
        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ){
            links.forEach {
                AppElevatedAssistChip(
                    onClick = { runCatching { uriHandler.openUri(it.url) } },
                    label = { AppText(it.label, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)) },
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        if (it.icon!=null){
                            Image(it.icon,
                                null,
                                modifier = modifier.size(18.dp)
                            )

                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun LoadingBoxChart(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 3.dp
        )
    }
}

@Composable
private fun ErrorBoxChart(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppIcon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(8.dp))

            AppText(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            FilledTonalButton(onClick = onRetry) {
                AppText(
                    text = "Retry",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}



@ThemePreviews
@Composable
fun OverviewExchangeScreenPreview() {
    val exchangeDetailFakeData = ExchangeDetailsFakes.binanceFromReal()
    AppThemedPreview {
        OverviewExchangeScreen(
            uiState = ExchangeDetailsUiState(
                exchangeDetails = exchangeDetailFakeData,
                isLoading = false,
                error = null
            ),
            onRefresh = {},
            onTimeRangeSelected = {}
        )
    }
}

