package com.example.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single


private const val PREFERENCES_FILE_NAME = "app_preferences"

/**
 * Provides the Preferences DataStore used by DataStoreManager and SettingsDataSource.
 */
@Module
class PreferencesDataStoreModule {

    @Single
    fun providePreferencesDataStore(
        context: Context,
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile(PREFERENCES_FILE_NAME) }
    )
}
