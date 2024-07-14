package com.pp.domain.repository

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
import com.pp.domain.utils.RemoteError
import okhttp3.RequestBody

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
        getPostsRequest: GetPostsRequest
    ): GetPostsResponse?
    suspend fun getPreSignedUrl(
        remoteError: RemoteError,
        getPreSignedUrlRequest: GetPreSignedUrlRequest
    ): GetPreSignedUrlResponse?
    suspend fun uploadPost(
        remoteError: RemoteError,
        uploadPostRequest: UploadPostRequest
    ): String?
    suspend fun revokeToken(
        remoteError: RemoteError,
        revokeTokenRequest: RevokeTokenRequest
    ): String?
    suspend fun deleteUser(
        remoteError: RemoteError,
        userId: String
    ): CommonResponse?
    suspend fun getComments(
        remoteError: RemoteError,
        getCommentsRequest: GetCommentsRequest
    ): GetCommentsResponse?
    suspend fun postComment(
        remoteError: RemoteError,
        postId: Int,
        postCommentRequest: PostCommentRequest
    ): String?
    suspend fun reportComment(
        remoteError: RemoteError,
        postId: Int
    ): String?
    suspend fun uploadFile(
        remoteError: RemoteError,
        url: String,
        file: RequestBody
    ): String?
    suspend fun getUserProfile(
        remoteError: RemoteError,
        userId: Int
    ): GetUserProfileResponse?
    suspend fun updateUserProfile(
        remoteError: RemoteError,
        userId: Int,
        updateUserProfileResponse: UpdateUserProfileRequest
    ) : String?
    suspend fun getNotices(
        remoteError: RemoteError,
        getNoticesRequest: GetNoticesRequest
    ): GetNoticesResponse?
}