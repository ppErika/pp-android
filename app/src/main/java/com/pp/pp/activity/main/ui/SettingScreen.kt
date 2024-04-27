package com.pp.pp.activity.main.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pp.pp.ui.theme.color_bbbbbb

@Composable
fun SettingScreen() {
    val settingList = remember {
        listOf("공지사항", "약관 및 정책", "버전 정보")
    }
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ) {
        items(settingList) {
            SettingItemUI(it)
        }
    }
}

@Composable
fun SettingItemUI(title: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .border(width = 1.dp, color = color_bbbbbb, shape = RoundedCornerShape(5.dp))
            .padding(10.dp),
        text = title
    )
}