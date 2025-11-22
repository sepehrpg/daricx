package com.daricx.markets.ui.screen.coins.coin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.daricx.markets.data.CoinDetailsFakes
import com.daricx.markets.ui.model.PricePercentChangedItem
import com.daricx.markets.ui.screen.ErrorBox
import com.daricx.markets.ui.screen.LoadingBox
import com.daricx.markets.ui.screen.coins.coin.CoinDetailsUiState
import com.daricx.ui.PercentChangeView
import com.daricx.ui.visualizations.advance.ChartScaleMode
import com.daricx.ui.visualizations.advance.CoinCandlestickChart
import com.daricx.ui.visualizations.advance.MarketAreaChart
import com.daricx.ui.visualizations.advance.OffsetBaseline
import com.daricx.ui.visualizations.advance.buildTimeXAxisLabels
import com.daricx.ui.visualizations.advance.toPricePoints
import com.example.common.currency.CurrencyStyle
import com.example.common.numbers.facade.prettyBTC
import com.example.common.numbers.facade.prettyPriceCrypto
import com.example.designsystem.component.AppPullToRefresh
import com.example.designsystem.component.text.AppText
import com.example.designsystem.component.AppVerticalDivider
import com.example.designsystem.component.cards.AppCard
import com.example.designsystem.component.icons.AppFilledTonalIconButton
import com.example.designsystem.component.icons.AppIcon
import com.example.designsystem.component.tabs.AppIconTab
import com.example.designsystem.component.tabs.AppPillIconTabs
import com.example.designsystem.component.tabs.AppPillTabs
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import com.example.model.coins.CoinDetails
import com.example.model.coins.CoinHistoricalChart
import com.example.model.coins.mapper.percentChangeFor
import com.example.model.option.ChartUiType
import com.example.model.option.CryptoChartType
import com.example.model.option.CryptoTimeRange


