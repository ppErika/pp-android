package com.pp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pp.data.entity.Diary

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary")
    fun getAll(): List<Diary>

    @Query("SELECT * FROM diary WHERE id = :id")
    fun getById(id: Int): Diary

    @Query("SELECT COUNT(*) FROM diary")
    fun getCount(): Int

    @Insert
    fun insert(vararg diary: Diary)

    @Delete
    fun delete(diary: Diary)
}