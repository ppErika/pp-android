package com.pp.data.datasource.server

import com.pp.domain.model.common.CommonResponse
import com.pp.domain.model.post.GetPostsRequest
import com.pp.domain.model.post.GetPostsResponse
import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.model.token.RevokeTokenRequest
import com.pp.domain.model.users.UserRegisteredResponse
import com.pp.domain.utils.RemoteError

interface PpApiDataSource {
    suspend fun oauthToken(
        remoteError: RemoteError,
        oauthTokenRequest: OauthTokenRequest
    ): OauthTokenResponse?
    suspend fun userRegistered(
        remoteError: RemoteError,
        client: String,
        idToken: String
    ): UserRegisteredResponse?
    suspend fun getPosts(
        remoteError: RemoteError,
        getPostsRequest: GetPostsRequest
    ): GetPostsResponse?
    suspend fun revokeToken(
        remoteError: RemoteError,
        revokeTokenRequest: RevokeTokenRequest
    ): String?
    suspend fun deleteUser(
        remoteError: RemoteError,
        userId: String
    ): CommonResponse?
}