package com.pp.pp.viewmodel

import com.pp.pp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor() : BaseViewModel() {
    override fun onError(msg: String) {
        TODO("Not yet implemented")
    }
}