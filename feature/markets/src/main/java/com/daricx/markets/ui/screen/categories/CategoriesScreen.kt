package com.daricx.markets.ui.screen.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.daricx.markets.data.CategoriesFakeData
import com.daricx.ui.PercentChangeView
import com.example.common.numbers.adapter.NumberFormatterAdapter
import com.example.designsystem.component.AppHorizontalDivider
import com.example.designsystem.component.AppPullToRefresh
import com.example.designsystem.component.text.AppText
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import com.example.model.Categories
import com.example.model.sort.SortKey
import com.example.model.sort.SortOption
import com.example.model.sort.SortOrder
import org.koin.androidx.compose.koinViewModel

// -------------------------------- Column Weights --------------------------------
// Keep header and row in sync:
private const val CL_1 = 2f
private const val Cl_2 = 5f
private const val Cl_3 = 2f
private const val Cl_4 = 3f
// -------------------------------- Column Weights --------------------------------

@Composable
fun CategoriesRoute(viewModel: CategoriesViewModel = koinViewModel()){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CategoriesScreen(
        //categories = com.daricx.markets.ui.data.CategoriesFakeData.categories,
        categories = uiState.categories,
    )
    //LaunchedEffect(Unit) { viewModel.getTrending() }
    //TrendingScreen(trending = uiState.trending)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    categories: List<Categories?>? = null,
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
                Spacer(modifier.height(12.dp))
                CategoriesHeaderRow(
                    sort = SortOption(sortKey = SortKey.MARKET_CAP, sortOrder = SortOrder.DESC),
                    onHeaderClick = {}
                )
            }

            if (categories!=null){
                items(categories.size) { index ->
                    categories[index]?.let {
                        CategoryRow(category = categories[index])
                        AppHorizontalDivider()
                    }


                }
            }

        }
    }
}


@Composable
fun CategoriesHeaderRow(
    sort: SortOption,
    onHeaderClick: (SortKey) -> Unit,
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
                .padding(horizontal = 5.dp, vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SortableHeader(
                title = "Top Coins",
                active = sort.sortKey == SortKey.RANK,
                direction = sort.sortOrder.takeIf { sort.sortKey == SortKey.RANK },
                onClick = { onHeaderClick(SortKey.RANK) },
                modifier = Modifier.weight(CL_1)
            )
            SortableHeader(
                title = "Category",
                active = sort.sortKey == SortKey.MARKET_CAP,
                direction = sort.sortOrder.takeIf { sort.sortKey == SortKey.MARKET_CAP },
                onClick = { onHeaderClick(SortKey.MARKET_CAP) },
                modifier = Modifier.weight(Cl_2)
            )
            SortableHeader(
                title = "24H",
                active = sort.sortKey == SortKey.PRICE,
                direction = sort.sortOrder.takeIf { sort.sortKey == SortKey.PRICE },
                onClick = { onHeaderClick(SortKey.PRICE) },
                modifier = Modifier.weight(Cl_3)
            )
            SortableHeader(
                title = "Market Cap",
                active = sort.sortKey == SortKey.CHANGE_24H,
                direction = sort.sortOrder.takeIf { sort.sortKey == SortKey.CHANGE_24H },
                onClick = { onHeaderClick(SortKey.CHANGE_24H) },
                modifier = Modifier.weight(Cl_4)
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
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
           /* if (active && direction != null) {
                Icon(
                    imageVector = if (direction == SortOrder.ASC)
                        Icons.Rounded.KeyboardArrowUp
                    else Icons.Rounded.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }*/
        }
    }
}


@Composable
fun CategoryRow(
    category: Categories?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier.weight(CL_1).fillMaxWidth(), contentAlignment = Alignment.Center) {

            LazyRow(modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start) {
                if (!category?.top3Coins.isNullOrEmpty()){
                    items(category.top3Coins!!){
                        AsyncImage(
                            model = it,
                            placeholder = painterResource(com.daricx.ui.R.drawable.daricx_logo_place_holder),
                            error = painterResource(com.daricx.ui.R.drawable.daricx_logo_place_holder),
                            contentDescription =  "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(18.dp)
                                .clip(MaterialTheme.shapes.small)
                        )
                    }
                }
            }

        }
        Box(modifier.weight(Cl_2).fillMaxWidth().padding(horizontal = 4.dp), contentAlignment = Alignment.Center) {
            AppText(
                text = category?.name ?: "—",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1,
            )
        }
        val pctUsd = category?.marketCapChange24h
        pctUsd?.let { value ->
            Box(modifier.weight(Cl_3).fillMaxWidth().padding(horizontal = 4.dp), contentAlignment = Alignment.Center) {
                PercentChangeView(
                    percent = value,
                    showBackground = false,
                    showIcon = false,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.padding(top = 0.dp)
                )
            }
        }

        Box(modifier.weight(Cl_4).fillMaxWidth(), contentAlignment = Alignment.Center) {

            AppText(
                //text = category?.marketCap?.let { NumberFormatters.pricePretty(it) } ?: "—",
                text =  NumberFormatterAdapter.compactNumber(category?.marketCap)?: "—",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
    }
}



@ThemePreviews
@Composable
fun CategoriesScreenPreview(){
    AppThemedPreview {
        CategoriesScreen(
            categories = CategoriesFakeData.categories,
        )
    }
}