package com.example.datastore.di


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.example.datastore.SettingsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val SETTINGS_FILE = "app_settings.preferences_pb"

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

/*    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile(SETTINGS_FILE) }
    )*/

    @Provides
    @Singleton
    fun provideSettingsDataSource(
        dataStore: DataStore<Preferences>
    ): SettingsDataSource = SettingsDataSource(dataStore)
}
