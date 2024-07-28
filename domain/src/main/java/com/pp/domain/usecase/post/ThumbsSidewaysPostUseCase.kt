package com.pp.domain.usecase.post

import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class ThumbsSidewaysPostUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        postId: Int
    ): String? =
        ppApiRepository.thumbsSidewaysPost(remoteError, postId)
}