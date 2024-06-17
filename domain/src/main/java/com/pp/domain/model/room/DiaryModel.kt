package com.pp.domain.model.room

import java.util.Date

interface DiaryModel {
    val id: Int
    val title: String
    val contents: String
    val images: List<ByteArray>?
    val createDate: Date
}