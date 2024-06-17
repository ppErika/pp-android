package com.pp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pp.data.dao.DiaryDao
import com.pp.data.entity.DiaryEntity
import com.pp.data.utils.Converters

@Database(entities = [DiaryEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

}