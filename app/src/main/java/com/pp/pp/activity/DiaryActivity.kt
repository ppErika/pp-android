package com.pp.pp.activity

import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pp.pp.R
import com.pp.pp.base.BaseActivity
import com.pp.pp.viewmodel.DiaryViewModel
import com.pp.pp.ui.CommonCompose
import com.pp.pp.ui.getRobotoFontFamily
import com.pp.pp.ui.theme.color_d9d9d9
import com.pp.pp.ui.theme.color_ebebf4
import com.pp.pp.ui.theme.color_white
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiaryActivity : BaseActivity<DiaryViewModel>() {
    override val viewModel: DiaryViewModel by viewModels()
    override fun observerViewModel() {
    }

    @Preview
    @Composable
    override fun ComposeUi() {
        Column(modifier = Modifier.fillMaxSize()) {
            CommonCompose.CommonAppBarUI(title = "나의 일기", isBackPressed = false) {}
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = color_ebebf4)
            ) {
                DiaryListUI()
                Image(
                    painterResource(id = R.drawable.ic_diary_upload_btn), contentDescription = null,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }


    }

    override fun init() {
    }
}

@Composable
fun DiaryListUI() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(
            listOf(
                1,2,3,4,5,6,67,7,8,8,9,9,0,233,4,45,65,7,89,9,5,323,2,4,65,677,78
            )
        ) {
            DiaryItemUI()
        }
    }
}

@Composable
fun DiaryItemUI() {
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
            Image(
                painterResource(id = R.drawable.img_empty), contentDescription = null
            )
        }
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "바다거북이 본 날",
            fontSize = 15.sp,
            fontFamily = getRobotoFontFamily()
        )
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = "2024.03.30",
            fontSize = 12.sp,
            fontFamily = getRobotoFontFamily()
        )
    }
}