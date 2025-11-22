package com.daricx.markets.ui.screen.trending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.daricx.markets.data.TrendingFakeData
import com.daricx.ui.PercentChangeView
import com.example.common.numbers.adapter.NumberFormatterAdapter
import com.example.designsystem.component.AppPullToRefresh
import com.example.designsystem.component.cards.AppCard
import com.example.designsystem.component.text.AppText
import com.example.designsystem.component.icons.AppIcon
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import com.example.model.Trending


@Composable
fun TrendingRoute(
    viewModel: TrendingViewModel = hiltViewModel(),
    onNavigateToCoinDetailsScreen: (coinId: String) -> Unit,
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    //LaunchedEffect(Unit) { viewModel.getTrending() }
    TrendingScreen(trending = uiState.trending, onNavigateToCoinDetailsScreen = onNavigateToCoinDetailsScreen)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingScreen(
    modifier: Modifier = Modifier,
    trending: Trending? = null,
    onNavigateToCoinDetailsScreen: (coinId: String) -> Unit,
){
    val refreshState = rememberPullToRefreshState()
    // Show pull-to-refresh spinner only when we are refreshing over existing content.
    var isRefreshing by remember { mutableStateOf(false) }

    AppPullToRefresh(
        isRefreshing = isRefreshing,
        onRefresh = {  },
        state = refreshState,
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn {

            item {
                TrendingCoins(
                    listTrendingCoins =  trending?.coins,
                    onNavigateToCoinDetailsScreen = onNavigateToCoinDetailsScreen
                )
            }

            item {
                TrendingNFT(listNftsTrending = trending?.nfts)
            }

            trendingCategoriesSection(trending?.categories)

            item {
                Spacer(modifier.height(50.dp))
            }
        }
    }
}


@Composable
private fun TrendingCoins(
    modifier: Modifier = Modifier,
    listTrendingCoins : List<Trending.Coin>?,
    onNavigateToCoinDetailsScreen: (coinId: String) -> Unit,
){
    if (!listTrendingCoins.isNullOrEmpty()){
        Column(modifier.fillMaxWidth().padding(top = 15.dp, start = 5.dp,end=5.dp)){
            Box(modifier.padding(horizontal = 5.dp)){
                AppText("Trending Coins", style = MaterialTheme.typography.titleMedium)
            }
            LazyRow(modifier.fillMaxWidth().padding(top = 5.dp)) {

                items(listTrendingCoins){ item ->
                    AppCard (
                        modifier.padding(horizontal = 5.dp, vertical = 5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        onClick = { onNavigateToCoinDetailsScreen(item.item?.id.toString()) },
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),
                    ){
                        Column(
                            modifier =  modifier.width( 125.dp).height(160.dp).padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            AsyncImage(
                                model = item.item?.large,
                                placeholder = painterResource(com.daricx.ui.R.drawable.daricx_logo_place_holder),
                                error = painterResource(com.daricx.ui.R.drawable.daricx_logo_place_holder),
                                contentDescription = item.item?.name?: "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(MaterialTheme.shapes.small)
                            )
                            AppText(
                                text = item.item?.name?:"",
                                modifier = modifier.padding(top = 7.dp),
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1
                            )
                            AppText(
                                text = item.item?.data?.price?.let { NumberFormatterAdapter.pricePretty(it) } ?: "—",
                                modifier = modifier.padding(top = 8.dp),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                                maxLines = 1
                            )
                            val pctUsd = item.item
                                ?.data
                                ?.priceChangePercentage24h
                                ?.get("usd")
                            pctUsd?.let { value ->
                                PercentChangeView(
                                    percent = value,
                                    showBackground = false,
                                    modifier = Modifier.padding(top = 0.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun TrendingNFT(
    modifier: Modifier = Modifier,
    listNftsTrending: List<Trending.Nft>?
){
    if (!listNftsTrending.isNullOrEmpty()){
        Column(modifier.fillMaxWidth().padding(top = 15.dp, start = 5.dp,end=5.dp)){
            Box(modifier.padding(horizontal = 5.dp)){
                AppText("Trending NFT", style = MaterialTheme.typography.titleMedium)
            }
            LazyRow(modifier.fillMaxWidth().padding(top = 5.dp)) {
                items(listNftsTrending){ item ->
                    Card(
                        modifier.padding(horizontal = 5.dp, vertical = 5.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),){
                        Column(
                            modifier = modifier.width(125.dp).height( 160.dp).padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            AsyncImage(
                                model = item.thumb,
                                placeholder = painterResource(com.daricx.ui.R.drawable.daricx_logo_place_holder),
                                error = painterResource(com.daricx.ui.R.drawable.daricx_logo_place_holder),
                                contentDescription = item.name?: "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(MaterialTheme.shapes.small)
                            )
                            AppText(
                                text = item.name?:"",
                                modifier = modifier.padding(top = 7.dp),
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1
                            )
                            AppText(
                                text = item.floorPriceInNativeCurrency?.let {
                                    NumberFormatterAdapter.pricePretty(it, currencySymbol = " BTC",revert = true)
                                } ?: "—",
                                modifier = modifier.padding(top = 8.dp),
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                                maxLines = 1
                            )
                            val pctUsd = item.floorPrice24hPercentageChange
                            pctUsd?.let { value ->
                                PercentChangeView(
                                    percent = value,
                                    showBackground = false,
                                    modifier = Modifier.padding(top = 0.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



/**
 * Flattened categories section.
 * This extension adds header + list items directly to the parent LazyColumn.
 */
private fun LazyListScope.trendingCategoriesSection(
    listCategoriesTrending: List<Trending.Category>?
) {
    if (!listCategoriesTrending.isNullOrEmpty()){
        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 10.dp, end = 10.dp)
            ) {
                AppText("Trending Categories", style = MaterialTheme.typography.titleMedium)
            }
        }

        items(listCategoriesTrending){
            TrendingCategory(item = it)
        }
    }
}


@Composable
private fun TrendingCategory(
    modifier: Modifier = Modifier,
    item : Trending.Category
){
    Card(
        modifier.padding(horizontal = 10.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),){
        Row (
            modifier = modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 10.dp),
        ){
            AppIcon(
                AppIcons.List,
                contentDescription = ""
            )
            Spacer(modifier.width(10.dp))
            Column {
                AppText(item.name?:"", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier.height(10.dp))

                val price  = NumberFormatterAdapter.compactNumber(item.data?.marketCap)
                AppText(
                    text = item.data?.marketCap?.let {
                        NumberFormatterAdapter.pricePretty(it)
                    } ?: "—",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ){
                /*AsyncImage(
                    model = item.data?.sparkline,
                    placeholder = painterResource(com.daricx.ui.R.drawable.core_ui_sparkline),
                    error = painterResource(com.daricx.ui.R.drawable.core_ui_sparkline),
                    contentDescription = item.name?: "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.width(70.dp)
                        .clip(MaterialTheme.shapes.small)
                )*/

                val pctUsd = item.marketCap1hChange
                pctUsd?.let { value ->
                    PercentChangeView(
                        percent = value,
                        showBackground = false,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
@ThemePreviews
fun TrendingScreenPreview(){
    AppThemedPreview {
        TrendingScreen(
            trending = TrendingFakeData.generateFakeTrending(),
            onNavigateToCoinDetailsScreen = {}
        )
    }
}