
package com.daricx.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.daricx.app.ui.AppState
import com.daricx.markets.navigation.MarketsIntroRoute
import com.daricx.markets.navigation.marketsScreen


@Composable
fun AppNavHost(
    appState: AppState,
    onOpenDrawerMenu: () -> Unit,
    onShowSnackBar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = MarketsIntroRoute,
        modifier = modifier,
    ) {
        marketsScreen(
            onOpenDrawerMenu = onOpenDrawerMenu
        )

    }
}
