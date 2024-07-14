package com.pp.community.activity.notice.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pp.domain.model.notice.Notice

@Composable
fun NoticeScreen(noticeList: List<Notice>,onClickEvent:(Notice)->Unit) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        items(noticeList){
            NoticeItemUI(it){notice ->
                onClickEvent(notice)
            }
        }
    }
}