package com.daricx.markets.ui.screen.coins

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
import com.example.model.coins.Coins
import com.example.model.sort.SortKey
import com.example.model.sort.SortOption
import com.example.model.sort.SortOrder
import kotlinx.coroutines.flow.flowOf

// -------------------------------- Previews Helpers --------------------------------

@Composable
fun rememberCoinsPreviewItems(
    data: List<Coins>
): LazyPagingItems<Coins> {
    val flow = remember(data) { flowOf(PagingData.from(data)) }
    return flow.collectAsLazyPagingItems()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsScaffoldPreview(
    isRefreshing: Boolean = false,
    content: @Composable () -> Unit
) {
    val refreshState = rememberPullToRefreshState()
    AppPullToRefresh(
        isRefreshing = isRefreshing,
        onRefresh = { /* no-op */ },
        state = refreshState,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(Modifier.fillMaxSize()) {
            Spacer(Modifier.height(8.dp))
            CoinsHeaderRow(
                sort = SortOption(sortKey = SortKey.MARKET_CAP, sortOrder = SortOrder.DESC),
                onHeaderClick = {}
            )
            content()
            Spacer(Modifier.height(24.dp))
        }
    }
}

// -------------------------------- Fake Data --------------------------------

