package com.pp.data.datasource.server

import com.pp.data.base.BaseRepository
import com.pp.data.remote.api.PpApi
import com.pp.domain.model.post.GetPostsRequest
import com.pp.domain.model.post.GetPostsResponse
import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.model.users.UserRegisteredResponse
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class PpApiDataSourceImpl @Inject constructor(
    private val ppApi: PpApi

) : BaseRepository(), PpApiDataSource {
    override suspend fun oauthToken(
        remoteError: RemoteError,
        oauthTokenRequest: OauthTokenRequest
    ): OauthTokenResponse? {
        return safeApiCall(remoteError) {
            ppApi.oauthToken(
                grant_type = oauthTokenRequest.grant_type,
                client_id = oauthTokenRequest.client_id,
                client_assertion = oauthTokenRequest.client_assertion,
                client_assertion_type = oauthTokenRequest.client_assertion_type,
                authorization_code = oauthTokenRequest.authorization_code,
                scope = oauthTokenRequest.scope,
                refresh_token = oauthTokenRequest.refresh_token,
            )
        }
    }

    override suspend fun userRegistered(
        remoteError: RemoteError,
        client: String,
        idToken: String
    ): UserRegisteredResponse? {
        return safeApiCall(remoteError){
            ppApi.userRegistered(
                client = client,
                idToken = idToken
            )
        }
    }

    override suspend fun getPosts(
        remoteError: RemoteError,
        accessToken: String,
        getPostsRequest: GetPostsRequest
    ): GetPostsResponse? {
        return safeApiCallData(remoteError) {
            ppApi.getPosts(
                access_token = accessToken,
                lastId = getPostsRequest.lastId,
                limit = getPostsRequest.limit
            )
        }
    }

}