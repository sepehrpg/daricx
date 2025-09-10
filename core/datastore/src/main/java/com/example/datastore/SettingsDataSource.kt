package com.example.datastore


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.model.settings.AppCurrency
import com.example.model.settings.AppLanguage
import com.example.model.settings.AppSettings
import com.example.model.settings.AppThemeOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



internal object SettingsKeys {
    val THEME = stringPreferencesKey("theme")
    val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
    val LANGUAGE = stringPreferencesKey("language_code")
    val CURRENCY = stringPreferencesKey("currency_code")
    val FORCE_RTL = booleanPreferencesKey("force_rtl")
}


class SettingsDataSource(
    private val dataStore: DataStore<Preferences>
) {
    val stream: Flow<AppSettings> = dataStore.data.map { p ->
        AppSettings(
            theme = p[SettingsKeys.THEME]?.let { runCatching { AppThemeOption.valueOf(it) }.getOrNull() }
                ?: AppThemeOption.System,
            dynamicColor = p[SettingsKeys.DYNAMIC_COLOR] ?: true,
            language = AppLanguage.from(p[SettingsKeys.LANGUAGE]),
            currency = AppCurrency.from(p[SettingsKeys.CURRENCY]),
            forceRtl = p[SettingsKeys.FORCE_RTL] ?: false
        )
    }

    suspend fun setTheme(value: AppThemeOption){
        dataStore.edit { it[SettingsKeys.THEME] = value.name }
    }
    suspend fun setDynamicColor(value: Boolean){
        dataStore.edit { it[SettingsKeys.DYNAMIC_COLOR] = value }
    }
    suspend fun setLanguage(value: AppLanguage){
        dataStore.edit { it[SettingsKeys.LANGUAGE] = value.code }
    }
    suspend fun setCurrency(value: AppCurrency){
        dataStore.edit { it[SettingsKeys.CURRENCY] = value.code }
    }
    suspend fun setForceRtl(value: Boolean){
        dataStore.edit { it[SettingsKeys.FORCE_RTL] = value }
    }
}