package com.pp.domain.model.post

data class UploadPostRequest(
    var title: String = "",
    var content: String = "",
    var postImageFileUploadIds: List<String>? = null
)