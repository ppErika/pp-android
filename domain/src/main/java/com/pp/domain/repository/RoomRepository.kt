package com.pp.domain.repository

import com.pp.domain.model.room.DiaryModel

interface RoomRepository {
    suspend fun insert(diary: DiaryModel)
    suspend fun getAll(): List<DiaryModel>
    suspend fun getById(id: Int): DiaryModel?
}