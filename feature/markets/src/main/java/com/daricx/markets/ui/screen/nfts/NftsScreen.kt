package com.daricx.markets.ui.screen.nfts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.daricx.markets.data.NftsFakeData
import com.daricx.markets.ui.screen.EmptyBox
import com.daricx.markets.ui.screen.ErrorBox
import com.daricx.markets.ui.screen.ErrorFooter
import com.daricx.markets.ui.screen.LoadingBox
import com.daricx.markets.ui.screen.LoadingMore
import com.example.designsystem.component.AppHorizontalDivider
import com.example.designsystem.component.AppPullToRefresh
import com.example.designsystem.component.text.AppText
import com.example.designsystem.theme.AppThemedPreview
import com.example.model.nfts.Nfts
import org.koin.androidx.compose.koinViewModel


// -------------------------------- Column Weights --------------------------------
// Keep header and row in sync:
private const val CL_1 = 1f
private const val CL_2 = 1f
private const val CL_3 = 1f
// -------------------------------- Header --------------------------------


@Composable
fun NftsRoute(
    viewModel: NftsViewModel = koinViewModel()
) {
    val nfts = viewModel.pagedNfts.collectAsLazyPagingItems()
    val ui by viewModel.uiState.collectAsStateWithLifecycle()

    NftsScreen(nfts)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NftsScreen(
    items: LazyPagingItems<Nfts>,
    modifier: Modifier = Modifier
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
            NftsHeaderRow()

            when (val s = items.loadState.refresh) {
                is LoadState.Loading -> {
                    if (hasItems) {
                        NftsList(items = items, modifier = Modifier.fillMaxSize())
                    } else {
                        LoadingBox()
                    }
                }
                is LoadState.Error -> {
                    ErrorBox(message = s.error.message ?: "Error") { items.retry() }
                }
                is LoadState.NotLoading -> {
                    if (hasItems) {
                        NftsList(items = items, modifier = Modifier.fillMaxSize())
                    } else {
                        EmptyBox()
                    }
                }
            }
        }
    }
}


@Composable
fun NftsHeaderRow(
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
                title = "Name",
                onClick = { },
                modifier = Modifier.weight(CL_1)
            )
            SortableHeader(
                title = "Platform",
                onClick = { },
                modifier = Modifier.weight(CL_2)
            )
            SortableHeader(
                title = "Symbol",
                onClick = { },
                modifier = Modifier.weight(CL_3)
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
    Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        TextButton(
            onClick = onClick,
            modifier = Modifier.height(32.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            AppText(
                title,
                style = MaterialTheme.typography.labelMedium
                    .copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Composable
private fun NftsList(
    items: LazyPagingItems<Nfts>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 5.dp)
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
                NftRow(nfts = coin)
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
private fun NftRow(
    nfts: Nfts,
    modifier: Modifier = Modifier
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 15.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(Modifier.weight(CL_1).fillMaxWidth(), contentAlignment = Alignment.Center) {
            AppText(
                nfts.name ?: "—",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Box(Modifier.weight(CL_2).fillMaxWidth(), contentAlignment = Alignment.Center) {
            AppText(
                nfts.assetPlatformId ?: "—",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Box(Modifier.weight(CL_3).fillMaxWidth(), contentAlignment = Alignment.Center) {
            AppText(
                nfts.symbol ?: "—",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NftsScreen_Preview_NotLoading() {
    AppThemedPreview(darkTheme = false) {
        val sample = NftsFakeData.nfts
        val items = rememberNftsPreviewItems(sample)
        NftsScreen(
            items = items,
        )
    }
}
