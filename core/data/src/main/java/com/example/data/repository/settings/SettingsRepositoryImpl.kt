package com.example.data.repository.settings


import com.example.datastore.SettingsDataSource
import com.example.model.settings.AppCurrency
import com.example.model.settings.AppLanguage
import com.example.model.settings.AppSettings
import com.example.model.settings.AppThemeOption
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val ds: SettingsDataSource
) : SettingsRepository {
    override val settings: Flow<AppSettings> = ds.stream
    override suspend fun setTheme(theme: AppThemeOption) = ds.setTheme(theme)
    override suspend fun setDynamicColor(enabled: Boolean) = ds.setDynamicColor(enabled)
    override suspend fun setLanguage(lang: AppLanguage) = ds.setLanguage(lang)
    override suspend fun setCurrency(cur: AppCurrency) = ds.setCurrency(cur)
    override suspend fun setForceRtl(force: Boolean) = ds.setForceRtl(force)
}