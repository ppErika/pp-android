package com.pp.pp.activity.comment.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pp.domain.model.comments.CommentModel

@Composable
fun CommentUI(
    list: List<CommentModel>,
    reportEvent: (CommentModel) -> Unit,
){
    Column {
        CommentListUI(list = list, reportEvent = reportEvent, modifier = Modifier.weight(0.9f))
        CommentInputUI(modifier = Modifier.weight(0.1f))
    }

}