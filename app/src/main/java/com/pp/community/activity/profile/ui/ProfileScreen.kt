package com.pp.community.activity.profile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pp.community.activity.main.ui.DiaryListUI
import com.pp.community.activity.main.ui.ProfileUI
import com.pp.domain.model.users.GetUserProfileResponse

@Composable
fun ProfileScreen(
    profileInfo: GetUserProfileResponse,
    inputNickname: MutableState<String>,
    selectedProfileImage: MutableState<String>,
    onClickModify: () -> Unit,
    onClickDetail: () -> Unit,
    onclickProfileImage: () -> Unit
) {
    var isShowBottomSheet by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(10.dp)) {
        ProfileUI("프로필 수정", profileInfo) {
            isShowBottomSheet = true
        }
        ProfileSummaryUI(
            postCount = profileInfo.postCount,
            thumbsUpCount = profileInfo.thumbsUpCount
        )
        DiaryListUI(profileInfo.posts, { onClickDetail() }, {})
    }
    ProfileModifyBottomSheetUI(
        isShow = isShowBottomSheet,
        selectedProfileImage = selectedProfileImage,
        inputNickname = inputNickname,
        onclickProfileImage = { onclickProfileImage() },
        onDismiss = { isShowBottomSheet = false },
        onModifyEvent = {
            onClickModify()
            isShowBottomSheet = false
        }
    )

}
