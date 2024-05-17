package com.pp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pp.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStorePreferences: DataStore<Preferences>
) : DataStoreRepository {
    private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")

    override suspend fun setAccessToken(accessToken: String) {
        dataStorePreferences.edit {
            it[KEY_ACCESS_TOKEN] = accessToken
        }
    }

    override suspend fun getAccessToken(): Flow<String?> = dataStorePreferences.data.map {
        it[KEY_ACCESS_TOKEN]
    }
}