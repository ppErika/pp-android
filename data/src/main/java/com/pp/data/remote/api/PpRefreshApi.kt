package com.pp.data.remote.api

import com.pp.domain.model.token.OauthTokenResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PpRefreshApi {
    @FormUrlEncoded
    @POST("/oauth2/token")
    suspend fun refreshToken(
        @Field("client_id") client_id: String = "kauth.kakao.com",
        @Field("grant_type") grant_type: String = "refresh_token",
        @Field("refresh_token") refresh_token: String
    ): Response<OauthTokenResponse>
}