package com.pp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pp.data.entity.Diary

@Dao
interface DiaryDao {
    @Insert
    suspend fun insert(diary: Diary)

    @Query("SELECT * FROM diary WHERE id = :id")
    suspend fun getById(id: Int): Diary?
}