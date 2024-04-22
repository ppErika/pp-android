package com.pp.pp.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.usecase.token.OauthTokenUseCase
import com.pp.pp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val oauthTokenUseCase: OauthTokenUseCase
) : BaseViewModel() {

    fun testApi() {
        viewModelScope.launch {
            val response = oauthTokenUseCase.execute(this@MainViewModel, OauthTokenRequest())
            response?.let {
                Log.d("EJ_LOG", "testApi response $it")

            }
        }

    }
}