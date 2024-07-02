package com.pp.community.activity.main.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pp.community.R
import com.pp.community.ui.CustomModifier.removeEffectClickable
import com.pp.community.ui.getRobotoFontFamily
import com.pp.community.ui.module.InfiniteListHandler
import com.pp.community.ui.theme.color_d9d9d9
import com.pp.community.ui.theme.color_white
import com.pp.community.utils.ConvertUtils
import com.pp.domain.model.post.PostModel

@Composable
fun DiaryScreen(
    communityPostList: List<PostModel>,
    onClickItemEvent: (PostModel) -> Unit,
    onClickUploadEvent: () -> Unit,
    loadEvent: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        when (communityPostList.isNotEmpty()) {
            true -> DiaryListUI(communityPostList, onClickItemEvent, loadEvent)
            false -> {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(id = R.drawable.post_it_note_with_pin),
                    contentDescription = null
                )
            }
        }

        Image(
            painterResource(id = R.drawable.ic_diary_upload_btn), contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .removeEffectClickable { onClickUploadEvent() }
        )
    }
}

@Composable
fun DiaryListUI(
    communityPostList: List<PostModel>,
    onClickEvent: (PostModel) -> Unit,
    loadEvent: () -> Unit
) {
    val lazyListState = rememberLazyGridState()
    LazyVerticalGrid(
        state = lazyListState,
        columns = GridCells.Fixed(2)
    ) {
        items(
            communityPostList
        ) {
            DiaryItemUI(it, onClickEvent)
        }
    }
    //무한 스크롤
    InfiniteListHandler(gridListState = lazyListState) {
        Log.d("EJ_LOG", "infiniteList Call")
        loadEvent()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DiaryItemUI(
    post: PostModel,
    onClickEvent: (PostModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(shape = RoundedCornerShape(10.dp), color = color_white)
            .removeEffectClickable {
                onClickEvent(post)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                    color = color_d9d9d9
                ),
            contentAlignment = Alignment.Center
        ) {
            if (post.thumbnailUrl.isNullOrEmpty().not()) {
                when(post.type){
                    "COMMUNITY" -> {
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
                    "DIARY" -> {
                        val byteArray = android.util.Base64.decode(post.thumbnailUrl, android.util.Base64.DEFAULT)
                        val bitmap = ConvertUtils.byteArrayToBitmap(byteArray)
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        )
                    }
                }

            } else {
                Image(
                    painter = painterResource(id = R.drawable.img_empty),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                )
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