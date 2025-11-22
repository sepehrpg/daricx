package com.daricx.markets.ui.screen.watchlist

import com.daricx.markets.ui.screen.coins.rememberCoinsPreviewItems

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.*

import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.daricx.markets.data.CoinsFakeData
import com.daricx.markets.data.CoinsFakeData.coinsFakeModel
import com.daricx.markets.ui.screen.EmptyBox
import com.daricx.markets.ui.screen.ErrorBox
import com.daricx.markets.ui.screen.ErrorFooter
import com.daricx.markets.ui.screen.LoadingBox
import com.daricx.markets.ui.screen.LoadingMore
import com.daricx.ui.PercentChangeBadge
import com.daricx.ui.visualizations.CoinSparklineChart
import com.example.common.numbers.adapter.NumberFormatterAdapter
import com.example.designsystem.component.AppPullToRefresh
import com.example.designsystem.component.SwipeAction
import com.example.designsystem.component.SwipeableActionsRow
import com.example.designsystem.component.text.AppText
import com.example.designsystem.component.cards.AppCard
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import com.example.model.coins.Coins
import com.example.model.sort.SortKey
import com.example.model.sort.SortOrder
import com.example.model.sort.SortOption



// -------------------------------- Column Weights --------------------------------
// Keep header and row in sync:
private const val CL_1 = 1f
private const val CL_2 = 4f
private const val CL_3 = 4f
private const val CL_4 = 3f
// -------------------------------- Header --------------------------------

@Composable
fun WatchlistRoute(
    viewModel: WatchlistViewModel = hiltViewModel(),
    onNavigateToCoinDetailsScreen: (coinId: String) -> Unit,
) {
    val ui by viewModel.uiState.collectAsStateWithLifecycle()
    val items = viewModel.pagedCoinsFav.collectAsLazyPagingItems()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()

    WatchlistScreen(
        sort = ui.sort,
        items = items,
        favoriteIds = favoriteIds,
        onHeaderClick = viewModel::onHeaderClick,
        onNavigateToCoinDetailsScreen  = onNavigateToCoinDetailsScreen,
        onFavoriteClick  = viewModel::onFavoriteClick,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen(
    sort: SortOption,
    items: LazyPagingItems<Coins>,
    favoriteIds: Set<String>,
    onHeaderClick: (SortKey) -> Unit,
    modifier: Modifier = Modifier,
    onFavoriteClick: (coin: Coins) -> Unit,
    onNavigateToCoinDetailsScreen: (coinId: String) -> Unit,
) {
    val refreshState = rememberPullToRefreshState()
    val hasItems = items.itemCount > 0
    val isRefreshing = items.loadState.refresh is LoadState.Loading && hasItems

    AppPullToRefresh(
        isRefreshing = isRefreshing,
        onRefresh = { items.refresh() },
        state = refreshState,
        modifier = modifier.fillMaxSize(),
    ) {
        Column(Modifier.fillMaxSize()) {
            Spacer(Modifier.height(8.dp))
            WatchlistHeaderRow(sort = sort, onHeaderClick = onHeaderClick)

            when (val s = items.loadState.refresh) {
                is LoadState.Loading -> {
                    if (hasItems) {
                        MarketsList(
                            items = items,
                            favoriteIds = favoriteIds,
                            modifier = Modifier.fillMaxSize(),
                            onNavigateToCoinDetailsScreen = onNavigateToCoinDetailsScreen,
                            onFavoriteClick = onFavoriteClick,
                        )
                    } else {
                        LoadingBox()
                    }
                }
                is LoadState.Error -> {
                    ErrorBox(message = s.error.message ?: "Error") { items.retry() }
                }
                is LoadState.NotLoading -> {
                    if (hasItems) {
                        MarketsList(
                            items = items,
                            modifier = Modifier.fillMaxSize(),
                            onNavigateToCoinDetailsScreen = onNavigateToCoinDetailsScreen,
                            onFavoriteClick= onFavoriteClick,
                            favoriteIds = favoriteIds
                        )
                    } else {
                        EmptyBox()
                    }
                }
            }
        }
    }
}

@Composable
fun WatchlistHeaderRow(
    sort: SortOption,
    onHeaderClick: (SortKey) -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = RectangleShape
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp, vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SortableHeader(
                title = "#",
                active = sort.sortKey == SortKey.RANK,
                direction = sort.sortOrder.takeIf { sort.sortKey == SortKey.RANK },
                onClick = { onHeaderClick(SortKey.RANK) },
                modifier = Modifier.weight(CL_1)
            )
            SortableHeader(
                title = "Coin",
                active = sort.sortKey == SortKey.MARKET_CAP,
                direction = sort.sortOrder.takeIf { sort.sortKey == SortKey.MARKET_CAP },
                onClick = { onHeaderClick(SortKey.MARKET_CAP) },
                modifier = Modifier.weight(CL_2)
            )
            SortableHeader(
                title = "Price",
                active = sort.sortKey == SortKey.PRICE,
                direction = sort.sortOrder.takeIf { sort.sortKey == SortKey.PRICE },
                onClick = { onHeaderClick(SortKey.PRICE) },
                modifier = Modifier.weight(CL_3)
            )
            SortableHeader(
                title = "24h %",
                active = sort.sortKey == SortKey.CHANGE_24H,
                direction = sort.sortOrder.takeIf { sort.sortKey == SortKey.CHANGE_24H },
                onClick = { onHeaderClick(SortKey.CHANGE_24H) },
                modifier = Modifier.weight(CL_4)
            )
        }
    }
}

