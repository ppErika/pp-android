package com.pp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pp.data.entity.DiaryEntity

@Dao
interface DiaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(diary: DiaryEntity)

    @Query("SELECT * FROM diary")
    suspend fun getAll(): List<DiaryEntity>

    @Query("SELECT * FROM diary WHERE id = :id")
    suspend fun getById(id: Int): DiaryEntity?
}