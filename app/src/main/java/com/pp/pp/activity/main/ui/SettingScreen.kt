package com.pp.pp.activity.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.pp.pp.R
import com.pp.pp.ui.CustomModifier.removeEffectClickable
import com.pp.pp.ui.theme.color_bbbbbb
import com.pp.pp.ui.theme.color_ebebf4
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

    Column(
        modifier = Modifier.padding(19.dp)
    ) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "바다거북맘")
            Spacer(modifier = Modifier.weight(1f)) // Fill remaining space
            Button(onClick = { /* Handle button click */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = color_main,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                ),
                shape = RoundedCornerShape(5.dp)) {
                Text(text = "프로필 보기")
            }
        }

        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 22.dp),
            color = color_ebebf4)

        LazyColumn(
            modifier = Modifier.padding(vertical = 10.dp)
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

@Preview
@Composable
fun SettingScreenPreview() {
    SettingScreen(isLogin = true, version = "1.0.0") {

    }
}
