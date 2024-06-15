package com.pp.pp.viewmodel.comment.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun CommentUI(){
    Column {
        CommentListUI(modifier = Modifier.weight(0.9f))
        CommentInputUI(modifier = Modifier.weight(0.1f))
    }

}