package com.pp.domain.model.users

import com.pp.domain.model.post.PostModel
import java.io.Serializable

data class GetUserProfileResponse(
    val id: Int = 0,
    val nickname: String = "",
    val profileImageUrl: String = "",
    val postCount: Int = 0,
    val thumbsUpCount: Int = 0,
    val posts: List<PostModel> = mutableListOf()
) : Serializable