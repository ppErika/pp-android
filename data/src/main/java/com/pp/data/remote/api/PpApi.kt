package com.pp.data.remote.api

import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.model.token.OauthTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PpApi {
    @POST("/oauth2/token")
    suspend fun oauthToken(
        @Body param: OauthTokenRequest
    ): Response<OauthTokenResponse>

}