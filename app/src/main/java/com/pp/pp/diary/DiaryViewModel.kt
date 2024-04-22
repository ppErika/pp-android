package com.pp.pp.diary

import com.pp.pp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(): BaseViewModel(){
    override fun onError(msg: String) {
        TODO("Not yet implemented")
    }
}