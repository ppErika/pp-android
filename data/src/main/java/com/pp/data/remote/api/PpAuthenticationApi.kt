package com.pp.data.remote.api

import com.pp.data.model.ApiDataResponse
import com.pp.domain.model.post.GetPostsResponse
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.model.users.UserRegisteredResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PpAuthenticationApi {

    @GET("/api/v1/posts")
    suspend fun getPosts(
        @Query("limit") limit: Int,
        @Query("lastId") lastId: Int?
    ): Response<ApiDataResponse<GetPostsResponse>>
}