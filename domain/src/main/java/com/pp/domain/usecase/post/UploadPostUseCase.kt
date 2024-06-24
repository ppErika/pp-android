package com.pp.domain.usecase.post

import com.pp.domain.model.post.UploadPostRequest
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class UploadPostUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        uploadPostRequest: UploadPostRequest
    ): String? =
        ppApiRepository.uploadPost(remoteError, uploadPostRequest)
}