package com.pp.community.activity.profile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pp.community.activity.main.ui.DiaryListUI
import com.pp.community.activity.main.ui.ProfileUI
import com.pp.domain.model.users.GetUserProfileResponse

@Composable
fun ProfileScreen(
    profileInfo: GetUserProfileResponse,
    onClickModify: () -> Unit,
    onClickDetail: () -> Unit
) {
    Column(modifier = Modifier.padding(10.dp)) {
        ProfileUI("프로필 수정", profileInfo) {
            onClickModify()
        }
        ProfileSummaryUI(
            postCount = profileInfo.postCount,
            thumbsUpCount = profileInfo.thumbsUpCount
        )
        DiaryListUI(profileInfo.posts, {onClickDetail()}, {})
    }

}
