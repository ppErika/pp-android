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
import com.pp.pp.activity.main.ui.DiaryListUI
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

