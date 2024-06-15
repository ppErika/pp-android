package com.pp.data.repository

import com.pp.data.datasource.server.PpApiDataSource
import com.pp.domain.model.comments.GetCommentsRequest
import com.pp.domain.model.comments.GetCommentsResponse
import com.pp.domain.model.common.CommonResponse
import com.pp.domain.model.post.GetPostsRequest
import com.pp.domain.model.post.GetPostsResponse
import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.model.token.RevokeTokenRequest
import com.pp.domain.model.users.UserRegisteredResponse
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

    override suspend fun userRegistered(
        remoteError: RemoteError,
        client: String,
        idToken: String
    ): UserRegisteredResponse? {
        return ppApiDataSource.userRegistered(remoteError, client, idToken)
    }

    override suspend fun getPosts(
        remoteError: RemoteError,
        getPostsRequest: GetPostsRequest
    ): GetPostsResponse? {
        return ppApiDataSource.getPosts(remoteError, getPostsRequest)
    }

    override suspend fun revokeToken(
        remoteError: RemoteError,
        revokeTokenRequest: RevokeTokenRequest
    ): String? {
        return ppApiDataSource.revokeToken(remoteError, revokeTokenRequest)
    }

    override suspend fun deleteUser(remoteError: RemoteError, userId: String): CommonResponse? {
        return ppApiDataSource.deleteUser(remoteError, userId)
    }

    override suspend fun getComments(
        remoteError: RemoteError,
        getCommentsRequest: GetCommentsRequest
    ): GetCommentsResponse? {
        return ppApiDataSource.getComments(remoteError, getCommentsRequest)
    }
}