package com.daricx.markets.ui.screen.nfts

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
import com.example.model.nfts.Nfts
import kotlinx.coroutines.flow.flowOf

// -------------------------------- Previews Helpers --------------------------------

@Composable
fun rememberNftsPreviewItems(
    data: List<Nfts>
): LazyPagingItems<Nfts> {
    val flow = remember(data) { flowOf(PagingData.from(data)) }
    return flow.collectAsLazyPagingItems()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NftsScaffoldPreview(
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
            NftsHeaderRow( )
            content()
            Spacer(Modifier.height(24.dp))
        }
    }
}

