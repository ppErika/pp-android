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
import com.pp.community.ui.CustomModifier.removeEffectClickable
import com.pp.community.ui.theme.color_bbbbbb
import com.pp.domain.model.notice.Notice

@Composable
fun NoticeItemUI(item: Notice,onClickEvent:(Notice)->Unit) {
    Column(modifier = Modifier.removeEffectClickable { onClickEvent(item) }) {
        Text(
            text = item.title,
            fontSize = 15.sp
        )
        Text(
            text = item.createDate,
            fontSize = 12.sp
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .height(1.dp)
                .background(color = color_bbbbbb)
        )
    }
}