package com.pp.domain.model.post

data class GetPreSignedUrlRequest (
    var presignedUploadUrlRequests: List<PreSignedUploadUrl> = mutableListOf()
)

data class PreSignedUploadUrl (
    var fileType: String,
    var fileName: String,
    var fileContentLength: Long,
    var fileContentType: String
)

data class PreSignedUploadUrlTemp (
    var fileType: String,
    var fileName: String,
    var fileContentLength: Long,
    var fileContentType: String,
    var filePath: String
)