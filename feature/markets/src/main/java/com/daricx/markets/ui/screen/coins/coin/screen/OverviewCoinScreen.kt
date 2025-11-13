package com.daricx.markets.ui.screen.coins.coin.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.daricx.markets.data.CoinDetailsFakes
import com.daricx.markets.ui.model.PricePercentChangedItem
import com.daricx.ui.PercentChangeView
import com.daricx.ui.visualizations.advance.MarketAreaChart
import com.daricx.ui.visualizations.advance.OffsetBaseline
import com.daricx.ui.visualizations.advance.PricePoint
import com.daricx.ui.visualizations.advance.generateBtcLike2hSeries
import com.example.common.currency.CurrencyStyle
import com.example.common.numbers.facade.prettyBTC
import com.example.common.numbers.facade.prettyPrice
import com.example.common.numbers.facade.prettyUSD
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
import com.example.model.option.CryptoChart
import com.example.model.option.CryptoTimeRange


@Composable
fun OverviewCoinRoute(){
   val coinDetailsFakeData = CoinDetailsFakes.bitcoin()
    OverviewCoinScreen(coinDetails = coinDetailsFakeData)
}

@Composable
fun OverviewCoinScreen(
    modifier: Modifier = Modifier,
    coinDetails: CoinDetails?,
){
    Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        LazyColumn(modifier.fillMaxSize().padding(top=15.dp, end = 8.dp, start = 8.dp)) {

            item {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(horizontal = 4.dp)){
                    AppText(
                        "${coinDetails?.name}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier.width(10.dp))
                    AppCard(
                        colors =  CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ){
                        AppText( "#${coinDetails?.marketCapRank}",modifier= modifier.padding(horizontal = 4.dp, vertical = 2.dp), style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            item {
                Row(modifier.fillMaxWidth()){
                    val currentPrice: Double? = coinDetails?.marketData?.currentPrice?.
                    get(CurrencyStyle.usd().name)
                    AppText(
                        currentPrice?.prettyPrice(CurrencyStyle.usd())?:"",
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
                        PercentChangeView(percent = coinDetails?.marketData?.priceChangePercentage24h?:0.0 ,size = 20.dp, showBackground = true,
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
                val cryptoChart = remember { CryptoChart.entries }
                val labels = cryptoChart.map { it.label }
                var selectedIdx = remember {
                    mutableStateOf(cryptoChart.indexOf(CryptoChart.PRICE).takeIf { it >= 0 } ?: 0)
                }

                Row(modifier.fillMaxWidth().padding(start = 1.dp, end = 5.dp, top = 12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                    AppPillTabs(
                        items = labels,
                        selectedIndex = selectedIdx.value,
                        onSelected = { idx -> selectedIdx.value = idx },
                        modifier = modifier.width(150.dp)
                    )

                    var selected = remember { mutableStateOf(0) }
                    AppPillIconTabs(
                        items = listOf(
                            AppIconTab(AppIcons.ChartType1, "Price Chart"),
                            AppIconTab(AppIcons.CandlestickChart, "CandlestickChart"),
                        ),
                        selectedIndex = selected.value,
                        onSelected = { selected.value = it },
                        modifier =  modifier.width(80.dp)
                    )
                    /*AppFilledTonalIconButton(
                        onClick = {},
                        modifier=modifier.size(33.dp),
                        shape = RoundedCornerShape(8.dp)) {
                        AppIcon(AppIcons.ChartType1, contentDescription = "",
                            modifier=modifier.size(20.dp))
                    }*/

                }
            }

            item {
                val labels = listOf("02:00","16:00","20:00","00:00","04:00","08:00","08:30","09:00")
                val values = listOf(
                    100_000.0, 100_001.0, 100_002.0, 100_003.5, 100_002.8, 100_004.2, 100_003.7, 100_005.1
                )
                val points = remember { values.map { PricePoint(0L, it) } }

                Box(Modifier.padding(vertical = 12.dp, horizontal = 0.dp)) {
                    val (labels2h, btcPrices2h) = generateBtcLike2hSeries()
                    //val points = btcPrices2h.map { PricePoint(timestampMillis = 0L, value = it) }
                    MarketAreaChart(
                        points = points,
                        xLabelsOverride = labels,
                        lineColor = Color(0xFFE53935),
                        normalize = true,
                        mode = "offset",
                        offsetBaseline = OffsetBaseline.Min
                    )
                }
            }

            item{
                val cryptoTimeRange = CryptoTimeRange.entries
                val labels = cryptoTimeRange.map { it.label }
                var selectedIdx = remember {
                    mutableStateOf(cryptoTimeRange.indexOf(CryptoTimeRange.H1).takeIf { it >= 0 } ?: 0)
                }
                Box(modifier.fillMaxWidth().padding(start = 1.dp, end = 4.dp, top = 2.dp)){
                    AppPillTabs(
                        items = labels,
                        selectedIndex = selectedIdx.value,
                        onSelected = { idx -> selectedIdx.value = idx },
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }

            item {

                PricePercentChangedCard(
                    items = listOf(
                        PricePercentChangedItem(
                            "24 hours",
                            coinDetails?.marketData?.priceChangePercentage24h ?: 0.0
                        ),
                        PricePercentChangedItem("7 days", coinDetails?.marketData?.priceChangePercentage7d?:0.0),
                        PricePercentChangedItem("30 days", coinDetails?.marketData?.priceChangePercentage30d?:0.0),
                        PricePercentChangedItem("60 days", coinDetails?.marketData?.priceChangePercentage60d?:0.0),
                        PricePercentChangedItem("1 year", coinDetails?.marketData?.priceChangePercentage1y?:0.0)
                    )
                )

            }

            item {

                MarketStatsCard(
                    leftStats = listOf(
                        "High 24H" to coinDetails?.marketData?.high24h?.get(CurrencyStyle.usd().name)?.prettyUSD().toString(),
                        "Market Cap" to coinDetails?.marketData?.marketCap?.get(CurrencyStyle.usd().name)?.prettyUSD().toString(),
                        "Total Supply" to coinDetails?.marketData?.totalSupply?.prettyUSD().toString(),
                        "All-Time High" to coinDetails?.marketData?.ath?.get(CurrencyStyle.usd().name)?.prettyUSD().toString(),
                        "Fully Diluted Valuation" to coinDetails?.marketData?.fullyDilutedValuation?.get(CurrencyStyle.usd().name)?.prettyUSD().toString(),
                        "market Cap Rank" to coinDetails?.marketData?.marketCapRank.toString(),
                     ),
                    rightStats = listOf(
                        "Low 24H" to coinDetails?.marketData?.low24h?.get(CurrencyStyle.usd().name)?.prettyUSD().toString(),
                        "Volume" to coinDetails?.marketData?.totalVolume?.get(CurrencyStyle.usd().name)?.prettyUSD().toString(),
                        "Max Supply" to coinDetails?.marketData?.maxSupply?.prettyUSD().toString(),
                        "All-Time Low" to coinDetails?.marketData?.atl?.get(CurrencyStyle.usd().name)?.prettyUSD().toString(),
                        "Circulating Supply" to coinDetails?.marketData?.circulatingSupply?.prettyUSD().toString(),
                    )
                )
            }

            item{
                Spacer(modifier.height(60.dp))
            }
        }
    }

}


@Composable
fun PricePercentChangedCard(
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
fun MarketStatsCard(
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
            MarketStatItem(label = label, value = value)
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


@ThemePreviews
@Composable
private fun OverviewCoinScreenPreview(){
    val coinDetailsFakeData = CoinDetailsFakes.bitcoin()

    AppThemedPreview {
        OverviewCoinScreen(coinDetails = coinDetailsFakeData)
    }
}