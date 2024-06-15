package com.pp.data.remote.api

import com.pp.data.model.ApiDataResponse
import com.pp.domain.model.comments.GetCommentsResponse
import com.pp.domain.model.post.GetPostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PpAuthenticationApi {

    @GET("api/v1/posts")
    suspend fun getPosts(
        @Query("limit") limit: Int,
        @Query("lastId") lastId: Int?
    ): Response<ApiDataResponse<GetPostsResponse>>
    @GET("api/v1/posts/{postId}/comments")
    suspend fun getComments(
        @Path("postId") postId: Int,
        @Query("limit") limit: Int,
        @Query("lastId") lastId: Int?
    ): Response<ApiDataResponse<GetCommentsResponse>>
}