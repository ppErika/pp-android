package com.pp.domain.usecase.post

import com.pp.domain.model.common.CommonResponse
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import okhttp3.RequestBody
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        url: String,
        file: RequestBody
    ): String? =
        ppApiRepository.uploadFile(remoteError, url, file)
}