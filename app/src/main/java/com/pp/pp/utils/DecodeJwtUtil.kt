package com.pp.pp.utils

import android.util.Base64
import org.json.JSONObject

object DecodeJwtUtil {
    fun getUserId(token: String): String {
        val tokenParts = token.split(".")
        val payload = String(Base64.decode(tokenParts[1], Base64.URL_SAFE))
        val userId = JSONObject(payload).getString("sub")
        return userId
    }
}