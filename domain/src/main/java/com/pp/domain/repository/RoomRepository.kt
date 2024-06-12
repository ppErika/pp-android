package com.pp.domain.repository

import com.pp.domain.model.room.DiaryRequest

interface RoomRepository {
    suspend fun insert(diary: DiaryRequest)
    suspend fun getDiary(id: Int): DiaryRequest?
}