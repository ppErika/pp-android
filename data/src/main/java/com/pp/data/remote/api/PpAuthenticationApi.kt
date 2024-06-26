package com.pp.data.remote.api

import com.pp.data.model.ApiDataResponse
import com.pp.domain.model.comments.GetCommentsResponse
import com.pp.domain.model.comments.PostCommentRequest
import com.pp.domain.model.common.CommonResponse
import com.pp.domain.model.post.GetPostsResponse
import com.pp.domain.model.post.GetPreSignedUrlRequest
import com.pp.domain.model.post.GetPreSignedUrlResponse
import com.pp.domain.model.post.UploadPostRequest
import com.pp.domain.model.users.GetUserProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PpAuthenticationApi {

    @GET("api/v1/posts")
    suspend fun getPosts(
        @Query("limit") limit: Int,
        @Query("lastId") lastId: Int?
    ): Response<ApiDataResponse<GetPostsResponse>>
    @POST("api/v1/presigned-urls/upload")
    suspend fun getPreSignedUrl(
        @Body getPreSignedUrlRequest: GetPreSignedUrlRequest
    ): Response<ApiDataResponse<GetPreSignedUrlResponse>>
    @POST("api/v1/posts")
    suspend fun uploadPost(
        @Body uploadPostRequest: UploadPostRequest
    ): Response<ApiDataResponse<CommonResponse?>>
    @GET("api/v1/posts/{postId}/comments")
    suspend fun getComments(
        @Path("postId") postId: Int,
        @Query("limit") limit: Int,
        @Query("lastId") lastId: Int?
    ): Response<ApiDataResponse<GetCommentsResponse>>
    @POST("api/v1/posts/{postId}/comments")
    suspend fun postComment(
        @Path("postId") postId: Int,
        @Body content: PostCommentRequest
    ): Response<ApiDataResponse<CommonResponse?>>
    @POST("api/v1/comments/{commentId}/report")
    suspend fun reportComment(
        @Path("commentId") commentId: Int
    ): Response<ApiDataResponse<CommonResponse?>>
    @GET("api/v1/users/{userId}/profiles")
    suspend fun getUserProfile(
        @Path("userId") userId: Int
    ): Response<ApiDataResponse<GetUserProfileResponse?>>
}