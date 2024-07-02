package com.pp.domain.model.users

data class UpdateUserProfileRequest(
    var nickname: String? = null,
    var profileImageFileUploadId: String? = null
)