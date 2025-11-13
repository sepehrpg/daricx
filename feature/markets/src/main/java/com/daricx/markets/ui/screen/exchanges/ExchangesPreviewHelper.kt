
package com.daricx.markets.ui.screen.exchanges

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.designsystem.component.AppPullToRefresh
import com.example.model.exchanges.Exchanges
import kotlinx.coroutines.flow.flowOf


// ----------------------------- Preview helpers -----------------------------

/**
 * Create LazyPagingItems from static data for previews.
 * NOTE: We do not try to override or fake LazyPagingItems internals.
 */
@Composable
fun rememberPreviewPagingItems(
    data: List<Exchanges>
): LazyPagingItems<Exchanges> {
    val flow = remember(data) { flowOf(PagingData.from(data)) }
    return flow.collectAsLazyPagingItems()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangesScaffoldPreview(
    isRefreshing: Boolean = false,
    content: @Composable () -> Unit
) {
    val refreshState = rememberPullToRefreshState()
    AppPullToRefresh(
        isRefreshing = isRefreshing,
        onRefresh = { /* no-op in preview */ },
        state = refreshState,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(Modifier.fillMaxSize()) {
            Spacer(Modifier.height(8.dp))
            ExchangesHeaderRow()
            content()
            Spacer(Modifier.height(24.dp))
        }
    }
}

fun fakeExchange(
    id: String,
    name: String,
    volumeBtc: Double
) = Exchanges(
    id = id,
    name = name,
    country = "Global",
    description = "Leading crypto exchange with high liquidity and trading pairs.",
    hasTradingIncentive = true,
    image = "https://assets.coingecko.com/markets/images/52/small/binance.jpg?1695800957",
    tradeVolume24hBtc = volumeBtc,
    trustScore = 10,
    trustScoreRank = 1,
    url = null,
    yearEstablished = 2017
)