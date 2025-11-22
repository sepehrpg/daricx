package com.daricx.markets.ui.screen.exchanges.exchange

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
import com.daricx.markets.data.ExchangeDetailsFakes
import com.daricx.markets.ui.screen.exchanges.exchange.screen.OverviewExchangeRoute
import com.daricx.ui.ComingSoonNoticeText
import com.daricx.ui.TrustScorePill
import com.example.designsystem.component.AppTabPager
import com.example.designsystem.component.AppTabPagerItems
import com.example.designsystem.component.icons.AppIcon
import com.example.designsystem.component.text.AppText
import com.example.designsystem.extension.onBackPress
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import com.example.model.exchanges.ExchangeDetail
import com.example.model.option.CryptoTimeRange


@Composable
fun ExchangeDetailsRoute(
    viewModel: ExchangeDetailsViewModel = hiltViewModel(),
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ExchangeDetailsScreen(
        uiState = uiState,
        onRefresh= viewModel::getExchangeDetails,
        onTimeRangeSelected = viewModel::onTimeRangeSelected
    )
}


@Composable
fun ExchangeDetailsScreen(
    uiState: ExchangeDetailsUiState,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    onTimeRangeSelected: (CryptoTimeRange) -> Unit,
){
    Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Scaffold(
            modifier = modifier.padding(top = 10.dp),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            topBar = { Header(exchangeDetail = uiState.exchangeDetails) }
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
private fun Header(modifier: Modifier = Modifier,exchangeDetail: ExchangeDetail?) {
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
                model = exchangeDetail?.image,
                placeholder = painterResource(com.daricx.ui.R.drawable.daricx_logo_place_holder),
                error = painterResource(com.daricx.ui.R.drawable.daricx_logo_place_holder),
                contentDescription = exchangeDetail?.name ?: "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(28.dp)
                    .clip(MaterialTheme.shapes.small)
            )

            Spacer(modifier.width(5.dp))
            AppText(exchangeDetail?.name?:"", modifier = Modifier.align(Alignment.CenterVertically))

            Spacer(modifier.width(15.dp))

            val trust = exchangeDetail?.trustScore ?: 0
            TrustScorePill(score = trust)
        }
    }
}


@Composable
private fun TabsSection(
    modifier: Modifier= Modifier,
    uiState: ExchangeDetailsUiState,
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
                    OverviewExchangeRoute(
                        uiState = uiState,
                        onRefresh =onRefresh,
                        onTimeRangeSelected = onTimeRangeSelected
                    )
                },
            ),
            AppTabPagerItems(
                title = "Markets",
                contentScreens = {
                    Box(modifier.padding(horizontal = 20.dp)){
                        ComingSoonNoticeText()
                    }
                },
            ),

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
fun ExchangeDetailsScreenPreview(){
    val exchangeDetailFakeData = ExchangeDetailsFakes.binanceFromReal()
    AppThemedPreview {
        ExchangeDetailsScreen(
            uiState = ExchangeDetailsUiState(
                exchangeDetails = exchangeDetailFakeData,
                isLoading = false,
                error = null
            ),
            onRefresh = {},
            onTimeRangeSelected = {}
        )
    }
}