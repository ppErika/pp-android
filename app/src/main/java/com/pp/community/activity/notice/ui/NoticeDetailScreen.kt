package com.pp.community.activity.notice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pp.community.ui.theme.color_bbbbbb

@Composable
fun NoticeDetailScreen(title: String, content: String, createDate: String) {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Text(
            text = title,
            fontSize = 15.sp
        )
        Text(
            text = createDate,
            fontSize = 12.sp
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(1.dp)
                .background(color = color_bbbbbb)

        )
        Text(text = content, fontSize = 12.sp)
    }
}