package com.pp.data.datasource.server

import com.pp.data.base.BaseRepository
import com.pp.data.remote.api.PpApi
import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class PpApiDataSourceImpl @Inject constructor(
    private val ppApi: PpApi

) : BaseRepository(), PpApiDataSource {
    override suspend fun oauthToken(
        remoteError: RemoteError,
        oauthTokenRequest: OauthTokenRequest
    ): OauthTokenResponse? {
        return safeApiCall(remoteError) { ppApi.oauthToken(oauthTokenRequest) }
    }

}