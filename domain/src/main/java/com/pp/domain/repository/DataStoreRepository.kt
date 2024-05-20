package com.pp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun setAccessToken(accessToken: String)
    suspend fun getAccessToken(): Flow<String?>
}