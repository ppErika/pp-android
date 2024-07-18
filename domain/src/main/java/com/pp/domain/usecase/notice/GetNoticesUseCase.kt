package com.pp.domain.usecase.notice

import com.pp.domain.model.notice.GetNoticesRequest
import com.pp.domain.model.notice.GetNoticesResponse
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject


class GetNoticesUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        getNoticesRequest: GetNoticesRequest
    ): GetNoticesResponse? =
        ppApiRepository.getNotices(remoteError, getNoticesRequest)
}