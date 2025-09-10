package com.daricx.markets.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.daricx.markets.ui.MarketsRoute
import kotlinx.serialization.Serializable




@Serializable object MarketsIntroRoute

@Serializable object MarketsRoute

//@Serializable data class WorkSpaceScreenRoute(val projectID: Int)

/*fun NavController.navigateToWorkSpaceScreen(projectID: Int) =
    navigate(route = WorkSpaceScreenRoute(projectID = projectID))*/

fun NavGraphBuilder.marketsScreen(
    onOpenDrawerMenu: () -> Unit,
) {

    navigation<MarketsIntroRoute>(startDestination = MarketsRoute){

        composable<MarketsRoute> {
            MarketsRoute(onOpenDrawerMenu=onOpenDrawerMenu)
        }

        /*composable<WorkSpaceScreenRoute> {
            WorkSpaceScreen(onBackClick = onBackClickFromWorkSpaceScreen)
        }*/

    }
}