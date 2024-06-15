package com.pp.pp.activity.comment.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pp.pp.R
import com.pp.pp.ui.theme.color_bbbbbb
import com.pp.pp.ui.theme.color_white

@Preview
@Composable
fun CommentInputUI(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = color_bbbbbb, shape = RoundedCornerShape(42.dp)
            )
            .background(color = color_white)
    ) {
        TextField(
            value = "",
            onValueChange = {},
            label = { },
            modifier = Modifier.weight(1f).background(color = color_white)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_comment_input_btn),
            contentDescription = null
        )
    }

}