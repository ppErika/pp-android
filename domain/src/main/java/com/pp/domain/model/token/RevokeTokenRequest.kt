package com.pp.domain.model.token

data class RevokeTokenRequest(
    var client_id: String = "kauth.kakao.com",
    var token: String = "",
    var token_type_hint: String = "access_token"
)