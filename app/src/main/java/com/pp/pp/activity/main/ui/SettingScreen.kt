package com.pp.pp.activity.main.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pp.pp.R
import com.pp.pp.ui.CustomModifier.removeEffectClickable
import com.pp.pp.ui.theme.color_bbbbbb
import com.pp.pp.ui.theme.color_main

@Composable
fun SettingScreen(
    isLogin: Boolean,
    version: String,
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
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ) {
        items(settingList) {
            SettingItemUI(it, if (it == "버전 정보") version else null) {
                when (it) {
                    "공지사항" -> {}
                    "개인정보 처리 방침" -> {}
                    "서비스 이용 약관" -> {}
                }
            }
        }
        items(userSettingList) {
            UserSettingItemUI(title = it) {
                when (it) {
                    "로그아웃" -> {}
                    "탈퇴하기" -> {}
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