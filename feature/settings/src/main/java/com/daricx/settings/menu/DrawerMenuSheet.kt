package com.daricx.settings.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.AppHorizontalDivider
import com.example.designsystem.component.icons.AppIcon
import com.example.designsystem.component.AppText
import com.example.designsystem.component.buttons.AppButton
import com.example.designsystem.component.buttons.AppOutlinedButton
import com.example.designsystem.component.cards.AppElevatedCard
import com.example.designsystem.component.tabs.AppPillTabs
import com.example.designsystem.icon.AppIcons.AccountCircleIcon
import com.example.designsystem.icon.AppIcons.BadgeIcon
import com.example.designsystem.icon.AppIcons.ChevronRightIcon
import com.example.designsystem.icon.AppIcons.CloseIcon
import com.example.designsystem.icon.AppIcons.DiamondIcon
import com.example.designsystem.icon.AppIcons.LanguageIcon
import com.example.designsystem.icon.AppIcons.NotificationIcon
import com.example.designsystem.icon.AppIcons.PriceChangeIcon
import com.example.designsystem.icon.AppIcons.RocketLaunchIcon
import com.example.designsystem.icon.AppIcons.SwapHorizIcon
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import com.example.model.settings.AppCurrency
import com.example.model.settings.AppLanguage
import com.example.model.settings.AppThemeOption
import com.example.model.settings.displayName


