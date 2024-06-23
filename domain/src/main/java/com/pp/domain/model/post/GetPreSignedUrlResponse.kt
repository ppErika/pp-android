package com.pp.domain.model.post

data class GetPreSignedUrlResponse (
    val presignedUploadFiles: List<PresignedUploadResponseData> = mutableListOf()
)

data class PresignedUploadResponseData (
    val fileUploadId: String,
    val fileName: String,
    val presignedUploadUrl: String,
    val fileUrl: String
)