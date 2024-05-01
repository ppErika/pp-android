package com.pp.domain.model.token

data class OauthTokenRequest(
    var grant_type: String = "",
    var client_id: String = "",
    var client_assertion: String = "",
    var client_assertion_type: String = "urn:ietf:params:oauth:client-assertion-type:jwt-bearer",
    var authorization_code: String = "",
    var scope: String = "user.read user.write post.read post.write file.write",
    var refresh_token: String = "",
)