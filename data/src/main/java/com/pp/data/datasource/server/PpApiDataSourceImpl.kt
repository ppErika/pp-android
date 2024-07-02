package com.pp.data.datasource.server

import com.pp.data.base.BaseRepository
import com.pp.data.remote.api.PpApi
import com.pp.data.remote.api.PpAuthenticationApi
import com.pp.data.remote.api.S3Api
import com.pp.domain.model.comments.GetCommentsRequest
import com.pp.domain.model.comments.GetCommentsResponse
import com.pp.domain.model.comments.PostCommentRequest
import com.pp.domain.model.common.CommonResponse
import com.pp.domain.model.post.GetPostsRequest
import com.pp.domain.model.post.GetPostsResponse
import com.pp.domain.model.post.GetPreSignedUrlRequest
import com.pp.domain.model.post.GetPreSignedUrlResponse
import com.pp.domain.model.post.UploadPostRequest
import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.model.token.RevokeTokenRequest
import com.pp.domain.model.users.GetUserProfileResponse
import com.pp.domain.model.users.UpdateUserProfileRequest
import com.pp.domain.model.users.UserRegisteredResponse
import com.pp.domain.utils.RemoteError
import okhttp3.RequestBody
import javax.inject.Inject

class PpApiDataSourceImpl @Inject constructor(
    private val ppAuthenticationApi: PpAuthenticationApi,
    private val ppApi: PpApi,
    private val s3Api: S3Api
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

    override suspend fun revokeToken(
        remoteError: RemoteError,
        revokeTokenRequest: RevokeTokenRequest
    ): String? {
        return safeApiCallNoContext(remoteError) {
            ppApi.revokeToken(
                client_id = revokeTokenRequest.client_id,
                token = revokeTokenRequest.token,
                token_type_hint = revokeTokenRequest.token_type_hint
            )
        }
    }

    override suspend fun deleteUser(remoteError: RemoteError, userId: String): CommonResponse? {
        return safeApiCall(remoteError) {
            ppApi.deleteUser(userId)
        }
    }
    override suspend fun getPosts(
        remoteError: RemoteError,
        getPostsRequest: GetPostsRequest
    ): GetPostsResponse? {
        return safeApiCallData(remoteError) {
            ppAuthenticationApi.getPosts(
                lastId = getPostsRequest.lastId,
                limit = getPostsRequest.limit
            )
        }
    }
    override suspend fun getPreSignedUrl(
        remoteError: RemoteError,
        getPreSignedUrlRequest: GetPreSignedUrlRequest
    ): GetPreSignedUrlResponse? {
        return safeApiCallData(remoteError){
            ppAuthenticationApi.getPreSignedUrl(
                getPreSignedUrlRequest
            )
        }
    }

    override suspend fun uploadPost(
        remoteError: RemoteError,
        uploadPostRequest: UploadPostRequest
    ): String? {
        return safeApiCallNoContext(remoteError) {
            ppAuthenticationApi.uploadPost(
                uploadPostRequest
            )
        }
    }

    override suspend fun getComments(
        remoteError: RemoteError,
        getCommentsRequest: GetCommentsRequest
    ): GetCommentsResponse? {
        return safeApiCallData(remoteError) {
            ppAuthenticationApi.getComments(
                postId = getCommentsRequest.postId,
                lastId = getCommentsRequest.lastId,
                limit = getCommentsRequest.limit
            )
        }
    }

    override suspend fun postComment(
        remoteError: RemoteError,
        postId: Int,
        postCommentRequest: PostCommentRequest
    ): String? {
        return safeApiCallNoContext(remoteError) {
            ppAuthenticationApi.postComment(
                postId = postId,
                content = postCommentRequest
            )
        }
    }

    override suspend fun reportComment(remoteError: RemoteError, commentId: Int): String? {
        return safeApiCallNoContext(remoteError) {
            ppAuthenticationApi.reportComment(
                commentId = commentId
            )
        }
    }

    override suspend fun uploadFile(remoteError: RemoteError, url: String, file: RequestBody): String? {
        return safeApiCallNoContext(remoteError) {
            s3Api.uploadFile(url,file)
        }
    }

    override suspend fun getUserProfile(
        remoteError: RemoteError,
        userId: Int
    ): GetUserProfileResponse? {
        return safeApiCallData(remoteError){
            ppAuthenticationApi.getUserProfile(
                userId = userId
            )
        }
    }

    override suspend fun updateUserProfile(
        remoteError: RemoteError,
        userId: Int,
        updateUserProfileRequest: UpdateUserProfileRequest
    ): String? {
        return safeApiCallNoContext(remoteError) {
            ppAuthenticationApi.updateUserProfile(
                userId = userId,
                updateUserProfileRequest = updateUserProfileRequest
            )
        }
    }
}