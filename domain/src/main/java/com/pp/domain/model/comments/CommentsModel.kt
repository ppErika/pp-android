package com.pp.domain.model.comments

data class CommentModel(
    val id: Int = 0,
    val content: String = "",
    val createDate: String = "",
    val createdUser: CreatedUser = CreatedUser()
)

data class CreatedUser(
    val id: Int = 0,
    val nickname: String = "",
    val profileImageUrl: String = ""
)