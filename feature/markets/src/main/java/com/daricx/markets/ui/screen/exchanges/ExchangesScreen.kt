package com.daricx.markets.ui.screen.exchanges

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.daricx.markets.ui.screen.EmptyBox
import com.daricx.markets.ui.screen.ErrorBox
import com.daricx.markets.ui.screen.ErrorFooter
import com.daricx.markets.ui.screen.LoadingBox
import com.daricx.markets.ui.screen.LoadingMore
import com.daricx.ui.TrustScorePill
import com.example.common.numbers.adapter.NumberFormatterAdapter
import com.example.designsystem.component.AppHorizontalDivider
import com.example.designsystem.component.AppPullToRefresh
import com.example.designsystem.component.text.AppText
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import com.example.model.exchanges.Exchanges

// ----------------------------- Route -----------------------------

@Composable
fun ExchangesRoute(
    viewModel: ExchangesViewModel = hiltViewModel(),
    onNavigateToExchangeDetailsScreen: (exchangeId: String) -> Unit,
) {
    val items = viewModel.pagedExchanges.collectAsLazyPagingItems()
    ExchangesScreen(
        items = items,
        onNavigateToExchangeDetailsScreen = onNavigateToExchangeDetailsScreen
    )
}

// ----------------------------- Screen -----------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangesScreen(
    items: LazyPagingItems<Exchanges>,
    modifier: Modifier = Modifier,
    onNavigateToExchangeDetailsScreen: (exchangeId: String) -> Unit,
) {
    val refreshState = rememberPullToRefreshState()
    val hasItems = items.itemCount > 0
    // Show pull-to-refresh spinner only when we are refreshing over existing content.
    val isRefreshing = items.loadState.refresh is LoadState.Loading && hasItems

    AppPullToRefresh(
        isRefreshing = isRefreshing,
        onRefresh = { items.refresh() },
        state = refreshState,
        modifier = modifier.fillMaxSize(),
    ) {
        Column(Modifier.fillMaxSize()) {
            Spacer(Modifier.height(8.dp))
            ExchangesHeaderRow()

            when (val s = items.loadState.refresh) {
                is LoadState.Loading -> {
                    if (hasItems) {
                        ExchangesList(
                            items = items,
                            modifier = Modifier.fillMaxSize(),
                            onNavigateToExchangeDetailsScreen = onNavigateToExchangeDetailsScreen
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
                        ExchangesList(
                            items = items,
                            modifier = Modifier.fillMaxSize(),
                            onNavigateToExchangeDetailsScreen = onNavigateToExchangeDetailsScreen
                        )
                    } else {
                        EmptyBox()
                    }
                }
            }
        }
    }
}

// ----------------------------- List & Row -----------------------------

@Composable
fun ExchangesList(
    items: LazyPagingItems<Exchanges>,
    modifier: Modifier = Modifier,
    onNavigateToExchangeDetailsScreen: (exchangeId: String) -> Unit,
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
            contentType = { _ -> "exchange_row" }
        ) { index ->
            items[index]?.let { exchange ->
                ExchangesRow(
                    exchange = exchange,
                    index = index,
                    onNavigateToExchangeDetailsScreen = onNavigateToExchangeDetailsScreen
                )
                AppHorizontalDivider(thickness = 0.5.dp)
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
fun ExchangesRow(
    exchange: Exchanges,
    index: Int,
    modifier: Modifier = Modifier,
    onNavigateToExchangeDetailsScreen: (exchangeId: String) -> Unit,
) {
    Row(
        Modifier.clickableWithNoRipple{
            onNavigateToExchangeDetailsScreen(exchange.id ?: "")
        }
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 15.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center){
            Text(
                text = "${index + 1}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }


        Row(
            Modifier.weight(4f).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = exchange.image,
                placeholder = painterResource(com.daricx.ui.R.drawable.core_ui_binance),
                error = painterResource(com.daricx.ui.R.drawable.core_ui_binance),
                contentDescription = exchange.name ?: "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(28.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Spacer(Modifier.width(5.dp))
            Column(Modifier) {
                Text(
                    exchange.name.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = NumberFormatterAdapter.compactNumber(exchange.tradeVolume24hBtc),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Box(modifier.weight(4f).fillMaxWidth(), contentAlignment = Alignment.Center){
            Text(
                text = exchange.tradeVolume24hBtc?.let { NumberFormatterAdapter.pricePretty(it) } ?: "—",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1
            )
        }


        Spacer(Modifier.width(8.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(3f).padding(horizontal = 5.dp)) {
            // Reserved for Trust/Sparkline/etc.
            val trust = exchange.trustScore ?: 0
            TrustScorePill(score = trust)
        }
    }
}

// ----------------------------- Header -----------------------------

@Composable
fun ExchangesHeaderRow(
    modifier: Modifier = Modifier
) {
    Card(
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
                onClick = {},
                modifier = Modifier.weight(1f),
            )
            SortableHeader(
                title = "Exchange",
                onClick = {},
                modifier = Modifier.weight(4f)
            )
            SortableHeader(
                title = "Volume",
                onClick = {},
                modifier = Modifier.weight(4f),
            )
            Spacer(Modifier.width(8.dp))
            SortableHeader(
                title = "Trust",
                onClick = {},
                modifier = Modifier.weight(3f),
            )
        }
    }
}

@Composable
private fun SortableHeader(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        TextButton(
            onClick = onClick,
            modifier = modifier.height(32.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            AppText(
                title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


// ----------------------------- Previews -----------------------------


// Main happy-path preview (NotLoading with content)
// @ThemePreviews don't work
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExchangesScreen_Preview_NotLoading_Light() {
    AppThemedPreview(darkTheme = false) {
        val sample = listOf(
            fakeExchange("binance", "Binance", 125_000.0),
            fakeExchange("okx", "OKX", 84_300.0),
            fakeExchange("bybit", "Bybit", 62_910.0),
            fakeExchange("coinbase", "Coinbase", 41_220.0),
        )
        val items = rememberPreviewPagingItems(sample)
        ExchangesScreen(
            items = items,
            modifier = Modifier.padding(horizontal = 0.dp),
            onNavigateToExchangeDetailsScreen = {},
        )
    }
}





@ThemePreviews
@Composable
fun ExchangesRow_Preview() {
    AppThemedPreview {
        ExchangesRow(
            exchange = fakeExchange("binance", "Binance", 125_000.0),
            index = 0,
            onNavigateToExchangeDetailsScreen = {},
        )
    }
}


/**
 * Indicator Loading
 * Initial loading (no data yet)
 */
@ThemePreviews
@Composable
fun ExchangesScreen_Preview_Loading() {
    AppThemedPreview {
        ExchangesScaffoldPreview(isRefreshing = true) {
            LoadingBox() //Loading Indicator
        }
    }
}

// Error (no data yet)
@ThemePreviews
@Composable
fun ExchangesScreen_Preview_Error() {
    AppThemedPreview {
        ExchangesScaffoldPreview(isRefreshing = false) {
            ErrorBox(
                message = "Network error: 429 Too Many Requests",
                onRetry = { /* no-op */ }
            )
        }
    }
}

// Empty (NotLoading with empty content)
@ThemePreviews
@Composable
fun ExchangesScreen_Preview_Empty() {
    AppThemedPreview {
        ExchangesScaffoldPreview(isRefreshing = false) {
            EmptyBox()
        }
    }
}
