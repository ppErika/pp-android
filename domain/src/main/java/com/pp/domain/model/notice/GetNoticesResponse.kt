package com.pp.domain.model.notice

data class GetNoticesResponse(
    val notices: List<Notice> = mutableListOf()
)

data class Notice(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val createDate: String = ""
)