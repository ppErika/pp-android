package com.pp.domain.model.post

import java.io.Serializable

data class GetPostDetailsResponse(
    var post: PostDetailsModel
)

data class PostDetailsModel(
    var id: Int = 0,
    var postImageUrls: List<String>? = mutableListOf(),
    var title: String = "",
    var content: String = "",
    var createDate: String = "",
    var thumbsUpCount: Int = 0,
    var commentCount: Int = 0,
    var userActionHistory: UserActionHistoryModel,
    var createdUser: CreatedUserModel,
):Serializable

data class UserActionHistoryModel(
    var thumbsUpped: Boolean = false,
    var reported: Boolean = false,
)

data class CreatedUserModel(
    var id: Int = 0,
    var nickname: String = "",
    var profileImageUrl: String = "",
)