package com.pp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

@Entity(tableName = "diary")
data class Diary(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "contents") val contents: String,
    @ColumnInfo(name = "create_date") val createDate: String,
    @ColumnInfo(name = "image_url") val imageUrl: Text?
)
