package com.example.datastore

import com.example.datastore.model.FakeUser
import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun saveUserToPreferencesStore(user: FakeUser)
    fun getUserFromPreferencesStore(): Flow<FakeUser>
}
