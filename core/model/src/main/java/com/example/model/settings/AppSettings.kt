package com.example.model.settings


data class AppSettings(
    val theme: AppThemeOption = AppThemeOption.Light,
    val dynamicColor: Boolean = false,
    val language: AppLanguage = AppLanguage.English,
    val currency: AppCurrency = AppCurrency.USD,
    val forceRtl: Boolean = false
)