package com.pp.domain.model.post

import java.io.Serializable

data class GetPostsResponse(
    var posts: List<PostModel>
)

data class PostModel(
    var type: String = "",
    var id: Int = 0,
    var thumbnailUrl: String? = "",
    var title: String = "",
    var createDate: String = ""
): Serializable