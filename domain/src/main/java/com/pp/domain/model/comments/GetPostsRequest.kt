package com.pp.domain.model.comments

data class GetCommentsRequest(
    var postId: Int = 0,
    var limit: Int = 20,
    var lastId: Int? = null
)