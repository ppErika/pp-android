package com.pp.community.viewmodel

import com.pp.community.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiaryDetailsViewModel @Inject constructor(): BaseViewModel() {
    override fun onError(msg: String) {
        TODO("Not yet implemented")
    }
}