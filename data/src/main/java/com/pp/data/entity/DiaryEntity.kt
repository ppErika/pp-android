package com.pp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pp.domain.model.room.DiaryModel
import java.util.Date

@Entity(tableName = "diary")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
    @ColumnInfo(name = "title") override val title: String,
    @ColumnInfo(name = "contents") override val contents: String,
    @ColumnInfo(name = "images") override val images: List<ByteArray>?,
    @ColumnInfo(name = "create_date") override val createDate: Date = Date()
) : DiaryModel {
    // 기본 생성자
    constructor() : this(0, "", "", emptyList(), Date())
}
