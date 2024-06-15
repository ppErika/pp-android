package com.pp.domain.usecase.users

import com.pp.domain.model.common.CommonResponse
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        userId: String
    ): CommonResponse? =
        ppApiRepository.deleteUser(remoteError, userId)
}