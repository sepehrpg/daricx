package com.daricx.settings.menu

import com.example.model.settings.AppCurrency
import com.example.model.settings.AppLanguage
import com.example.model.settings.AppThemeOption



sealed interface MenuAction {
    data object Login : MenuAction
    data object SignUp : MenuAction
    data object SeeAllNotifications : MenuAction

    data class ChangeTheme(val theme: AppThemeOption) : MenuAction
    data class ChangeLanguage(val language: AppLanguage) : MenuAction

    data class ToggleCurrency(val currency: AppCurrency) : MenuAction
    data object ManageCurrencies : MenuAction
    data object WidgetsClicked : MenuAction

    data object OpenSettings : MenuAction
    data object OpenHelpCenter : MenuAction

    data class ShortcutClicked(val id: String) : MenuAction
    data class SocialClicked(val id: String) : MenuAction
}