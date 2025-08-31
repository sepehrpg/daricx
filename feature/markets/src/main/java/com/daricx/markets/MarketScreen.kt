package com.daricx.markets

import MarketStatsRow
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.daricx.ui.visualizations.ElectricCarSalesChart
import com.daricx.ui.visualizations.ElectricCarSalesData
import com.daricx.ui.visualizations.mockCoins
import com.example.designsystem.component.AppTabPager
import com.example.designsystem.component.AppTabPagerItems
import com.example.designsystem.component.AppTopBar
import com.example.designsystem.component.CollapsingHeaderLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.daricx.ui.PercentChangeBadge
import com.daricx.ui.visualizations.Sparkline
import com.example.model.CoinMarket
import com.example.model.MarketSortColumn
import com.example.model.SortDirection
import com.example.model.SortSpec
import kotlin.math.ln
import kotlin.math.pow
import kotlin.random.Random


@Composable
fun MarketsRoute(viewModel: MarketsViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    MarketScreen(state = state, onHeaderClick = viewModel::onHeaderClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(
    state: MarketsUiState,
    onHeaderClick: (MarketSortColumn) -> Unit,
    modifier: Modifier = Modifier,
) {

    Box(Modifier
        .background(Color.White)
        .statusBarsPadding()){
        CollapsingHeaderLayout(
            headerMaxHeight = 150.dp,
            headerMinHeight = 0.dp,
            header = { modifier ->
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.White)
                        .padding(horizontal = 0.dp)
                ) {
                    Column {
                        AppTopBar(title = "Markets")
                        Box(Modifier.padding(horizontal = 12.dp)){
                            MarketStatsRow()
                        }
                    }
                }
            },
            body = {
                TabsSection(state=state,onHeaderClick=onHeaderClick,modifier=modifier)
            }
        )
    }
}




@Composable
fun TabsSection(
    state: MarketsUiState,
    onHeaderClick: (MarketSortColumn) -> Unit,
    modifier: Modifier = Modifier,
) {
   Box(Modifier.padding(top = 0.dp)){
       val tabs = listOf(
           AppTabPagerItems(
               title = "Coins",
               contentScreens = {
                   CoinScreen(state= state,onHeaderClick= onHeaderClick,modifier=modifier)
               },
           ),
           AppTabPagerItems(
               title = "Watchlists",
               contentScreens = {
                   Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                   }
               },
           ),
           AppTabPagerItems(
               title = "Exchanges",
               contentScreens = {
                   Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                   }
               },
           ),
           AppTabPagerItems(
               title = "Chains",
               contentScreens = {
                   Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                   }
               },
           ),
           AppTabPagerItems(
               title = "Categories",
               contentScreens = {
                   Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                   }
               },
           ),
           AppTabPagerItems(
               title = "NFT",
               contentScreens = {
                   Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                   }
               },
           ),
       )

       AppTabPager(
           tabs = tabs,
           indicatorWidthMatchWithTextSize = true,
           indicatorColor = Color(0xFF378A3B),
           indicatorShape = RoundedCornerShape(10.dp),
           thicknessIndicator = 2.dp,
           dividerThickness = 2.dp,
           dividerColor = Color(0xFFEEEEEE),
           tabPadding = 12.dp
       )
   }
}



@Composable
fun CoinScreen(
    state: MarketsUiState,
    onHeaderClick: (MarketSortColumn) -> Unit,
    modifier: Modifier = Modifier,
){
    Column(modifier.fillMaxSize()) {
        MarketsHeaderRow(
            sort = (state as? MarketsUiState.Success)?.sort,
            onHeaderClick = onHeaderClick
        )
        when (state) {
            is MarketsUiState.Loading -> LinearProgressIndicator(Modifier.fillMaxWidth())
            is MarketsUiState.Error -> Text(state.message, color = MaterialTheme.colorScheme.error)
            is MarketsUiState.Success ->
             MarketsList(
                items = state.items,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun CoinListItem(coin: ElectricCarSalesData) {
    val priceChangeColor = if (coin.priceChangePercent >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Rank and Name
        Row(modifier = Modifier.weight(1.5f), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = coin.rank.toString(),
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.width(35.dp)
            )
            // Placeholder for coin icon
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEEEEEE)), // THEME CHANGE
                contentAlignment = Alignment.Center
            ) {
                Text(coin.symbol.first().toString(), color = Color.Black, fontWeight = FontWeight.Bold) // THEME CHANGE
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(coin.name, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp) // THEME CHANGE
                Text(coin.marketCap, color = Color.Gray, fontSize = 12.sp)
            }
        }

        // Price and Sparkline
        Row(modifier = Modifier.weight(2f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(1f)) {
                Text(coin.price, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold) // THEME CHANGE
            }
            Spacer(modifier = Modifier.width(12.dp))
           Box(Modifier
               .width(50.dp)
               .height(50.dp)){
               ElectricCarSalesChart()
           }
            /*SparklineChart(
                data = coin.chartData,
                color = priceChangeColor,
                modifier = Modifier.height(35.dp).weight(1f)
            )*/
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "%.2f%%".format(coin.priceChangePercent),
                color = priceChangeColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// Reusable components that are not theme-specific
@Composable
fun CoinListHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("#", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.width(30.dp), textAlign = TextAlign.Center)
        Text("Market Cap", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.weight(1f))
        Text("Price ▲", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
        Text("24h %", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
    }
}

@Composable
fun GaugeIndicator(
    value: Int,
    maxValue: Int,
    modifier: Modifier = Modifier.size(40.dp)
) {
    val sweepAngle = (value.toFloat() / maxValue) * 270f
    val startAngle = 135f
    val color = when {
        value < 25 -> Color.Red
        value < 50 -> Color(0xFFFFA500) // Orange
        value < 75 -> Color.Yellow
        else -> Color.Green
    }

    Canvas(modifier = modifier) {
        drawArc(
            color = Color.LightGray.copy(alpha = 0.6f), // THEME CHANGE
            startAngle = startAngle,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(width = 8f, cap = StrokeCap.Round)
        )
        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = 8f, cap = StrokeCap.Round)
        )
    }
}

@Composable
fun SparklineChart(
    data: List<Float>,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        if (data.size < 2) return@Canvas
        val path = Path()
        val min = data.minOrNull() ?: 0f
        val max = data.maxOrNull() ?: 0f
        val range = max - min
        val xStep = size.width / (data.size - 1)
        data.forEachIndexed { index, value ->
            val x = index * xStep
            val y = size.height * (1 - ((value - min) / range.coerceAtLeast(1f)))
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        drawPath(path = path, color = color, style = Stroke(width = 3f))
    }
}









@Composable
private fun MarketsHeaderRow(
    sort: SortSpec?,
    onHeaderClick: (MarketSortColumn) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("#", style = MaterialTheme.typography.labelMedium, modifier = Modifier.width(28.dp))
        Text("Market Cap", style = MaterialTheme.typography.labelMedium, modifier = Modifier.weight(1f))
        SortableHeader(
            title = "Price",
            active = sort?.column == MarketSortColumn.PRICE,
            direction = sort?.direction,
            onClick = { onHeaderClick(MarketSortColumn.PRICE) },
            modifier = Modifier.widthIn(min = 96.dp)
        )
        Spacer(Modifier.width(8.dp))
        SortableHeader(
            title = "24h %",
            active = sort?.column == MarketSortColumn.CHANGE_24H,
            direction = sort?.direction,
            onClick = { onHeaderClick(MarketSortColumn.CHANGE_24H) },
            modifier = Modifier.width(84.dp)
        )
    }
}

