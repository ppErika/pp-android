package com.pp.domain.model.users

data class UserRegisteredResponse(
    val data : UserRegisteredModel = UserRegisteredModel()
)

data class UserRegisteredModel(
    val isRegistered: Boolean = false
)