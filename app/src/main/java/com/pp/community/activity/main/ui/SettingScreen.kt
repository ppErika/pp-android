package com.pp.community.activity.main.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pp.community.R
import com.pp.community.ui.CustomModifier.removeEffectClickable
import com.pp.community.ui.theme.color_bbbbbb
import com.pp.community.ui.theme.color_ebebf4
import com.pp.community.ui.theme.color_main
import com.pp.domain.model.users.GetUserProfileResponse

@Composable
fun SettingScreen(
    isLogin: Boolean,
    version: String,
    profileInfo: GetUserProfileResponse?,
    onClick: (String) -> Unit
    ) {
    val settingList = listOf(
        stringResource(id = R.string.setting_notice),
        stringResource(id = R.string.setting_privacy_policy),
        stringResource(id = R.string.setting_terms_of_service),
        stringResource(id = R.string.setting_version_info)
    )
    val userSettingList = if (isLogin) {
        listOf(
            stringResource(id = R.string.setting_logout),
            stringResource(id = R.string.setting_delete_account)
        )
    } else {
        emptyList()
    }

    Column(
        modifier = Modifier.padding(19.dp)
    ) {
        profileInfo?.let{
            ProfileUI("프로필 보기", it){
                onClick("profile")
            }
        }
        LazyColumn(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            items(settingList) {
                SettingItemUI(it, if (it == "버전 정보") version else null) {
                    when (it) {
                        "공지사항" -> { onClick("notice") }
                        "개인정보 처리 방침" -> {onClick("terms1")}
                        "서비스 이용 약관" -> {onClick("terms2")}
                    }
                }
            }
            items(userSettingList) {
                UserSettingItemUI(title = it) {
                    when (it) {
                        "로그아웃" -> {
                            onClick("logout")
                        }
                        "탈퇴하기" -> {}
                    }
                }
            }

        }
    }
}
@Composable
fun SettingItemUI(title: String, sub: String? = null, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .removeEffectClickable { onClick() }
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .border(width = 1.dp, color = color_bbbbbb, shape = RoundedCornerShape(5.dp))
            .padding(10.dp),
    ) {
        Text(
            text = title
        )
        Spacer(modifier = Modifier.weight(1f))
        sub?.run {
            Text(text = sub, color = color_main)
        } ?: (
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Localized description"
                )
                )
    }
}

@Composable
fun UserSettingItemUI(title: String, onClick: () -> Unit) {
    Text(
        modifier = Modifier
            .removeEffectClickable { onClick() }
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .border(width = 1.dp, color = color_bbbbbb, shape = RoundedCornerShape(5.dp))
            .padding(10.dp),
        text = title
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileUI(text: String, profileInfo: GetUserProfileResponse,onClick: () -> Unit){
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        GlideImage(
            model = profileInfo.profileImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = profileInfo.nickname)
        Spacer(modifier = Modifier.weight(1f)) // Fill remaining space
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = color_main,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            ),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = text)
        }
    }

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 22.dp),
        color = color_ebebf4
    )
}