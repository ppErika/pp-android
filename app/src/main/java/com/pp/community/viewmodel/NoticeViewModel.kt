package com.pp.community.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.pp.community.base.BaseViewModel
import com.pp.domain.model.notice.GetNoticesRequest
import com.pp.domain.model.notice.Notice
import com.pp.domain.usecase.notice.GetNoticesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val getNoticesUseCase: GetNoticesUseCase
) : BaseViewModel() {
    var noticeList = mutableStateListOf<Notice>()
        private set

    fun getNotice() {
        viewModelScope.launch {
            val getNoticesRequest = GetNoticesRequest()
            val response = getNoticesUseCase.execute(this@NoticeViewModel, getNoticesRequest)
            response?.let{
                noticeList.clear()
                noticeList.addAll(it.notices)
            }
        }
    }
}