package com.pp.community.activity.comment.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.pp.community.R
import com.pp.community.ui.CustomModifier.removeEffectClickable
import com.pp.community.ui.theme.color_bbbbbb
import com.pp.community.ui.theme.color_white

@Composable
fun CommentInputUI(
    modifier: Modifier = Modifier,
    inputComment: String,
    inputCommentEvent: (String) -> Unit,
    postCommentEvent: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp, color = color_bbbbbb, shape = RoundedCornerShape(42.dp)
            )
            .background(color = color_white)
            .padding(10.dp)
    ) {
        BasicTextField(
            value = inputComment,
            onValueChange = { inputCommentEvent(it) },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide() // 키보드 숨기기
            }),
            modifier = Modifier
                .weight(1f)
        )
        Image(
            modifier = Modifier
                .removeEffectClickable {
                    keyboardController?.hide() // 키보드 숨기기
                    postCommentEvent()
                }
                .weight(0.1f),
            painter = painterResource(id = R.drawable.ic_comment_input_btn),
            contentDescription = null,
        )
    }

}