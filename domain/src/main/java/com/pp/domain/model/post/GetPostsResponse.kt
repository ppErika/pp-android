package com.pp.domain.model.post

data class GetPostsResponse(
    var posts: List<PostModel>
)

data class PostModel(
    var id: Int = 0,
    var thumbnailUrl: String = "",
    var title: String = "",
    var createDate: String = ""
)