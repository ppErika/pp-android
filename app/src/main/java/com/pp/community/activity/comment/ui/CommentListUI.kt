package com.pp.community.activity.comment.ui

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pp.community.R
import com.pp.community.ui.CommonCompose
import com.pp.community.ui.getRobotoFontFamily
import com.pp.community.ui.module.InfiniteListHandler
import com.pp.community.ui.theme.color_999999
import com.pp.domain.model.comments.CommentModel
import com.pp.domain.model.comments.CreatedUser

@Composable
fun CommentListUI(
    list: List<CommentModel>,
    reportEvent: (CommentModel) -> Unit,
    modifier: Modifier = Modifier,
    loadEvent: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        items(list) {
            CommentItemUI(it, reportEvent)
        }
    }
    //무한 스크롤
    InfiniteListHandler(lazyListState) {
        Log.d("EJ_LOG", "infiniteList Call")
        loadEvent()
    }
}

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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularImage(url = commentModel.createdUser.profileImageUrl)
        Column {
            Row {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = commentModel.createdUser.nickname,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = getRobotoFontFamily()
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = commentModel.createDate,
                    fontSize = 10.sp,
                    color = color_999999,
                    fontFamily = getRobotoFontFamily()
                )
            }

            Text(
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 14.sp,
                text = commentModel.content
            )
        }

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