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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.daricx.markets.data.CoinDetailsFakes
import com.daricx.markets.ui.screen.coins.coin.screen.AboutCoinRoute

import com.daricx.markets.ui.screen.coins.coin.screen.OverviewCoinRoute

import com.example.designsystem.component.AppTabPager
import com.example.designsystem.component.AppTabPagerItems
import com.example.designsystem.component.text.AppText
import com.example.designsystem.component.icons.AppIcon
import com.example.designsystem.extension.onBackPress
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import com.example.model.coins.CoinDetails


@Composable
fun CoinDetailsRoute() {
    //val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    CoinDetailsScreen()
}

@Composable
fun CoinDetailsScreen(
    modifier: Modifier = Modifier
) {
    val coinDetailsFakeData = CoinDetailsFakes.bitcoin()

    Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Scaffold(
            modifier = modifier.padding(top = 10.dp),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            topBar = { Header(coinDetails = coinDetailsFakeData) }
        ) {
            val paddingV = it
            Box(
                modifier = modifier
                    .padding(paddingV)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
            ) {
                TabsSection()
            }
        }
    }

}


@Composable
private fun Header(modifier: Modifier = Modifier,coinDetails: CoinDetails) {
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
                model = coinDetails.image?.small,
                placeholder = painterResource(com.daricx.ui.R.drawable.core_ui_bitcoin),
                error = painterResource(com.daricx.ui.R.drawable.core_ui_bitcoin),
                contentDescription = coinDetails.name ?: "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(28.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Spacer(modifier.width(3.dp))
            AppText(coinDetails.symbol?.uppercase()?:"", modifier = Modifier.align(Alignment.CenterVertically))
        }

        Row(
            modifier = modifier.weight(2f).padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIcon(AppIcons.Search, contentDescription = "",modifier.size(22.dp))
            Spacer(modifier.width(12.dp))
            AppIcon(AppIcons.Star, contentDescription = "",modifier.size(22.dp))
            Spacer(modifier.width(10.dp))
            AppIcon(AppIcons.Share, contentDescription = "",modifier.size(22.dp))
        }
    }
}

@Composable
private fun TabsSection(modifier: Modifier= Modifier) {
    Box(
        modifier
            .padding(top = 0.dp)
            .fillMaxSize()
    ) {
        val tabs = listOf(
            AppTabPagerItems(
                title = "Overview",
                contentScreens = {
                    OverviewCoinRoute()
                },
            ),
            AppTabPagerItems(
                title = "About",
                contentScreens = {
                    AboutCoinRoute()
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
    AppThemedPreview {
        CoinDetailsScreen()
    }
}