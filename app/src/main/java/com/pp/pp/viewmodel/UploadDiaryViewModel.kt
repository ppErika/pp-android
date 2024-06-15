package com.pp.pp.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pp.data.entity.DiaryEntity
import com.pp.domain.repository.RoomRepository
import com.pp.pp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadDiaryViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val application: Application
) : BaseViewModel() {

    fun saveDiaryEntry(title: String, contents: String, imageUris: List<Uri>?) {
        viewModelScope.launch {
            val imageByteArrays = imageUris?.map { uri ->
                application.contentResolver.openInputStream(uri)?.use { inputStream ->
                    inputStream.readBytes()
                } ?: byteArrayOf()
            }

            val entry = DiaryEntity(
                title = title,
                contents = contents,
                images = imageByteArrays
            )
            roomRepository.insert(entry)
        }
    }

    fun writingLog() {
        Log.d("test", "test")
    }
}