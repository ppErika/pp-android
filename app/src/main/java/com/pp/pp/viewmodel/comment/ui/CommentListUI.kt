package com.pp.pp.viewmodel.comment.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pp.pp.ui.getRobotoFontFamily

@Composable
fun CommentListUI(modifier: Modifier = Modifier) {
    LazyColumn {
        items(5) {
            CommentItemUI()
        }
    }
}

@Preview
@Composable
fun CommentItemUI() {
    Row(
        modifier = Modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommentProfileUI()
        Spacer(modifier = Modifier.width(10.dp))
        Text("dhsmfajsdfkljasd;lfjsadklfjasdlfsdhsmfajsdfkljasd;lfjsadklfjasdlfsdhsmfajsdfkljasd;lfjsadklfjasdlfsdhsmfajsdfkljasd;lfjsadklfjasdlfsdhsmfajsdfkljasd;lfjsadklfjasdlfsdhsmfajsdfkljasd;lfjsadklfjasdlfsdh")
    }
}

@Composable
fun CommentProfileUI() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularImage(url = "https://cdn-store.leagueoflegends.co.kr/images/v2/companion/27031.png")
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "바다거북맘",
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