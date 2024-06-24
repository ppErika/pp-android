package com.pp.domain.usecase.comment

import com.pp.domain.model.comments.GetCommentsRequest
import com.pp.domain.model.comments.GetCommentsResponse
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        getCommentsRequest: GetCommentsRequest
    ): GetCommentsResponse? =
        ppApiRepository.getComments(remoteError, getCommentsRequest)
}