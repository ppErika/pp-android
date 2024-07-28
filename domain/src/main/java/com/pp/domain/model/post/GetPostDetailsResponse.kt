package com.pp.domain.model.post

data class GetPostDetailsResponse(
    var id: Int = 0,
    var postImageUrls: List<String>? = mutableListOf(),
    var title: String = "",
    var content: String = "",
    var createdDate: String = "",
    var thumbsUpCount: Int = 0,
    var commentCount: Int = 0,
    var userActionHistory: UserActionHistoryModel = UserActionHistoryModel(),
    var createdUser: CreatedUserModel = CreatedUserModel(),
)

data class UserActionHistoryModel(
    var thumbsUpped: Boolean = false,
    var reported: Boolean = false,
)

data class CreatedUserModel(
    var id: Int = 0,
    var nickname: String = "",
    var profileImageUrl: String = "",
)