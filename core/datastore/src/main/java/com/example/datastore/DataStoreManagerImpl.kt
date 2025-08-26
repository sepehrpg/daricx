package com.example.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.datastore.model.FakeUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(
    private val userPreferencesDataStore: DataStore<Preferences>,
) : DataStoreManager {

    private val USER_ID = intPreferencesKey("user_id")
    private val USER_FIRST_NAME = stringPreferencesKey("user_first_name")
    private val USER_LAST_NAME = stringPreferencesKey("user_last_name")
    private val USER_BIRTH_DAY = longPreferencesKey("user_birth_day")

    override suspend fun saveUserToPreferencesStore(user: FakeUser) {
        userPreferencesDataStore.edit { preferences ->
            preferences[USER_ID] = user.id
            preferences[USER_FIRST_NAME] = user.firstName
            preferences[USER_LAST_NAME] = user.lastName
            preferences[USER_BIRTH_DAY] = user.birthDay
        }
    }

    override fun getUserFromPreferencesStore(): Flow<FakeUser> = userPreferencesDataStore.data
        .map { preferences ->
            FakeUser(
                id = preferences[USER_ID] ?: 0,
                firstName = preferences[USER_FIRST_NAME] ?: "",
                lastName = preferences[USER_LAST_NAME] ?: "",
                birthDay = preferences[USER_BIRTH_DAY] ?: 0
            )
        }

}
