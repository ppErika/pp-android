package com.pp.community.utils

import android.util.Base64
import org.json.JSONObject
import javax.inject.Inject

class DecodeJwtUtil @Inject constructor() {
    fun getUserId(token: String): String {
        val tokenParts = token.split(".")

        if (tokenParts.size < 2) {
            throw IllegalArgumentException("Invalid token: $token")
        }

        val payload = String(Base64.decode(tokenParts[1], Base64.URL_SAFE))
        val userId = JSONObject(payload).getString("sub")
        return userId
    }
}
