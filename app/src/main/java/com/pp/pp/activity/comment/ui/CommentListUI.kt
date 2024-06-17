package com.pp.pp.activity.comment.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pp.domain.model.comments.CommentModel
import com.pp.domain.model.comments.CreatedUser
import com.pp.pp.R
import com.pp.pp.ui.CommonCompose
import com.pp.pp.ui.getRobotoFontFamily

@Composable
fun CommentListUI(
    list: List<CommentModel>,
    reportEvent: (CommentModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(list) {
            CommentItemUI(it, reportEvent)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommentItemUI(
    commentModel: CommentModel,
    reportEvent: (CommentModel) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(10.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showDialog = true
                    }
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommentProfileUI(commentModel.createdUser)
        Spacer(modifier = Modifier.width(10.dp))
        Text(commentModel.content)
    }
    CommonCompose.CommonDialog(
        isShow = showDialog,
        content = stringResource(id = R.string.dialog_report),
        onClickOk = {
            reportEvent(commentModel)
            showDialog = false
        },
        onClickCancel = { showDialog = false })
}

@Composable
fun CommentProfileUI(user: CreatedUser) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularImage(url = user.profileImageUrl)
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = user.nickname,
            fontSize = 12.sp,
            fontFamily = getRobotoFontFamily()
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CircularImage(url: String, modifier: Modifier = Modifier) {
    GlideImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(35.dp)
            .clip(CircleShape)
    )
}