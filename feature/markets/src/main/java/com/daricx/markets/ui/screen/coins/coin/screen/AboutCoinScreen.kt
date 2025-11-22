package com.daricx.markets.ui.screen.coins.coin.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.daricx.markets.data.CoinDetailsFakes
import com.daricx.markets.ui.mapper.CoinLinkItemsMapper.buildCoinCommunityLinks
import com.daricx.markets.ui.mapper.CoinLinkItemsMapper.buildCoinExplorersLinkItems
import com.daricx.markets.ui.mapper.CoinLinkItemsMapper.buildCoinOfficialLinkItems
import com.daricx.markets.ui.model.LinkItem
import com.daricx.markets.ui.screen.ErrorBox
import com.daricx.markets.ui.screen.LoadingBox
import com.daricx.markets.ui.screen.coins.coin.CoinDetailsUiState
import com.example.designsystem.component.AppHorizontalDivider
import com.example.designsystem.component.AppPullToRefresh
import com.example.designsystem.component.chips.AppElevatedAssistChip
import com.example.designsystem.component.text.AppText
import com.example.designsystem.component.text.AppTextReadMoreHtmlFormat
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import com.example.model.coins.CoinDetails
import kotlin.String


@Composable
fun AboutCoinRoute(
    uiState: CoinDetailsUiState,
    onRefresh: () -> Unit,
){
    AboutCoinScreen(uiState = uiState, onRefresh =onRefresh)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutCoinScreen(
    modifier: Modifier = Modifier,
    uiState: CoinDetailsUiState,
    onRefresh: () -> Unit,
){
    val refreshState = rememberPullToRefreshState()
    // Show pull-to-refresh spinner only when we are refreshing over existing content.
    val isRefreshing = uiState.isLoading && uiState.coinDetails != null


    Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.fillMaxSize()
    ) {


        AppPullToRefresh(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            state = refreshState,
            modifier = modifier.fillMaxSize(),
        ) {
            when {
                uiState.isLoading && uiState.coinDetails == null -> {
                    LoadingBox(modifier = modifier.height(2.dp))
                }

                uiState.error != null && uiState.coinDetails == null -> {
                    ErrorBox(
                        message = uiState.error,
                        onRetry = onRefresh,
                    )
                }

                uiState.coinDetails == null -> {
                    ErrorBox(
                        message = "Empty List",
                        onRetry = onRefresh,
                    )
                }
                else -> {
                    AboutCoinScreenContent(
                        coinDetails = uiState.coinDetails,
                        modifier = modifier
                    )
                }
            }
        }

    }
}


@Composable
private fun AboutCoinScreenContent(
    coinDetails: CoinDetails,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier.fillMaxSize().padding(vertical = 12.dp)){
        item {
            AboutCoin(coinDetails = coinDetails)
            Spacer(modifier.height(7.dp))
        }

        item{
            LinksChip(
                links =  buildCoinOfficialLinkItems(coinDetails),
                title = "Official Links"
            )
            Spacer(modifier.height(7.dp))
            AppHorizontalDivider()
        }

        item{
            LinksChip(
                links = buildCoinExplorersLinkItems(coinDetails),
                title = "Explorers"
            )
            Spacer(modifier.height(7.dp))
            AppHorizontalDivider()
        }

        item{
            LinksChip(
                links = buildCoinCommunityLinks(coinDetails),
                title = "Community"
            )
            Spacer(modifier.height(7.dp))
            AppHorizontalDivider()
        }




    }
}


@Composable
private fun AboutCoin(
    modifier: Modifier= Modifier,
    coinDetails: CoinDetails?,
) {
    val coinName = coinDetails?.name
    val symbol = coinDetails?.symbol?.uppercase()
    val description = coinDetails?.description?.translations?.get("en")

    if (!description.isNullOrEmpty()){
        Column(modifier.padding(horizontal = 10.dp)){
            AppText(
                "About $coinName",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = modifier.height(5.dp))
            AppText(
                "What is $coinName ($symbol)?",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = modifier.height(10.dp))

            AppTextReadMoreHtmlFormat(
                modifier = modifier,
                text = description?:"",
                collapsedMaxLines = 5,
                readMoreText = "Show more",
                readLessText = "Show less",
                readMoreColor = MaterialTheme.colorScheme.inversePrimary
            )
        }
    }
}

/*@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LinkChipsRow(
    links: List<LinkChip>,
    modifier: Modifier = Modifier,
    maxItemsInEachRow: Int = 3
) {
    val uriHandler = LocalUriHandler.current

    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = maxItemsInEachRow
    ) {
        links.forEachIndexed { index, chip ->
            AssistChip(
                onClick = { runCatching { uriHandler.openUri(chip.url) } },
                label = { Text(DomainExtractor.domain(chip.label), style = MaterialTheme.typography.labelLarge) },
                modifier = Modifier.padding(4.dp)
            )
            if (index == links.lastIndex) {
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}*/




@Composable
private fun LinksChip(
    modifier: Modifier= Modifier,
    links: List<LinkItem>,
    title: String
){
    val uriHandler = LocalUriHandler.current

    Column(
        modifier =modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, top = 7.dp)
    ){
        AppText(
            title,
            style = MaterialTheme.typography.titleMedium,
            )
        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ){
            links.forEach {
                AppElevatedAssistChip(
                    onClick = { runCatching { uriHandler.openUri(it.url) } },
                    label = { AppText(it.label, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)) },
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        if (it.icon!=null){
                            Image(it.icon,
                                null,
                                modifier = modifier.size(18.dp)
                            )

                        }
                    }
                )
            }
        }
    }
}


/*@Composable
private fun SocialsLinks(modifier: Modifier= Modifier){
    val statics: List<LinkChip> = listOf(
        LinkChip("site",""),
        LinkChip("whitepaper",""),
        LinkChip("github",""),
    )
    val explor: List<LinkChip> = listOf(
        LinkChip("https://mempool.space/",""),
        LinkChip("https://blockchair.com/bitcoin/",""),
        LinkChip("https://btc.com/",""),
        LinkChip("https://btc.tokenview.io/",""),
        LinkChip("https://www.oklink.com/btc",""),
        LinkChip("https://3xpl.com/bitcoin",""),
    )
    val community: List<LinkChip> = listOf(
        LinkChip("Reddit",""),
        LinkChip("twitter",""),
        LinkChip("Telegram",""),
        LinkChip("facebook",""),
        LinkChip("bitcointalk-thread",""),
    )

    Column(
        modifier =modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, top = 20.dp)
    ){

        AppText(
            "Socials",
            style = MaterialTheme.typography.titleMedium,
        )
        LinkChipsRow(links = statics, modifier = Modifier.padding(horizontal = 12.dp))
    }
}*/

@ThemePreviews
@Composable
private fun AboutCoinScreenPreview(){
    val coinDetailsFakeData = CoinDetailsFakes.bitcoin()

    AppThemedPreview {
        AboutCoinScreen(
            uiState = CoinDetailsUiState(
                coinDetails = coinDetailsFakeData,
                isLoading = false,
                error = null
            ),
            onRefresh = {}
        )
    }
}