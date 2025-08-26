package com.daricx.markets

import MarketStatsRow
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import com.daricx.ui.visualizations.ElectricCarSalesChart
import com.daricx.ui.visualizations.ElectricCarSalesData
import com.daricx.ui.visualizations.mockCoins
import com.example.designsystem.component.AppTopBar


// --- Main Screen Composable ---
@Composable
fun MarketScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // THEME CHANGE: Background to White
    ) {
        AppTopBar("Markets")
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 5.dp)
        ) {
            item { MarketStatsRow() }
            item { QuickActionsSectionLight() }
            item { TabsSectionLight() }
            item { CoinListHeader() } // Header can be reused
            items(mockCoins) { coin ->
                CoinListItemLight(coin = coin)
                HorizontalDivider(color = Color.Gray.copy(alpha = 0.1f), thickness = 1.dp)
            }
            item {
                Spacer(Modifier.height(100.dp))
            }
        }
    }
}


// --- UI Components (Light Theme Version) ---



@Composable
fun MarketStatsSectionLight() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatCardLight("Market Cap", "$4.02T", "+1.46%", Color(0xFF4CAF50))
        StatCardLight("CMC100", "$249.72", "+1.74%", Color(0xFF4CAF50))
        GaugeStatCardLight("Altcoin Index", 46, 100)
        GaugeStatCardLight("Fear & Greed", 57, 100)
    }
}

@Composable
fun StatCardLight(title: String, value: String, change: String, changeColor: Color) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(title, color = Color.Gray, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp) // THEME CHANGE
        Text(change, color = changeColor, fontSize = 14.sp)
    }
}

@Composable
fun GaugeStatCardLight(title: String, value: Int, maxValue: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, color = Color.Gray, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Box(contentAlignment = Alignment.Center) {
            GaugeIndicator(value = value, maxValue = maxValue) // GaugeIndicator is theme-agnostic
            Text("$value", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp) // THEME CHANGE
        }
    }
}

@Composable
fun QuickActionsSectionLight() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)) // THEME CHANGE
        ) {
            Text("Why is the market up today?", color = Color.Black, fontSize = 12.sp) // THEME CHANGE
        }
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)) // THEME CHANGE
        ) {
            Text("Are altcoins outperforming?", color = Color.Black, fontSize = 12.sp) // THEME CHANGE
        }
    }
}

@Composable
fun TabsSectionLight() {
    val tabs = listOf("Coins", "Watchlists", "DexScan", "Overview")
    var selectedTabIndex by remember { mutableStateOf(0) }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.White, // THEME CHANGE
        contentColor = Color(0xFF00ACC1),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = Color(0xFF00ACC1)
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index },
                text = { Text(title, fontSize = 12.sp,color = if (selectedTabIndex == index) Color(0xFF00ACC1) else Color.Gray) } // THEME CHANGE
            )
        }
    }
}


@Composable
fun CoinListItemLight(coin: ElectricCarSalesData) {
    val priceChangeColor = if (coin.priceChangePercent >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
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
           Box(Modifier.width(50.dp).height(50.dp)){
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


// --- Preview ---
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun DefaultPreviewLight() {
    MarketScreen()
}