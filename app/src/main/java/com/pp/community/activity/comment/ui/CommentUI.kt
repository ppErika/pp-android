package com.pp.community.activity.comment.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pp.domain.model.comments.CommentModel

@Composable
fun CommentUI(
    list: List<CommentModel>,
    inputComment: String,
    reportEvent: (CommentModel) -> Unit,
    inputCommentEvent: (String) -> Unit,
    postCommentEvent: () -> Unit,
    loadEvent: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CommentListUI(list = list, reportEvent = reportEvent, modifier = Modifier.weight(1f), loadEvent = loadEvent)
        CommentInputUI(
            modifier = Modifier.weight(0.1f),
            inputComment = inputComment,
            inputCommentEvent = inputCommentEvent,
            postCommentEvent = postCommentEvent
        )
    }

}