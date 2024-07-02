package com.pp.community.viewmodel

import androidx.lifecycle.viewModelScope
import com.pp.community.base.BaseViewModel
import com.pp.community.widget.SingleFlowEvent
import com.pp.domain.model.room.DiaryModel
import com.pp.domain.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryDetailsViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
): BaseViewModel() {
    private val _postDetails = MutableStateFlow<DiaryModel?>(null)
    val postDetails: StateFlow<DiaryModel?> = _postDetails

    fun loadDiaryDetails(postId: Int) {
        viewModelScope.launch {
            _postDetails.value = roomRepository.getById(id = postId)
        }
    }

    fun deleteDiary(postId: Int) {
        viewModelScope.launch {
            roomRepository.deleteById(id = postId)
        }
    }
}