package com.daricx.settings.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.designsystem.component.AppText
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun DrawerMenuScreen(
    drawerState: DrawerState,
    openDirection: LayoutDirection = LayoutDirection.Rtl,
    contentDirection: LayoutDirection = LayoutDirection.Ltr,
    drawerContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides openDirection) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides contentDirection) {
                    drawerContent()
                }
            }
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides contentDirection) {
                content()
            }
        }
    }
}

@Composable
private fun DrawerHostSample() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    var ui by remember {
        mutableStateOf(
            MenuUiState()
        )
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenuSheet(
                state = MenuUiState(
                    theme = ui.theme,
                    language = ui.language,
                    currency = ui.currency,
                    notificationsCount = 0
                ),
                onAction = {}
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) { AppText("Main content") }
    }
}


@ThemePreviews
@Composable
private fun DrawerPreview() {
    AppThemedPreview {
        DrawerHostSample()
    }
}