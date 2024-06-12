package com.pp.data.repository

import com.pp.data.dao.DiaryDao
import com.pp.data.entity.Diary
import com.pp.domain.model.room.DiaryRequest
import com.pp.domain.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val diaryDao: DiaryDao
) : RoomRepository {

    override suspend fun insert(diary: DiaryRequest) {
        val diaryEntity = Diary(
            id = diary.id,
            title = diary.title,
            contents = diary.contents,
            createDate = diary.createDate,
            imageData = diary.imageData
        )
        diaryDao.insert(diaryEntity)
    }

    override suspend fun getDiary(id: Int): DiaryRequest? {
        return withContext(Dispatchers.IO) {
            diaryDao.getById(id)?.let { diaryEntity ->
                DiaryRequest(
                    id = diaryEntity.id,
                    title = diaryEntity.title,
                    contents = diaryEntity.contents,
                    createDate = diaryEntity.createDate,
                    imageData = diaryEntity.imageData
                )
            }
        }
    }
}