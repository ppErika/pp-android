package com.pp.domain.usecase.comment

import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class ReportCommentUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        postId: Int
    ): String? =
        ppApiRepository.reportComment(remoteError, postId)
}