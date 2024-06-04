package com.pp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStorePreferences: DataStore<Preferences>
) : DataStoreRepository {
    private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")

    override suspend fun setAccessToken(oauthTokenResponse: OauthTokenResponse) {
        dataStorePreferences.edit {
            it[KEY_ACCESS_TOKEN] = oauthTokenResponse.access_token
            it[KEY_REFRESH_TOKEN] = oauthTokenResponse.refresh_token
        }
    }

    override suspend fun getAccessToken(): Flow<String?> = dataStorePreferences.data.map {
        it[KEY_ACCESS_TOKEN]
    }

    override suspend fun getRefreshToken(): Flow<String?> = dataStorePreferences.data.map {
        it[KEY_REFRESH_TOKEN]
    }
}