package com.pp.data.repository

import com.pp.data.datasource.server.PpApiDataSource
import com.pp.domain.model.comments.GetCommentsRequest
import com.pp.domain.model.comments.GetCommentsResponse
import com.pp.domain.model.comments.PostCommentRequest
import com.pp.domain.model.common.CommonResponse
import com.pp.domain.model.notice.GetNoticesRequest
import com.pp.domain.model.notice.GetNoticesResponse
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
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import okhttp3.RequestBody
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

    override suspend fun getPreSignedUrl(
        remoteError: RemoteError,
        getPreSignedUrlRequest: GetPreSignedUrlRequest
    ): GetPreSignedUrlResponse? {
        return ppApiDataSource.getPreSignedUrl(remoteError, getPreSignedUrlRequest)
    }

    override suspend fun uploadPost(
        remoteError: RemoteError,
        uploadPostRequest: UploadPostRequest
    ): String? {
        return ppApiDataSource.uploadPost(remoteError, uploadPostRequest)
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

    override suspend fun postComment(
        remoteError: RemoteError,
        postId: Int,
        postCommentRequest: PostCommentRequest
    ): String? {
        return ppApiDataSource.postComment(remoteError, postId, postCommentRequest)
    }

    override suspend fun reportComment(remoteError: RemoteError, postId: Int): String? {
        return ppApiDataSource.reportComment(remoteError, postId)
    }

    override suspend fun uploadFile(
        remoteError: RemoteError,
        url: String,
        file: RequestBody
    ): String? {
        return ppApiDataSource.uploadFile(remoteError, url, file)
    }

    override suspend fun getUserProfile(
        remoteError: RemoteError,
        userId: Int
    ): GetUserProfileResponse? {
        return ppApiDataSource.getUserProfile(remoteError, userId)
    }

    override suspend fun updateUserProfile(
        remoteError: RemoteError,
        userId: Int,
        updateUserProfileResponse: UpdateUserProfileRequest
    ): String? {
        return ppApiDataSource.updateUserProfile(remoteError, userId, updateUserProfileResponse)
    }

    override suspend fun getNotices(
        remoteError: RemoteError,
        getNoticesRequest: GetNoticesRequest
    ): GetNoticesResponse? {
        return ppApiDataSource.getNotices(remoteError, getNoticesRequest)
    }
}