package com.daricx.app
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.view.WindowCompat
import com.example.data.utils.NetworkMonitor
import com.example.designsystem.theme.AppTheme
import com.google.samples.apps.nowinandroid.core.data.util.TimeZoneMonitor
import com.daricx.app.ui.App
import com.daricx.app.ui.rememberAppState
import com.daricx.settings.SettingsViewModel
import com.daricx.settings.menu.DrawerMenuScreen
import com.daricx.settings.menu.DrawerMenuSheet
import com.daricx.settings.menu.MenuAction
import com.daricx.settings.menu.MenuUiState
import com.daricx.ui.snackbar.LocalSnackbarController
import com.daricx.ui.snackbar.SnackbarController
import com.example.model.settings.AppSettings
import com.example.model.settings.AppThemeOption
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var timeZoneMonitor: TimeZoneMonitor

    @Inject lateinit var snackbarController: SnackbarController

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        enableEdgeToEdge()
        setContent {
            val scope = rememberCoroutineScope()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val settingsVm: SettingsViewModel = androidx.hilt.navigation.compose.hiltViewModel()
            val settings by settingsVm.settings.collectAsState()
            val appState = rememberAppState(
                networkMonitor = networkMonitor,
                timeZoneMonitor = timeZoneMonitor,
            )

            BackHandler(enabled = drawerState.isOpen) {
                scope.launch { drawerState.close() }
            }

            CompositionLocalProvider(LocalSnackbarController provides snackbarController) {
                AppTheme(
                    darkTheme = when (settings.theme) {
                        AppThemeOption.Dark -> true
                        AppThemeOption.Light -> false
                        AppThemeOption.System -> isSystemInDarkTheme()
                    },
                    dynamicColor = false,
                ) {
                    DrawerMenu(
                        drawerState = drawerState,
                        settings=settings,
                        onAction = { act ->
                            when (act) {
                                is MenuAction.ChangeTheme     -> settingsVm.changeTheme(act.theme)
                                is MenuAction.ToggleCurrency  -> settingsVm.changeCurrency(act.currency)
                                is MenuAction.ChangeLanguage  -> settingsVm.changeLanguage(act.language)
                                else -> { /* navigate, open screens… */ }
                            }
                        },
                    ){
                        App(appState = appState, snackbarController = snackbarController, onOpenDrawerMenu = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        })
                    }
                }
            }
        }
    }
}


@Composable
fun DrawerMenu(
    drawerState: DrawerState,
    settings: AppSettings,
    onAction: (MenuAction) -> Unit,
    content: @Composable () -> Unit
){
    DrawerMenuScreen(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenuSheet(
                state = MenuUiState(
                    theme = settings.theme,
                    language = settings.language,
                    currency = settings.currency,
                    notificationsCount = 0
                ),
                onAction = onAction
            )
        },
        content = content
    )
}


/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val appState = rememberAppState(
        networkMonitor = networkMonitor,
        timeZoneMonitor = timeZoneMonitor,
    )
    AppThemedPreview {
        App(appState)
    }
}*/
