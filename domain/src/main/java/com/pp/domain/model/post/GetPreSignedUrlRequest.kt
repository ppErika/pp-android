package com.pp.domain.model.post

data class GetPreSignedUrlRequest (
    val presignedUploadUrlRequests: List<PresignedUploadUrl> = mutableListOf()
)

data class PresignedUploadUrl (
    val fileType: String,
    val fileName: String,
    val fileContentLength: Int,
    val fileContentType: String
)