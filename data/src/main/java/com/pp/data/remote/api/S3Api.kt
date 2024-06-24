package com.pp.data.remote.api

import com.pp.data.model.ApiDataResponse
import com.pp.domain.model.common.CommonResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Url

interface S3Api {
    @PUT
    suspend fun uploadFile(
        @Url url: String,
        @Body file: RequestBody
    ): Response<ApiDataResponse<CommonResponse?>>
}