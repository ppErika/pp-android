package com.pp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pp.data.dao.DiaryDao
import com.pp.data.entity.Diary

@Database(entities = [Diary::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
}