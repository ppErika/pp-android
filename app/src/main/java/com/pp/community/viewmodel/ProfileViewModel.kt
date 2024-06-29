package com.pp.community.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.pp.community.base.BaseViewModel
import com.pp.domain.model.users.GetUserProfileResponse
import javax.inject.Inject

class ProfileViewModel @Inject constructor():BaseViewModel() {
    var profileInfo = mutableStateOf(GetUserProfileResponse())
        private set

    fun setProfileInfo(profile: GetUserProfileResponse){
        profileInfo.value = profile
    }
}