package com.pp.community.activity.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pp.community.ui.theme.color_bbbbbb

@Composable
fun ProfileSummaryUI(postCount: Int, thumbsUpCount: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "게시글 수")
            Text(
                text = postCount.toString(), fontWeight = FontWeight.Bold
            )
        }
        Spacer(
            modifier = Modifier
                .background(color = color_bbbbbb)
                .width(10.dp)
        )
        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "받은 좋아요 수")
            Text(
                text = thumbsUpCount.toString(),
                fontWeight = FontWeight.Bold
            )
        }

    }
}