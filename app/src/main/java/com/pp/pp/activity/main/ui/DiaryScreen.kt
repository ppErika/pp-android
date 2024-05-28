package com.pp.pp.activity.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pp.domain.model.post.PostModel
import com.pp.pp.R
import com.pp.pp.ui.getRobotoFontFamily
import com.pp.pp.ui.theme.color_d9d9d9
import com.pp.pp.ui.theme.color_ebebf4
import com.pp.pp.ui.theme.color_white

@Composable
fun DiaryScreen(
    communityPostList: List<PostModel>
) {
    Box(
        modifier = Modifier
            .background(color = color_ebebf4)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        DiaryListUI(communityPostList)
        Image(
            painterResource(id = R.drawable.ic_diary_upload_btn), contentDescription = null,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun DiaryListUI(
    communityPostList: List<PostModel>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(
            communityPostList
        ) {
            DiaryItemUI(it)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DiaryItemUI(post: PostModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(shape = RoundedCornerShape(10.dp), color = color_white)
    ) {
        Box(
            modifier = Modifier
                .height(118.dp)
                .fillMaxWidth()
                .background(
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                    color = color_d9d9d9
                ),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = post.thumbnailUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            ) {

                it.error(R.drawable.img_empty)
                    .placeholder(R.drawable.img_empty)
                    .load(post.thumbnailUrl)
            }
        }
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = post.title,
            fontSize = 15.sp,
            fontFamily = getRobotoFontFamily()
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = post.createDate,
            fontSize = 12.sp,
            fontFamily = getRobotoFontFamily()
        )
    }
}