package com.daricx.markets.ui.screen.coins.coin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.daricx.markets.data.CoinDetailsFakes
import com.daricx.markets.ui.screen.coins.coin.screen.AboutCoinRoute

import com.daricx.markets.ui.screen.coins.coin.screen.OverviewCoinRoute

import com.example.designsystem.component.AppTabPager
import com.example.designsystem.component.AppTabPagerItems
import com.example.designsystem.component.text.AppText
import com.example.designsystem.component.icons.AppIcon
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.extension.onBackPress
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.StarColor
import com.example.designsystem.theme.ThemePreviews
import com.example.model.coins.CoinDetails
import com.example.model.option.CryptoTimeRange
import org.koin.androidx.compose.koinViewModel
import kotlin.collections.contains


@Composable
fun CoinDetailsRoute(
    viewModel: CoinDetailsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()
    val isFavorite = favoriteIds.contains(uiState.coinDetails?.id)

    CoinDetailsScreen(
        uiState = uiState,
        isFavorite = isFavorite,
        onRefresh = viewModel::onRefresh,
        onFavoriteClick = viewModel::onFavoriteClick,
        onTimeRangeSelected = viewModel::onTimeRangeSelected
    )
}

@Composable
fun CoinDetailsScreen(
    uiState: CoinDetailsUiState,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteClick: (coin: CoinDetails?) -> Unit,
    onRefresh: () -> Unit,
    onTimeRangeSelected: (CryptoTimeRange) -> Unit,
) {

    Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Scaffold(
            modifier = modifier.padding(top = 10.dp),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            topBar = { Header(
                coinDetails = uiState.coinDetails,
                onFavoriteClick=onFavoriteClick,
                isFavorite = isFavorite
            ) }
        ) {
            val paddingV = it
            Box(
                modifier = modifier
                    .padding(paddingV)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
            ) {
                TabsSection(
                    uiState = uiState,
                    onRefresh = onRefresh,
                    onTimeRangeSelected = onTimeRangeSelected
                )
            }
        }
    }

}


@Composable
private fun Header(
    modifier: Modifier = Modifier,
    coinDetails: CoinDetails?,
    isFavorite: Boolean,
    onFavoriteClick: (coin: CoinDetails?) -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            AppIcon(
                AppIcons.KeyboardArrowLeft,
                contentDescription = "",
                modifier = modifier.size(27.dp).onBackPress()
            )
        }

        Spacer(modifier.width(10.dp))

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = coinDetails?.image?.small,
                placeholder = painterResource(com.daricx.ui.R.drawable.daricx_logo_place_holder),
                error = painterResource(com.daricx.ui.R.drawable.daricx_logo_place_holder),
                contentDescription = coinDetails?.name ?: "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(28.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Spacer(modifier.width(3.dp))
            AppText(coinDetails?.symbol?.uppercase()?:"", modifier = Modifier.align(Alignment.CenterVertically))
        }

        Row(
            modifier = modifier.weight(2f).padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIcon(AppIcons.Search, contentDescription = "",modifier.size(23.dp))
            Spacer(modifier.width(12.dp))

            AppIcon(
                if (isFavorite) AppIcons.StarFill else AppIcons.Star,
                contentDescription = "",
                modifier = modifier.size(23.dp).clickableWithNoRipple(onClick = { onFavoriteClick(coinDetails) }),
                tint = if (isFavorite) StarColor  else MaterialTheme.colorScheme.surfaceTint,
            )

            Spacer(modifier.width(10.dp))
            AppIcon(AppIcons.Share, contentDescription = "",modifier.size(23.dp))
        }
    }
}

@Composable
private fun TabsSection(
    modifier: Modifier= Modifier,
    uiState: CoinDetailsUiState,
    onRefresh: () -> Unit,
    onTimeRangeSelected: (CryptoTimeRange) -> Unit,
) {
    Box(
        modifier
            .padding(top = 0.dp)
            .fillMaxSize()
    ) {
        val tabs = listOf(
            AppTabPagerItems(
                title = "Overview",
                contentScreens = {
                    OverviewCoinRoute(
                        uiState = uiState,
                        onRefresh =onRefresh,
                        onTimeRangeSelected = onTimeRangeSelected
                    )
                },
            ),
            AppTabPagerItems(
                title = "About",
                contentScreens = {
                    AboutCoinRoute(uiState = uiState, onRefresh =onRefresh)
                },
            ),

            /*AppTabPagerItems(
                title = "Markets",
                contentScreens = {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        MarketsCoinScreen()
                    }
                },
            ),*/

            )

        AppTabPager(
            tabs = tabs,
            scrollable = false,
            tabContainerColor = MaterialTheme.colorScheme.surface,
            tabContentColor = MaterialTheme.colorScheme.onSurface,
            dividerColor = MaterialTheme.colorScheme.outlineVariant,
            indicatorColor = MaterialTheme.colorScheme.primary,
            tabTextColorSelected = MaterialTheme.colorScheme.onBackground,
            tabTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicatorWidthMatchWithTextSize = false,
            indicatorShape = RoundedCornerShape(10.dp),
            thicknessIndicator = 2.dp,
            dividerThickness = 2.dp,
            tabPadding = 12.dp,
            modifier = Modifier.fillMaxSize()
        )
    }
}




@ThemePreviews
@Composable
private fun CoinScreenPreview() {
    val coinDetailsFakeData = CoinDetailsFakes.bitcoin()
    AppThemedPreview {
        CoinDetailsScreen(
            uiState = CoinDetailsUiState(
                coinDetails = coinDetailsFakeData,
                isLoading = false,
                error = null
            ),
            isFavorite = false,
            onFavoriteClick = {},
            onRefresh = {},
            onTimeRangeSelected = {}
        )
    }
}