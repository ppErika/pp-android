package com.pp.domain.usecase.post

import com.pp.domain.model.post.GetPreSignedUrlRequest
import com.pp.domain.model.post.GetPreSignedUrlResponse
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class GetPreSignedUrlUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        getPreSignedUrlRequest: GetPreSignedUrlRequest
    ): GetPreSignedUrlResponse? =
        ppApiRepository.getPreSignedUrl(remoteError, getPreSignedUrlRequest)
}