@Composable
private fun SortableHeader(
    title: String,
    active: Boolean,
    direction: SortDirection?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(onClick = onClick, modifier = modifier.height(32.dp), contentPadding = PaddingValues(0.dp)) {
        Text(title, style = MaterialTheme.typography.labelMedium)
        if (active && direction != null) {
            Icon(
                imageVector = if (direction == SortDirection.ASC) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                contentDescription = null
            )
        }
    }
}

/**
 * Reusable list backed by CoinMarket.
 * [sparklineValuesProvider] lets you inject sparkline points per item (null -> no chart).
 */
@Composable
fun MarketsList(
    items: List<CoinMarket>,
    modifier: Modifier = Modifier,
    priceFormatter: (Double) -> String = { "$" + pricePretty(it) },
    sparklineValuesProvider: (CoinMarket) -> List<Float>? = { null }
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(vertical = 4.dp)) {
        items(items, /*key = { it.id ?: it.name ?: it.symbol ?: hashCode().toString() }*/) { coin ->
            MarketRow(
                coin = coin,
                priceFormatter = priceFormatter,
                sparklineValues = sparklineValuesProvider(coin)
            )
            HorizontalDivider(thickness = 0.5.dp)
        }
        item { Spacer(Modifier.height(56.dp)) }
    }
}

@Composable
fun MarketRow(
    coin: CoinMarket,
    priceFormatter: (Double) -> String,
    sparklineValues: List<Float>?,
    modifier: Modifier = Modifier
) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 2.dp).then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(coin.marketCapRank?.toString() ?: "—", style = MaterialTheme.typography.labelLarge, modifier = Modifier.width(28.dp))

        Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = coin.image,
                contentDescription = coin.name ?: coin.symbol,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(28.dp).clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(1f)) {
                Text(coin.name.orEmpty(), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(
                    "$" + compactNumber(coin.marketCap),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(
            coin.currentPrice?.let { priceFormatter(it) } ?: "—",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.widthIn(min = 96.dp), maxLines = 1
        )

        Spacer(Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.widthIn(min = 84.dp)) {
            Box(Modifier
                .width(50.dp)
                .height(50.dp)){
                ElectricCarSalesChart()
            }
            PercentChangeBadge(percent = coin.priceChangePercentage24h ?: 0.0)
        }
    }
}

/** Pretty price with variable precision (like many crypto UIs) */
private fun pricePretty(price: Double): String {
    return when {
        price >= 1 -> "%,.2f".format(price)
        price >= 0.1 -> "%,.3f".format(price)
        price >= 0.01 -> "%,.4f".format(price)
        price >= 0.001 -> "%,.5f".format(price)
        else -> {
            // show significant digits for tiny prices
            val digits = (2 - ln(price).div(ln(10.0))).toInt().coerceIn(6, 10)
            "%.${digits}f".format(price)
        }
    }
}


/** 4.72B style with nullable input */
private fun compactNumber(n: Long?): String {
    val v = n ?: return "—"
    val abs = kotlin.math.abs(v.toDouble())
    return when {
        abs >= 1_000_000_000 -> "%,.2fB".format(v / 1_000_000_000.0)
        abs >= 1_000_000 -> "%,.2fM".format(v / 1_000_000.0)
        abs >= 1_000 -> "%,.2fK".format(v / 1_000.0)
        else -> "%,d".format(v)
    }
}






// --- Preview ---
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun DefaultPreviewLight() {
    MarketsRoute()
}




