package com.pp.domain.usecase.mydiary

import com.pp.domain.model.room.DiaryModel
import com.pp.domain.repository.RoomRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class GetAllMyDiaryUseCase @Inject constructor(
    private val roomRepository: RoomRepository
){
    suspend fun execute(
    ): List<DiaryModel> =
        roomRepository.getAll()
}