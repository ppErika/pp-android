package com.pp.data.utils

import com.pp.domain.model.room.DiaryModel
import com.pp.data.entity.DiaryEntity

fun DiaryModel.toEntity(): DiaryEntity {
    return DiaryEntity(
        id = this.id,
        title = this.title,
        contents = this.contents,
        images = this.images,
        createDate = this.createDate
    )
}

fun DiaryEntity.toModel(): DiaryModel {
    return object : DiaryModel {
        override val id = this@toModel.id
        override val title = this@toModel.title
        override val contents = this@toModel.contents
        override val images = this@toModel.images
        override val createDate = this@toModel.createDate
    }
}
