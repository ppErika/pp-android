package com.pp.community.widget

import com.pp.data.remote.api.PpRefreshApi
import com.pp.domain.usecase.datastore.GetAccessTokenUseCase
import com.pp.domain.usecase.datastore.GetRefreshTokenUseCase
import com.pp.domain.usecase.datastore.SetAccessTokenUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getRefreshTokenUseCase: GetRefreshTokenUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase,
    private val pPRefreshApi: PpRefreshApi
) : Authenticator {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentToken = runBlocking {
            getAccessTokenUseCase.invoke().first()
        }
        synchronized(this) {
            val updatedToken = runBlocking {
                getAccessTokenUseCase.invoke().first()
            }
            val token = if (currentToken != updatedToken) updatedToken else {
                val newSessionResponse = runBlocking {
                    pPRefreshApi.refreshToken(
                        refresh_token = getRefreshTokenUseCase.invoke().first() ?: ""
                    )
                }
                if (newSessionResponse.isSuccessful && newSessionResponse.body() != null) {
                    newSessionResponse.body()?.let { body ->
                        runBlocking {
                            setAccessTokenUseCase.invoke(body)
                        }
                        body.access_token
                    }
                } else null
            }
            return if (token != null) response.request.newBuilder()
                .header(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
                .build() else null
        }
    }
}