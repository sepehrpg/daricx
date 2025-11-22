package com.daricx.markets.ui

import MarketStatsRow
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daricx.markets.ui.screen.categories.CategoriesRoute
import com.example.designsystem.component.AppTabPager
import com.example.designsystem.component.AppTabPagerItems
import com.example.designsystem.component.AppTopBar
import com.example.designsystem.component.CollapsingHeaderLayout
import com.daricx.markets.ui.screen.coins.CoinsRoute
import com.daricx.markets.ui.screen.exchanges.ExchangesRoute
import com.daricx.markets.ui.screen.trending.TrendingRoute
import com.daricx.markets.ui.screen.watchlist.WatchlistRoute


@Composable
fun MarketsRoute(
    onOpenDrawerMenu: () -> Unit,
    onNavigateToCoinDetailsScreen: (coinId: String) -> Unit,
    onNavigateToExchangeDetailsScreen: (exchangeId: String) -> Unit,
) {
    MarketScreen(
        onOpenDrawerMenu=onOpenDrawerMenu,
        onNavigateToCoinDetailsScreen = onNavigateToCoinDetailsScreen,
        onNavigateToExchangeDetailsScreen = onNavigateToExchangeDetailsScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(
    modifier: Modifier = Modifier,
    onOpenDrawerMenu: () -> Unit,
    onNavigateToCoinDetailsScreen: (coinId: String) -> Unit,
    onNavigateToExchangeDetailsScreen: (exchangeId: String) -> Unit,
) {
    Box(Modifier.statusBarsPadding()){
        CollapsingHeaderLayout(
            headerMaxHeight = 150.dp,
            headerMinHeight = 0.dp,
            header = { modifier ->
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 0.dp)
                ) {
                    Column {
                        AppTopBar(title = "Markets", onMenuClick = onOpenDrawerMenu)
                        Box(Modifier.padding(horizontal = 12.dp)){
                            MarketStatsRow()
                        }
                    }
                }
            },
            body = {
                TabsSection(
                    onNavigateToCoinDetailsScreen = onNavigateToCoinDetailsScreen,
                    onNavigateToExchangeDetailsScreen = onNavigateToExchangeDetailsScreen,
                )
            }
        )
    }
}


@Composable
fun TabsSection(
    onNavigateToCoinDetailsScreen: (coinId: String) -> Unit,
    onNavigateToExchangeDetailsScreen: (exchangeId: String) -> Unit,
    ) {
    Box(
        Modifier
            .padding(top = 0.dp)
            .fillMaxHeight()
    ) {
       val tabs = listOf(
           AppTabPagerItems(
               title = "Coins",
               contentScreens = {
                   CoinsRoute(onNavigateToCoinDetailsScreen = onNavigateToCoinDetailsScreen)
               },
           ),
           AppTabPagerItems(
               title = "Trending",
               contentScreens = {
                   TrendingRoute(onNavigateToCoinDetailsScreen = onNavigateToCoinDetailsScreen)
               },
           ),

           AppTabPagerItems(
               title = "Watchlists",
               contentScreens = {
                   WatchlistRoute(onNavigateToCoinDetailsScreen = onNavigateToCoinDetailsScreen)
               },
           ),

           AppTabPagerItems(
               title = "Exchanges",
               contentScreens = {
                   ExchangesRoute(
                       onNavigateToExchangeDetailsScreen= onNavigateToExchangeDetailsScreen
                   )
               },
           ),

           AppTabPagerItems(
               title = "Categories",
               contentScreens = {
                   CategoriesRoute()
               },
           ),
           /*AppTabPagerItems(
               title = "NFT",
               contentScreens = {
                   NftsRoute()

               },
           ),*/

       )

       AppTabPager(
           tabs = tabs,
           tabContainerColor = MaterialTheme.colorScheme.surface,
           tabContentColor = MaterialTheme.colorScheme.onSurface,
           dividerColor = MaterialTheme.colorScheme.outlineVariant,
           indicatorColor = MaterialTheme.colorScheme.primary,
           tabTextColorSelected = MaterialTheme.colorScheme.onBackground,
           tabTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
           indicatorWidthMatchWithTextSize = true,
           indicatorShape = RoundedCornerShape(10.dp),
           thicknessIndicator = 2.dp,
           dividerThickness = 2.dp,
           tabPadding = 12.dp,
           modifier = Modifier.fillMaxSize()
       )
   }
}




// --- Preview ---
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun DefaultPreviewLight() {
    MarketsRoute(
        onOpenDrawerMenu={},
        onNavigateToCoinDetailsScreen={},
        onNavigateToExchangeDetailsScreen = {},
    )
}




