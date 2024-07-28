package com.pp.domain.model.notice

data class GetNoticesRequest(
    var limit: Int = 20,
    var lastId: Int? = null
)