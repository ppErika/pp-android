package com.pp.domain.model.post

data class GetPostsRequest(
    var limit: Int = 20,
    var lastId: Int? = null
)