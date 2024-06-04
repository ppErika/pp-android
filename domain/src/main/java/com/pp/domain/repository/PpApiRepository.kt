package com.pp.domain.repository

import com.pp.domain.model.post.GetPostsRequest
import com.pp.domain.model.post.GetPostsResponse
import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.model.users.UserRegisteredResponse
import com.pp.domain.utils.RemoteError

interface PpApiRepository {
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
        accessToken: String,
        getPostsRequest: GetPostsRequest
    ): GetPostsResponse?
}