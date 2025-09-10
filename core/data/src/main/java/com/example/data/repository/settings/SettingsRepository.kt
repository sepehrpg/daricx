package com.example.data.repository.settings

import com.example.model.settings.AppCurrency
import com.example.model.settings.AppLanguage
import com.example.model.settings.AppSettings
import com.example.model.settings.AppThemeOption
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val settings: Flow<AppSettings>
    suspend fun setTheme(theme: AppThemeOption)
    suspend fun setDynamicColor(enabled: Boolean)
    suspend fun setLanguage(lang: AppLanguage)
    suspend fun setCurrency(cur: AppCurrency)
    suspend fun setForceRtl(force: Boolean)
}