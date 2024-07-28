package com.pp.domain.usecase.users

import com.pp.domain.model.users.UserRegisteredResponse
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class UserRegisteredUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        client: String,
        idToken: String
    ): UserRegisteredResponse? =
        ppApiRepository.userRegistered(remoteError, client, idToken)
}