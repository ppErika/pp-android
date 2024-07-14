package com.pp.data.remote.api

import com.pp.data.model.ApiDataResponse
import com.pp.domain.model.common.CommonResponse
import com.pp.domain.model.notice.GetNoticesResponse
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.model.users.UserRegisteredResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PpApi {
    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun oauthToken(
        @Field("grant_type") grant_type: String,
        @Field("client_id") client_id: String,
        @Field("client_assertion") client_assertion: String,
        @Field("client_assertion_type") client_assertion_type: String,
        @Field("authorization_code") authorization_code: String?,
        @Field("scope") scope: String,
        @Field("refresh_token") refresh_token: String?,
    ): Response<OauthTokenResponse>

    @FormUrlEncoded
    @POST("api/v1/oauth2/{client}/users/registered")
    suspend fun userRegistered(
        @Path("client") client: String,
        @Field("idToken") idToken: String
    ) : Response<UserRegisteredResponse>

    @FormUrlEncoded
    @POST("oauth2/revoke")
    suspend fun revokeToken(
        @Field("client_id") client_id: String,
        @Field("token") token: String,
        @Field("token_type_hint") token_type_hint: String
    ) : Response<String?>

    @DELETE("/api/v1/users/{userId}")
    suspend fun deleteUser(
        @Path("userId") client: String
    ) : Response<CommonResponse>

    @GET("/api/v1/notices")
    suspend fun getNotices(
        @Query("limit") limit: Int,
        @Query("lastId") lastId: Int?
    ) : Response<ApiDataResponse<GetNoticesResponse>>

}