@Composable
private fun SortableHeader(
    title: String,
    active: Boolean,
    direction: SortOrder?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Center all header cells
    Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        TextButton(
            onClick = onClick,
            modifier = Modifier.height(32.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            AppText(
                title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (active && direction != null) {
                Icon(
                    imageVector = if (direction == SortOrder.ASC)
                        Icons.Rounded.KeyboardArrowUp
                    else Icons.Rounded.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun MarketsList(
    items: LazyPagingItems<Coins>,
    favoriteIds: Set<String>,
    modifier: Modifier = Modifier,
    onFavoriteClick: (coin: Coins) -> Unit,
    onNavigateToCoinDetailsScreen: (coinId: String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 0.dp)
    ) {
        items(
            count = items.itemCount,
            key = { index ->
                val id = items.peek(index)?.id
                if (id != null) "$id-$index" else "idx_$index"
            },
            contentType = { _ -> "market_row" }
        ) { index ->
            items[index]?.let { coin ->
                val isFavorite = favoriteIds.contains(coin.id)
                if (isFavorite){
                    MarketRow(
                        coin = coin,
                        isFavorite = isFavorite,
                        onNavigateToCoinDetailsScreen = onNavigateToCoinDetailsScreen,
                        onFavoriteClick =onFavoriteClick
                    )
                }

                //AppHorizontalDivider(thickness = 0.5.dp)
            }
        }

        item {
            when (val append = items.loadState.append) {
                is LoadState.Loading -> LoadingMore()
                is LoadState.Error -> ErrorFooter(append.error) { items.retry() }
                else -> Unit
            }
        }

        item { Spacer(Modifier.height(56.dp)) }
    }
}



@Composable
private fun MarketRow(
    coin: Coins,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteClick: (coin: Coins) -> Unit,
    onNavigateToCoinDetailsScreen: (coinId: String) -> Unit,
) {


    SwipeableActionsRow(
        endActions = listOf(
            SwipeAction(
                icon = if (isFavorite) AppIcons.StarFill else AppIcons.Star,
                backgroundColor =  MaterialTheme.colorScheme.primary,
                contentColor = if (isFavorite) Color.Yellow  else Color.White,
                iconSize = 27.dp,
                onClick = { onFavoriteClick(coin) }
            )
        ),
        onItemClick = { onNavigateToCoinDetailsScreen(coin.id?:"") }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp, vertical = 6.dp)
                .then(modifier),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // # Rank (center)
            Box(Modifier
                .weight(CL_1)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    coin.marketCapRank?.toString() ?: "—",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Coin (icon + name + market cap) (start)
            Row(
                Modifier
                    .weight(CL_2)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = coin.image,
                    placeholder = painterResource(com.daricx.ui.R.drawable.daricx_logo_place_holder),
                    error = painterResource(com.daricx.ui.R.drawable.daricx_logo_place_holder),
                    contentDescription = coin.name ?: coin.symbol,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(28.dp)
                        .clip(MaterialTheme.shapes.small)
                )
                Spacer(Modifier.width(6.dp))
                Column {
                    AppText(
                        coin.name.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    AppText(
                        text = NumberFormatterAdapter.compactNumber(coin.marketCap),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Price (center)
            Box(Modifier
                .weight(CL_3)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
                AppText(
                    text = coin.currentPrice?.let { NumberFormatterAdapter.pricePretty(it) } ?: "—",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 1
                )
            }

            // 24h% + sparkline (center)
            Box(Modifier
                .weight(CL_4)
                .fillMaxWidth()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CoinSparklineChart(
                        values = coin.sparklineIn7d?.price?.map { it.toFloat() } ?: emptyList(),
                        modifier = Modifier
                            .width(70.dp)
                            .height(25.dp),
                        lineColor = if ((coin.priceChangePercentage24h ?: 0.0) >= 0.0)
                            MaterialTheme.colorScheme.secondary
                        else
                            MaterialTheme.colorScheme.tertiary,
                    )
                    PercentChangeBadge(
                        percent = coin.priceChangePercentage24h ?: 0.0,
                        positiveColor = MaterialTheme.colorScheme.secondary,
                        negativeColor = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}



// -------------------------------- Previews --------------------------------

// Happy-path (NotLoading + content) — Light
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WatchlistScreen_Preview_NotLoading_Light() {
    AppThemedPreview(darkTheme = false) {

        val items = rememberCoinsPreviewItems(CoinsFakeData.coinsFakeData)
        val sort = SortOption(sortKey = SortKey.MARKET_CAP, sortOrder = SortOrder.DESC)
        WatchlistScreen(
            sort = sort,
            items = items,
            onNavigateToCoinDetailsScreen = {},
            onHeaderClick = {},
            onFavoriteClick ={},
            favoriteIds = setOf()
        )
    }
}


@ThemePreviews
@Composable
fun MarketRow_Preview_Light() {
    AppThemedPreview {
        MarketRow(
            onNavigateToCoinDetailsScreen = {},
            onFavoriteClick = {},
            isFavorite = true,
            coin = coinsFakeModel("btc", "Bitcoin", "BTC", 1, 61234.56, 1_234_000_000_000.0, +2.34),
        )
    }
}

