package com.pp.domain.usecase.post

import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class ReportPostUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        postId: Int
    ): String? =
        ppApiRepository.reportPost(remoteError, postId)
}