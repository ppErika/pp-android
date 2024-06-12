package com.pp.domain.model.room

data class DiaryRequest(
    val id: Int = 0,
    val title: String,
    val contents: String,
    val createDate: String,
    val imageData: ByteArray?
)