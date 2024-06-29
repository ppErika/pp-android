package com.pp.community.activity.profile

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.pp.community.activity.profile.ui.ProfileScreen
import com.pp.community.base.BaseActivity
import com.pp.community.ui.CommonCompose
import com.pp.community.viewmodel.ProfileViewModel
import com.pp.domain.model.users.GetUserProfileResponse

class ProfileActivity : BaseActivity<ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModels()
    override fun observerViewModel() {
    }

    @Composable
    override fun ComposeUi() {
        val profileInfo = remember {
            mViewModel.profileInfo
        }.value
        Column {
            CommonCompose.CommonAppBarUI(title = "프로필", isBackPressed = true) {
                finish()
            }
            ProfileScreen(
                profileInfo = profileInfo,
                onClickModify = {},
                onClickDetail = {}
            )
        }
    }

    override fun init() {
        val profileInfo =
            intent.getSerializableExtra("profileInfo", GetUserProfileResponse::class.java)
        profileInfo?.let {
            Log.d("profileInfo", it.toString())
            mViewModel.setProfileInfo(it)
        }
    }
}