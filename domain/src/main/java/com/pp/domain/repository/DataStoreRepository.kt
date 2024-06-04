package com.pp.domain.repository

import com.pp.domain.model.token.OauthTokenResponse
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun setAccessToken(oauthTokenResponse: OauthTokenResponse)
    suspend fun getAccessToken(): Flow<String?>
    suspend fun getRefreshToken(): Flow<String?>
}