@Composable
fun OverviewCoinRoute(
    uiState: CoinDetailsUiState,
    onRefresh: () -> Unit,
    onTimeRangeSelected: (CryptoTimeRange) -> Unit,
){
    OverviewCoinScreen(
        uiState = uiState,
        onRefresh = onRefresh,
        onTimeRangeSelected = onTimeRangeSelected
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewCoinScreen(
    modifier: Modifier = Modifier,
    uiState: CoinDetailsUiState,
    onRefresh: () -> Unit,
    onTimeRangeSelected: (CryptoTimeRange) -> Unit,
){
    Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {

        val refreshState = rememberPullToRefreshState()
        // Show pull-to-refresh spinner only when we are refreshing over existing content.
        val isRefreshing = uiState.isLoading && uiState.coinDetails != null

        AppPullToRefresh(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            state = refreshState,
            modifier = modifier.fillMaxSize(),
        ) {
            when {
                uiState.isLoading && uiState.coinDetails == null -> {
                    LoadingBox(modifier = modifier.height(2.dp))
                }

                uiState.error != null && uiState.coinDetails == null -> {
                    ErrorBox(
                        message = uiState.error,
                        onRetry = onRefresh,
                    )
                }

                uiState.coinDetails == null -> {
                    ErrorBox(
                        message = "Empty List",
                        onRetry = onRefresh,
                    )
                }
                else -> {
                    OverviewCoinScreenContent(
                        uiState = uiState,
                        coinDetails = uiState.coinDetails,
                        coinHistoricalChart = uiState.coinHistoricalChart,
                        modifier = modifier,
                        onTimeRangeSelected = onTimeRangeSelected
                    )
                }
            }
        }
    }
}


@Composable
private fun OverviewCoinScreenContent(
    uiState: CoinDetailsUiState,
    coinDetails: CoinDetails,
    coinHistoricalChart: CoinHistoricalChart?,
    modifier: Modifier = Modifier,
    onTimeRangeSelected: (CryptoTimeRange) -> Unit,
){

    // chart type
    val cryptoChartTypes = remember { CryptoChartType.entries }
    val cryptoChartTypeLabels = cryptoChartTypes.map { it.label }
    var selectedChartType by remember { mutableStateOf(CryptoChartType.PRICE) }

    // chart ui ( linear - candle )
    var selectedChartUiType by remember { mutableStateOf(ChartUiType.LINE) }
    val allUiTypes = listOf(ChartUiType.LINE, ChartUiType.CANDLE)
    val availableUiTypes: List<ChartUiType> =
        if (selectedChartType == CryptoChartType.MARKETS) {
            listOf(ChartUiType.LINE)
        } else {
            allUiTypes
        }
    val uiSelectedIndex = availableUiTypes.indexOf(selectedChartUiType)
        .takeIf { it >= 0 } ?: 0
    val availableUiTabs = availableUiTypes.map { type ->
        when (type) {
            ChartUiType.LINE ->
                AppIconTab(AppIcons.ChartType1, "Price Chart")
            ChartUiType.CANDLE ->
                AppIconTab(AppIcons.CandlestickChart, "CandlestickChart")
        }
    }

    val percentChange = remember(uiState.selectedRange, coinDetails) {
        coinDetails.percentChangeFor(uiState.selectedRange)
    }


    LazyColumn(modifier.fillMaxSize().padding(top=15.dp, end = 8.dp, start = 8.dp)) {
        item {
            FlowRow(verticalArrangement = Arrangement.Center, modifier = modifier.padding(horizontal = 4.dp)){
                AppText(
                    "${coinDetails.name}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                )
                Spacer(modifier.width(10.dp))
                AppCard(
                    colors =  CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(5.dp)
                ){
                    AppText( "#${coinDetails.marketCapRank}",modifier= modifier.padding(horizontal = 4.dp, vertical = 2.dp), style = MaterialTheme.typography.labelLarge)
                }
            }
        }

        item {
            Row(modifier.fillMaxWidth()){
                val currentPriceD: Double? = coinDetails.marketData?.currentPrice?.get(CurrencyStyle.usd().name)
                val currentPriceS: String = currentPriceD?.prettyPriceCrypto(CurrencyStyle.usd())?:""



                AppText(
                    currentPriceS,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier.padding(top = 3.dp, bottom = 0.dp, start = 8.dp, end = 8.dp)
                )

                Row(modifier.weight(1f), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically){
                    AppFilledTonalIconButton(
                        onClick = {},
                        modifier=modifier.size(32.dp),
                        shape = RoundedCornerShape(8.dp)) {
                        AppIcon(AppIcons.NotificationIcon, contentDescription = "",
                            modifier=modifier.size(20.dp))
                    }
                    Spacer(modifier.width(12.dp))
                    PercentChangeView(percent = percentChange ,size = 20.dp, showBackground = true,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                }
            }
            Row(modifier.padding(top = 3.dp), verticalAlignment = Alignment.CenterVertically){
                AppIcon(
                    AppIcons.SwapVert,
                    contentDescription = "Exchange Icon",
                    modifier = modifier.size(24.dp)
                )
                Spacer(modifier.width(1.dp))
                val btcPrice: Double? = coinDetails?.marketData?.currentPrice?.
                get(CurrencyStyle.btcPrefix().name)
                AppText(
                    "${btcPrice?.prettyBTC()}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                )
            }
        }

        item {
            Row(modifier.fillMaxWidth().padding(start = 1.dp, end = 5.dp, top = 12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                AppPillTabs(
                    items = cryptoChartTypeLabels,
                    selectedIndex = cryptoChartTypes.indexOf(selectedChartType),
                    onSelected = {
                        idx ->
                        selectedChartType = cryptoChartTypes[idx]
                        selectedChartUiType = ChartUiType.LINE

                    },
                    modifier = modifier.width(150.dp)
                )

                AppPillIconTabs(
                    items = availableUiTabs,
                    selectedIndex = uiSelectedIndex,
                    onSelected = { idx ->
                        selectedChartUiType = availableUiTypes[idx]
                    },
                    modifier = if(availableUiTabs.size==1) modifier.width(40.dp) else modifier.width(80.dp)
                )

            }
        }

        item {
            val isChartLoading = uiState.isChartLoading
            val chartError = uiState.chartError

            val hasLineData = coinHistoricalChart != null
            val hasCandleData = uiState.ohlcCandles != null
            val isCandleUi = selectedChartUiType == ChartUiType.CANDLE

            // Only consider data for the currently selected UI type.
            val hasDataForSelectedUi = if (isCandleUi) hasCandleData else hasLineData

            Box(
                Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth()
                    .height(220.dp)
            ) {

                // --- LINE chart (area) ---
                if (!isCandleUi && hasLineData) {
                    coinHistoricalChart?.let { chart ->
                        val points = remember(chart, selectedChartType) {
                            when (selectedChartType) {
                                CryptoChartType.PRICE   -> chart.prices.toPricePoints()
                                CryptoChartType.MARKETS -> chart.marketCaps.toPricePoints()
                            }
                        }

                        val xLabels = remember(points) {
                            buildTimeXAxisLabels(points)
                        }

                        val ascendingColor = MaterialTheme.colorScheme.secondary
                        val descendingColor = MaterialTheme.colorScheme.tertiary
                        val unknownColor = MaterialTheme.colorScheme.onSurfaceVariant

                        val chartColor = remember(points, percentChange) {
                            when {
                                percentChange > 0.0 -> ascendingColor
                                percentChange < 0.0 -> descendingColor
                                else                -> unknownColor
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

                // --- CANDLE chart (OHLC) ---
                if (isCandleUi && hasCandleData) {
                    uiState.ohlcCandles?.let { ohlc ->
                        CoinCandlestickChart(
                            candles = ohlc.candles,
                            modifier = Modifier.fillMaxSize(),
                            height = 220.dp,
                            scrollEnabled = true,
                        )
                    }
                }

                // --- Loading states ---

                // Full-screen loading when there is no data yet for the selected UI type.
                if (isChartLoading && !hasDataForSelectedUi) {
                    LoadingBoxChart(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                // Semi-transparent overlay loading when we already have some data.
                if (isChartLoading && hasDataForSelectedUi) {
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

                // --- Error state for selected UI type ---
                if (chartError != null && !hasDataForSelectedUi && !isChartLoading) {
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

            PricePercentChangedCard(
                items = listOf(
                    PricePercentChangedItem(
                        "24 hours",
                        coinDetails.marketData?.priceChangePercentage24h ?: 0.0
                    ),
                    PricePercentChangedItem("7 days", coinDetails.marketData?.priceChangePercentage7d?:0.0),
                    PricePercentChangedItem("30 days", coinDetails.marketData?.priceChangePercentage30d?:0.0),
                    PricePercentChangedItem("60 days", coinDetails.marketData?.priceChangePercentage60d?:0.0),
                    PricePercentChangedItem("1 year", coinDetails.marketData?.priceChangePercentage1y?:0.0)
                )
            )

        }

        item {

            MarketStatsCard(
                leftStats = listOf(
                    "High 24H" to coinDetails.marketData?.high24h?.get(CurrencyStyle.usd().name)?.prettyPriceCrypto().toString(),
                    "Market Cap" to coinDetails.marketData?.marketCap?.get(CurrencyStyle.usd().name)?.prettyPriceCrypto().toString(),
                    "Total Supply" to coinDetails.marketData?.totalSupply?.prettyPriceCrypto().toString(),
                    "All-Time High" to coinDetails.marketData?.ath?.get(CurrencyStyle.usd().name)?.prettyPriceCrypto().toString(),
                    "Fully Diluted Valuation" to coinDetails.marketData?.fullyDilutedValuation?.get(CurrencyStyle.usd().name)?.prettyPriceCrypto().toString(),
                    "market Cap Rank" to coinDetails.marketData?.marketCapRank.toString(),
                ),
                rightStats = listOf(
                    "Low 24H" to coinDetails.marketData?.low24h?.get(CurrencyStyle.usd().name)?.prettyPriceCrypto(currency = CurrencyStyle.usd()).toString(),
                    "Volume" to coinDetails.marketData?.totalVolume?.get(CurrencyStyle.usd().name)?.prettyPriceCrypto().toString(),
                    "Max Supply" to coinDetails.marketData?.maxSupply?.prettyPriceCrypto().toString(),
                    "All-Time Low" to coinDetails.marketData?.atl?.get(CurrencyStyle.usd().name)?.prettyPriceCrypto().toString(),
                    "Circulating Supply" to coinDetails.marketData?.circulatingSupply?.prettyPriceCrypto().toString(),
                )
            )
        }

        item{
            Spacer(modifier.height(60.dp))
        }
    }

}


@Composable
private fun PricePercentChangedCard(
    modifier: Modifier = Modifier,
    items: List<PricePercentChangedItem>
) {
    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 3.dp, end = 3.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items.forEachIndexed { index, item ->
                PricePercentChanged(item)
                if (index < items.lastIndex) Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }
}

@Composable
private fun PricePercentChanged(
    item: PricePercentChangedItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppText(
            text = item.label,
            style = MaterialTheme.typography.labelMedium
        )
        PercentChangeView(
            percent = item.percent,
            style = MaterialTheme.typography.labelSmall,
            showBackground = false
        )
    }
}






@Composable
private fun MarketStatsCard(
    modifier: Modifier = Modifier,
    leftStats: List<Pair<String, String>>,
    rightStats: List<Pair<String, String>>
) {

    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 3.dp, end = 3.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {

        Column {
            Spacer(modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row(verticalAlignment = Alignment.CenterVertically){
                    AppText("Statistics",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    AppIcon(
                        AppIcons.ChevronRightIcon, contentDescription = "",
                        modifier = modifier.size(18.dp)
                    )
                }
                AppText("See All",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.inversePrimary
                )
            }


            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left column
                MarketStatsColumn(
                    stats = leftStats,
                    modifier = Modifier.weight(1f)
                )

                Row(modifier = Modifier.wrapContentHeight().weight(1f)) {
                    AppVerticalDivider()

                    Spacer(modifier = Modifier.width(10.dp))
                    MarketStatsColumn(stats = rightStats)
                }
            }
        }
    }
}



@Composable
private fun MarketStatsColumn(
    stats: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        stats.forEachIndexed { index, (label, value) ->
            MarketStatItem(label = label, value = if (isValidStatValue(value)) value else "-" )
            if (index < stats.lastIndex) Spacer(modifier = Modifier.height(7.dp))
        }
    }
}

@Composable
private fun MarketStatItem(
    label: String,
    value: String
) {
    AppText(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(2.dp))
    AppText(
        text = value,
        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
    )
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
private fun OverviewCoinScreenPreview(){
    val coinDetailsFakeData = CoinDetailsFakes.bitcoin()
    AppThemedPreview {
        OverviewCoinScreen(
            uiState = CoinDetailsUiState(
                coinDetails = coinDetailsFakeData,
                isLoading = false,
                error = null
            ),
            onRefresh = {},
            onTimeRangeSelected = {}
        )
    }
}


private fun isValidStatValue(raw: String?): Boolean {
    val value = raw?.trim() ?: return false
    if (value.isEmpty()) return false
    if (value.equals("null", ignoreCase = true)) return false

    // "$0", "0", "0.0", "0.00"  ...
    val numericCandidate = value
        .replace("$", "")
        .replace(",", "")
        .trim()

    val number = numericCandidate.toDoubleOrNull()
    if (number != null && number == 0.0) return false

    return true
}