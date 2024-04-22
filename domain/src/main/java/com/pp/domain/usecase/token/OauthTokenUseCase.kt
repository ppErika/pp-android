package com.pp.domain.usecase.token

import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.model.token.OauthTokenResponse
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class OauthTokenUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        oauthTokenRequest: OauthTokenRequest
    ): OauthTokenResponse? =
        ppApiRepository.oauthToken(remoteError, oauthTokenRequest)
}