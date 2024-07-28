package com.pp.domain.usecase.token

import com.pp.domain.model.common.CommonResponse
import com.pp.domain.model.token.RevokeTokenRequest
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class RevokeTokenUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        revokeTokenRequest: RevokeTokenRequest
    ): String? =
        ppApiRepository.revokeToken(remoteError, revokeTokenRequest)
}