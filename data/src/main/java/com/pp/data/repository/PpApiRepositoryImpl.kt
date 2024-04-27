package com.pp.data.repository

import com.pp.data.datasource.server.PpApiDataSource
import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class PpApiRepositoryImpl @Inject constructor(
    private val ppApiDataSource: PpApiDataSource
) : PpApiRepository {
    override suspend fun oauthToken(
        remoteError: RemoteError,
        oauthTokenRequest: OauthTokenRequest
    ): OauthTokenResponse? {
        return ppApiDataSource.oauthToken(remoteError, oauthTokenRequest)
    }
}