@Composable
fun DrawerMenuSheet(
    state: MenuUiState,
    onAction: (MenuAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val scroll = rememberScrollState()
    ModalDrawerSheet(
        modifier = modifier.fillMaxWidth(0.92f),
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scroll)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Header(
                onLogin = { onAction(MenuAction.Login) },
                onSignUp = { onAction(MenuAction.SignUp) }
            )

            NotificationsCard(
                count = state.notificationsCount,
                onSeeAll = { onAction(MenuAction.SeeAllNotifications) }
            )

            QuickActions(
                items = listOf(
                    QuickAction("alert", "Price Alert", PriceChangeIcon),
                    QuickAction("converter", "Converter", SwapHorizIcon),
                    QuickAction("diamonds", "Diamonds", DiamondIcon),
                    QuickAction("airdrop", "Airdrop", RocketLaunchIcon)
                ),
                onClick = { onAction(MenuAction.ShortcutClicked(it)) }
            )

            PreferencesCard(
                theme = state.theme,
                onThemeChange = { onAction(MenuAction.ChangeTheme(it)) },
                language = state.language,
                onLanguageClick = { onAction(MenuAction.ChangeLanguage(/* مثلا زبان را در BottomSheet انتخاب کن */ AppLanguage.English)) },
                currency = state.currency,
                onToggleCurrency = { onAction(MenuAction.ToggleCurrency(it)) },
                onManageCurrencies = { onAction(MenuAction.ManageCurrencies) },
                onWidgetsClick = { onAction(MenuAction.WidgetsClicked) }
            )

            SettingsCard(
                onSettings = { onAction(MenuAction.OpenSettings) },
                onHelpCenter = { onAction(MenuAction.OpenHelpCenter) }
            )

            Box(Modifier.padding(horizontal = 2.dp)) {
                SocialRow(
                    items = listOf(
                        Social("language", LanguageIcon),
                        Social("badge", BadgeIcon),
                        Social("close", CloseIcon)
                    ),
                    onClick = { onAction(MenuAction.SocialClicked(it)) }
                )
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}




/* ---------- Pieces ---------- */

@Composable
private fun Header(onLogin: () -> Unit, onSignUp: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        AppIcon(
            imageVector = AccountCircleIcon,
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape),
        )
        Spacer(Modifier.width(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppOutlinedButton(
                onClick = onLogin,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                modifier = Modifier.weight(1f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) { AppText("Log in") }
            AppButton(
                onClick = onSignUp,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                modifier = Modifier.weight(1f)
            ) { AppText("Sign Up") }
        }
    }
}

@Composable
private fun NotificationsCard(count: Int, onSeeAll: () -> Unit) {
    AppElevatedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIcon(NotificationIcon, contentDescription = null)
            Spacer(Modifier.width(10.dp))
            AppText(
                if (count == 0) "No new notifications" else "$count new notifications",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.weight(1f)
            )
            AppText(
                text = "See All",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onSeeAll)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

private data class QuickAction(val id: String, val title: String, val icon: ImageVector)

@Composable
private fun QuickActions(items: List<QuickAction>, onClick: (String) -> Unit) {
    AppElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items.forEach { item ->
                Column(
                    modifier = Modifier
                        .width(72.dp)
                        .clickable { onClick(item.id) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        tonalElevation = 2.dp,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.size(44.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            AppIcon(item.icon, contentDescription = item.title)
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    AppText(item.title, style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center, maxLines = 2)
                }
            }
        }
    }
}

@Composable
private fun PreferencesCard(
    theme: AppThemeOption,
    onThemeChange: (AppThemeOption) -> Unit,
    language: AppLanguage,
    onLanguageClick: () -> Unit,
    currency: AppCurrency,
    onToggleCurrency: (AppCurrency) -> Unit,
    onManageCurrencies: () -> Unit,
    onWidgetsClick: () -> Unit
) {
    AppElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(8.dp)) {

            // THEME
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AppText(
                    text = "Theme",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 5.dp, top = 12.dp, bottom = 8.dp)
                )
                Spacer(Modifier.width(20.dp))

                val themeItems = listOf("Light", "Dark", "System")
                val themeIndex = when (theme) {
                    AppThemeOption.Light -> 0
                    AppThemeOption.Dark -> 1
                    AppThemeOption.System -> 2
                }

                AppPillTabs(
                    items = themeItems,
                    selectedIndex = themeIndex,
                    onSelected = { idx ->
                        onThemeChange(
                            when (idx) {
                                0 -> AppThemeOption.Light
                                1 -> AppThemeOption.Dark
                                else -> AppThemeOption.System
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                )
            }

            AppHorizontalDivider()

            // LANGUAGE
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onLanguageClick)
                    .padding(horizontal = 5.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppText(
                    text = "Language",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                AppText(
                    language.displayName,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(Modifier.width(6.dp))
                AppIcon(ChevronRightIcon, contentDescription = null)
            }

            AppHorizontalDivider()

            // DEFAULT CURRENCIES
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.padding(horizontal = 5.dp, vertical = 8.dp)) {
                    AppText(text = "Default Currency", style = MaterialTheme.typography.titleSmall)
                    Spacer(Modifier.height(5.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(onClick = onManageCurrencies)
                    ) {
                        AppText("Manage", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
                        Spacer(Modifier.width(2.dp))
                        AppIcon(ChevronRightIcon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    }
                }

                Spacer(Modifier.width(20.dp))

                val currencyItems = listOf(AppCurrency.USD, AppCurrency.BTC)
                val labels = currencyItems.map { it.code } // "USD", "BTC"
                val selectedIdx = currencyItems.indexOf(currency).takeIf { it >= 0 } ?: 0

                AppPillTabs(
                    items = labels,
                    selectedIndex = selectedIdx,
                    onSelected = { idx -> onToggleCurrency(currencyItems[idx]) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 8.dp)
                )
            }
        }
    }
}


@Composable
private fun SettingsCard(onSettings: () -> Unit, onHelpCenter: () -> Unit) {
    AppElevatedCard( modifier = Modifier.fillMaxWidth()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onSettings)
                    .padding(horizontal = 10.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppText(
                    "Settings",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                AppIcon(ChevronRightIcon, contentDescription = null)
            }
            AppHorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onHelpCenter)
                    .padding(horizontal = 10.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppText("Help Center", modifier = Modifier.weight(1f),style = MaterialTheme.typography.titleSmall)
                AppIcon(ChevronRightIcon, contentDescription = null)
            }
        }
    }
}

private data class Social(val id: String, val icon: ImageVector)

@Composable
private fun SocialRow(items: List<Social>, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { s ->
            Surface(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .clickable { onClick(s.id) },
                tonalElevation = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    AppIcon(s.icon, contentDescription = s.id)
                }
            }
        }
    }
}






/* ---------- Host usage & Preview ---------- */

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
