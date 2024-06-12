package com.pp.domain.model.token

data class OauthTokenResponse(
    var access_token: String = "",
    var refresh_token: String = "",
    var scope: String = "",
    var token_type: String = "",
    var expires_in: Int = 0
)