package com.pp.domain.usecase.users

import com.pp.domain.model.users.GetUserProfileResponse
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        userId: Int
    ): GetUserProfileResponse? =
        ppApiRepository.getUserProfile(remoteError, userId)
}