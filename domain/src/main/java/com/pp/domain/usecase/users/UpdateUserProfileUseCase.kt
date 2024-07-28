package com.pp.domain.usecase.users

import com.pp.domain.model.users.UpdateUserProfileRequest
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        userId: Int,
        updateUserProfileRequest: UpdateUserProfileRequest
    ): String? =
        ppApiRepository.updateUserProfile(remoteError, userId, updateUserProfileRequest)
}