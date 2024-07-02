package com.pp.data.repository

import com.pp.data.entity.DiaryEntity
import com.pp.domain.model.room.DiaryModel
import com.pp.domain.repository.RoomRepository
import com.pp.data.dao.DiaryDao
import com.pp.data.utils.toEntity
import com.pp.data.utils.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val diaryDao: DiaryDao
) : RoomRepository {

    override suspend fun insert(diary: DiaryModel) {
        diaryDao.insert(diary.toEntity())
    }

    override suspend fun getAll(): List<DiaryModel> {
        return diaryDao.getAll().map { it.toModel() }
    }

    override suspend fun getById(id: Int): DiaryModel? {
        return withContext(Dispatchers.IO) {
            diaryDao.getById(id)?.toModel()
        }
    }

    override suspend fun deleteById(id: Int) {
        diaryDao.deleteById(id)
    }
}
