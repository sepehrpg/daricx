package com.daricx.markets.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.daricx.markets.ui.MarketsViewModel
import com.daricx.ui.PercentChangeBadge
import com.daricx.ui.visualizations.CoinSparklineChart
import com.example.common.format.NumberFormatters
import com.example.designsystem.component.AppHorizontalDivider
import com.example.designsystem.component.AppText
import com.example.model.CoinMarket
import com.example.model.MarketSortColumn
import com.example.model.SortDirection
import com.example.model.SortSpec
import timber.log.Timber

@Composable
fun CoinsRoute(viewModel: MarketsViewModel = hiltViewModel()) {
    val ui by viewModel.uiState.collectAsState()
    val items = viewModel.pagedCoins.collectAsLazyPagingItems()

    CoinsScreen(
        sort = ui.sort,
        items = items,
        onHeaderClick = viewModel::onHeaderClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsScreen(
    sort: SortSpec,
    items: LazyPagingItems<CoinMarket>,
    onHeaderClick: (MarketSortColumn) -> Unit,
    modifier: Modifier = Modifier
) {
    val refreshState = rememberPullToRefreshState()
    val isRefreshing = items.loadState.refresh is LoadState.Loading

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { items.refresh() },
        state = refreshState,
        modifier = modifier.fillMaxSize(),
        indicator = {
            Indicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 20.dp),
                isRefreshing = isRefreshing,
                containerColor = MaterialTheme.colorScheme.background,
                color = MaterialTheme.colorScheme.primary,
                state = refreshState
            )
        },
    ) {
        Column(Modifier.fillMaxSize()) {
            Spacer(Modifier.height(8.dp))
            MarketsHeaderRow(
                sort = sort,
                onHeaderClick = onHeaderClick
            )

            when (val s = items.loadState.refresh) {
                is LoadState.Loading -> LoadingBox()
                is LoadState.Error -> ErrorBox(
                    message = s.error.message ?: "Error"
                ) { items.retry() }

                is LoadState.NotLoading -> {
                    if (items.itemCount == 0) {
                        EmptyBox()
                    } else {
                        MarketsList(
                            items = items,
                            modifier = Modifier.fillMaxSize()
                           // modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MarketsHeaderRow(
    sort: SortSpec,
    onHeaderClick: (MarketSortColumn) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        //elevation = CardDefaults.cardElevation(1.dp),
        shape = RectangleShape
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp, vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ID Sort (server-side)
            SortableHeader(
                title = "#",
                active = sort.column == MarketSortColumn.RANK,
                direction = sort.direction.takeIf { sort.column == MarketSortColumn.RANK },
                onClick = { onHeaderClick(MarketSortColumn.RANK) },
                modifier = Modifier.width(28.dp)
            )

            // Market Cap (server-side)
            SortableHeader(
                title = "Market Cap",
                active = sort.column == MarketSortColumn.MARKET_CAP,
                direction = sort.direction.takeIf { sort.column == MarketSortColumn.MARKET_CAP },
                onClick = { onHeaderClick(MarketSortColumn.MARKET_CAP) },
                modifier = Modifier.weight(1f)
            )

            // Price (client-side, per-page)
            SortableHeader(
                title = "Price",
                active = sort.column == MarketSortColumn.PRICE,
                direction = sort.direction.takeIf { sort.column == MarketSortColumn.PRICE },
                onClick = { onHeaderClick(MarketSortColumn.PRICE) },
                modifier = Modifier.widthIn(min = 96.dp)
            )

            Spacer(Modifier.width(8.dp))

            // 24h % (client-side, per-page)
            SortableHeader(
                title = "24h %",
                active = sort.column == MarketSortColumn.CHANGE_24H,
                direction = sort.direction.takeIf { sort.column == MarketSortColumn.CHANGE_24H },
                onClick = { /*onHeaderClick(MarketSortColumn.CHANGE_24H)*/ },
                modifier = Modifier.width(84.dp)
            )
        }
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
    TextButton(
        onClick = onClick,
        modifier = modifier.height(32.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        AppText(title, style = MaterialTheme.typography.labelMedium,color = MaterialTheme.colorScheme.onSurfaceVariant)
        if (active && direction != null) {
            Icon(
                imageVector = if (direction == SortDirection.ASC) Icons.Rounded.KeyboardArrowUp
                else Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
                tint =  MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun MarketsList(
    items: LazyPagingItems<CoinMarket>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 0.dp)
    ) {
        items(
            count = items.itemCount,
            //key = { index -> items[index]?.id ?: index } // CAUSING MULTIPLE REQUESTS (3 request)
            //key = { index -> items.peek(index)?.id ?: "idx_$index" },
            key = { index ->
                val id = items.peek(index)?.id
                if (id != null) "$id-$index" else "idx_$index"
            },
            contentType = { _ -> "market_row" }
        ) { index ->
            items[index]?.let { coin ->
                MarketRow(coin = coin)
                AppHorizontalDivider(thickness = 0.5.dp)
            }
        }

        item {
            when (val append = items.loadState.append) {
                is LoadState.Loading -> LoadingMore()
                is LoadState.Error -> ErrorFooter(append.error) { items.retry() }
                else -> {}
            }
        }

        item { Spacer(Modifier.height(56.dp)) }
    }
}

@Composable private fun LoadingBox() = Box(Modifier.fillMaxSize()) {
    LinearProgressIndicator(
        Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.primaryContainer
    )
}

@Composable private fun EmptyBox() = Box(Modifier.fillMaxSize()) {
    AppText(
        "No items",
        modifier = Modifier.align(Alignment.Center),
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable private fun ErrorBox(message: String, onRetry: () -> Unit) = Box(Modifier.fillMaxSize()) {
    Timber.tag("ERROBOX").i(message)
    Column(
        modifier = Modifier.align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppText(text = message)
        Button(onClick = onRetry, modifier = Modifier.padding(top = 8.dp)) { Text("Retry") }
    }
}

@Composable private fun LoadingMore() = Box(
    Modifier
        .fillMaxSize()
        .padding(16.dp)
) { CircularProgressIndicator(Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary) }

@Composable private fun ErrorFooter(error: Throwable, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = error.message ?: "Append error")
        Button(onClick = onRetry, modifier = Modifier.padding(top = 8.dp)) { Text("Retry") }
    }
}

@Composable
fun MarketRow(
    coin: CoinMarket,
    modifier: Modifier = Modifier
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            coin.marketCapRank?.toString() ?: "—",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.width(28.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = coin.image,
                contentDescription = coin.name ?: coin.symbol,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(28.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    coin.name.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = NumberFormatters.compactNumber(coin.marketCap),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Text(
            text = coin.currentPrice?.let { NumberFormatters.pricePretty(it) } ?: "—",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.widthIn(min = 96.dp),
            maxLines = 1
        )

        Spacer(Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.widthIn(min = 84.dp)) {
            CoinSparklineChart(
                values = coin.sparklineIn7d?.price?.map { it.toFloat() } ?: emptyList(),
                modifier = Modifier
                    .width(70.dp)
                    .height(40.dp),
                lineColor = if ((coin.priceChangePercentage24h ?: 0.0) >= 0.0) MaterialTheme.colorScheme.secondary  else  MaterialTheme.colorScheme.tertiary,
            )
            PercentChangeBadge(percent = coin.priceChangePercentage24h ?: 0.0,positiveColor=MaterialTheme.colorScheme.secondary,negativeColor=MaterialTheme.colorScheme.tertiary)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MarketRowPreview() {
    MarketRow(
        coin = CoinMarket(
            ath = null, athChangePercentage = null, athDate = null,
            atl = null, atlChangePercentage = null, atlDate = null,
            circulatingSupply = null, currentPrice = 61234.56,
            fullyDilutedValuation = null, high24h = null, id = "btc",
            image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
            lastUpdated = null, low24h = null, marketCap = 1234000000000.0,
            marketCapChange24h = null, marketCapChangePercentage24h = null,
            marketCapRank = 1, maxSupply = null, name = "Bitcoin",
            priceChange24h = null, priceChangePercentage24h = 2.34, roi = null,
            symbol = "BTC", totalSupply = null, totalVolume = null, sparklineIn7d = null
        )
    )
}
