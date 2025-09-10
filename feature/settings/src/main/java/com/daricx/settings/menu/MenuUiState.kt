package com.daricx.settings.menu

import com.example.model.settings.AppCurrency
import com.example.model.settings.AppLanguage
import com.example.model.settings.AppThemeOption

data class MenuUiState(
    val theme: AppThemeOption = AppThemeOption.System,
    val language: AppLanguage = AppLanguage.English,
    val currency: AppCurrency = AppCurrency.USD,
    val notificationsCount: Int = 0
)