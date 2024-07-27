package com.pp.domain.usecase.users

import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class BlockUserUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        userId: Int
    ): String? =
        ppApiRepository.blockUser(remoteError, userId)
}