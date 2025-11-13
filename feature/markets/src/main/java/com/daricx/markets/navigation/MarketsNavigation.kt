package com.daricx.markets.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.daricx.markets.ui.MarketsRoute
import com.daricx.markets.ui.screen.coins.coin.CoinDetailsRoute
import com.daricx.markets.ui.screen.exchanges.exchange.ExchangeDetailsRoute
import kotlinx.serialization.Serializable



@Serializable object MarketsIntroRoute
@Serializable object MarketsRoute
@Serializable data class CoinDetailsScreenRoute(val coinId: String)
@Serializable data class ExchangeDetailsScreenRoute(val exchangeId: String)



fun NavController.navigateToCoinDetailsScreen(
    coinId: String,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    if (builder != null) {
        navigate(CoinDetailsScreenRoute(coinId), builder)
    } else {
        navigate(CoinDetailsScreenRoute(coinId))
    }
}

fun NavController.navigateToExchangeDetailsScreen(
    exchangeId: String,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    if (builder != null) {
        navigate(ExchangeDetailsScreenRoute(exchangeId), builder)
    } else {
        navigate(ExchangeDetailsScreenRoute(exchangeId))
    }
}



fun NavGraphBuilder.marketsScreen(
    onOpenDrawerMenu: () -> Unit,
    onNavigateToCoinDetailsScreen: (coinId: String) -> Unit,
    onNavigateToExchangeDetailsScreen: (exchangeId: String) -> Unit,
) {

    navigation<MarketsIntroRoute>(startDestination = MarketsRoute){
        composable<MarketsRoute> {
            MarketsRoute(
                onOpenDrawerMenu = onOpenDrawerMenu,
                onNavigateToCoinDetailsScreen = onNavigateToCoinDetailsScreen,
                onNavigateToExchangeDetailsScreen = onNavigateToExchangeDetailsScreen
            )
        }

        composable<CoinDetailsScreenRoute> {
            Box(Modifier.statusBarsPadding()){
                CoinDetailsRoute()
            }
        }

        composable<ExchangeDetailsScreenRoute> {
            Box(Modifier.statusBarsPadding()){
                ExchangeDetailsRoute()
            }
        }

    }
}