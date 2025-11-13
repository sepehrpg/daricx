package com.daricx.markets.ui.screen.exchanges.exchange.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.daricx.markets.data.ExchangeDetailsFakes
import com.daricx.markets.ui.mapper.ExchangeLinkItemsMapper
import com.daricx.markets.ui.model.LinkItem
import com.daricx.ui.visualizations.advance.MarketAreaChart
import com.daricx.ui.visualizations.advance.OffsetBaseline
import com.daricx.ui.visualizations.advance.PricePoint
import com.daricx.ui.visualizations.advance.generateBtcLike2hSeries
import com.example.common.currency.CurrencyStyle
import com.example.common.numbers.facade.prettyPrice
import com.example.designsystem.component.AppHorizontalDivider
import com.example.designsystem.component.chips.AppElevatedAssistChip
import com.example.designsystem.component.tabs.AppPillTabs
import com.example.designsystem.component.text.AppText
import com.example.designsystem.component.text.AppTextReadMoreHtmlFormat
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import com.example.model.exchanges.ExchangeDetail
import com.example.model.option.CryptoTimeRange
import kotlin.collections.forEach


@Composable
fun OverviewExchangeRoute() {
    val exchangeDetailFakeData = ExchangeDetailsFakes.binanceFromReal()
    OverviewExchangeScreen(exchangeDetail = exchangeDetailFakeData)
}


@Composable
fun OverviewExchangeScreen(modifier: Modifier = Modifier, exchangeDetail: ExchangeDetail) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
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



@ThemePreviews
@Composable
fun OverviewExchangeScreenPreview() {
    val exchangeDetailFakeData = ExchangeDetailsFakes.binanceFromReal()
    AppThemedPreview {
        OverviewExchangeScreen(exchangeDetail = exchangeDetailFakeData)
    }
}

