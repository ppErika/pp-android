package com.pp.community.activity

import android.os.Build
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import com.pp.community.base.BaseActivity
import com.pp.community.viewmodel.ProfileViewModel
import com.pp.domain.model.users.GetUserProfileResponse

class ProfileActivity:BaseActivity<ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModels()
    override fun observerViewModel() {
    }

    @Composable
    override fun ComposeUi() {
    }

    override fun init() {
        val profileInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
             intent.getSerializableExtra("profileInfo",GetUserProfileResponse::class.java)
        } else {
            intent.getSerializableExtra("data") as GetUserProfileResponse?
        }
        profileInfo?.let {
            Log.d("profileInfo",it.toString())
        }
    }
}