package com.pp.pp.activity.comment.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pp.domain.model.comments.CommentModel
import com.pp.domain.model.comments.CreatedUser
import com.pp.pp.ui.getRobotoFontFamily

@Composable
fun CommentListUI(
    list: List<CommentModel>,
    reportEvent: (CommentModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(list){
            CommentItemUI(it, reportEvent)
        }
    }
}

@Composable
fun CommentItemUI(
    commentModel: CommentModel,
    reportEvent: (CommentModel) -> Unit,
) {
    Row(
        modifier = Modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommentProfileUI(commentModel.createdUser)
        Spacer(modifier = Modifier.width(10.dp))
        Text(commentModel.content)
    }
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