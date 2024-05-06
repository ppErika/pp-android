package com.pp.pp.viewmodel.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.pp.domain.model.token.OauthTokenRequest
import com.pp.domain.usecase.token.OauthTokenUseCase
import com.pp.domain.usecase.users.UserRegisteredUseCase
import com.pp.pp.base.BaseViewModel
import com.pp.pp.widget.SingleFlowEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isUserRegisteredUseCase: UserRegisteredUseCase,
    private val oauthTokenUseCase: OauthTokenUseCase
) : BaseViewModel() {
    var appBarTitle = mutableStateOf("My Wallet")
        private set
    var isLogin = mutableStateOf(false)
        private set
    private val _movePageEvent = SingleFlowEvent<String>()
    val movePageEvent = _movePageEvent.flow

    fun setAppBarTitle(title: String) {
        appBarTitle.value = title
    }

    fun isUserRegistered(idToken: String){
        viewModelScope.launch {
            val response = isUserRegisteredUseCase.execute(this@MainViewModel,"kakao",idToken)
            response?.let{
                Log.d("EJ_LOG","isUserReigstered : $it")
                // true -> oauthToken 호출
                // false -> 회원가입 페이지로 이동
                when(it.data.isRegistered){
                    true -> getOauthToken(idToken)
                    false -> _movePageEvent.emit("termsOfUse")
                }

            }
        }
    }

    fun getOauthToken(idToken: String) {
        val oauthTokenRequest = OauthTokenRequest().apply {
            client_id = "kauth.kakao.com"
            client_assertion = idToken
            grant_type="client_credentials"
        }
        viewModelScope.launch {
            val response = oauthTokenUseCase.execute(this@MainViewModel, oauthTokenRequest)
            response?.let {
                isLogin.value = true
                Log.d("EJ_LOG", "testApi response $it")
            }
        }
    }
}