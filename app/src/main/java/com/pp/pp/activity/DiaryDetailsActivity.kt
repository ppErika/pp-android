package com.pp.pp.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pp.pp.R
import com.pp.pp.base.BaseActivity
import com.pp.pp.ui.getRobotoFontFamily
import com.pp.pp.ui.theme.color_ebebf4
import com.pp.pp.ui.theme.color_white
import com.pp.pp.viewmodel.DiaryDetailsViewModel

class DiaryDetailsActivity : BaseActivity<DiaryDetailsViewModel>() {
    override val viewModel: DiaryDetailsViewModel by viewModels()

    override fun observerViewModel() {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    override fun ComposeUi() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        var showMenu by remember { mutableStateOf(false) }

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {

                    },
                    navigationIcon = {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowLeft,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Localized description"
                            )
                        }
                        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                            DropdownMenuItem(
                                text = {
                                    Text(text = stringResource(id = R.string.btn_delete))
                                },
                                onClick = { /*TODO*/ }
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, top = 66.dp, end = 16.dp, bottom = 16.dp),

                    ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(258.dp)
                            .background(
                                brush = Brush.horizontalGradient(listOf(Color.White, Color.White)),
                                RoundedCornerShape(10.dp),
                            )
                    ) {
                        val pagerState = rememberPagerState(pageCount = {
                            3
                        })

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            when (page) {
                                0 -> Image(
                                    modifier = Modifier
                                        .height(258.dp)
                                        .fillMaxWidth()
                                        .background(
                                            color_ebebf4,
                                            RoundedCornerShape(10.dp)
                                        ),
                                    painter = painterResource(id = R.drawable.ic_logo),
                                    contentDescription = null
                                )

                                1 -> Image(
                                    modifier = Modifier
                                        .height(258.dp)
                                        .fillMaxWidth()
                                        .background(
                                            color_ebebf4,
                                            RoundedCornerShape(10.dp)
                                        ),
                                    painter = painterResource(id = R.drawable.ic_logo),
                                    contentDescription = null
                                )

                                2 -> Image(
                                    modifier = Modifier
                                        .height(258.dp)
                                        .fillMaxWidth()
                                        .background(
                                            color_ebebf4,
                                            RoundedCornerShape(10.dp)
                                        ),
                                    painter = painterResource(id = R.drawable.ic_logo),
                                    contentDescription = null
                                )
                            }
                        }

                        Row(
                            Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(pagerState.pageCount) { iteration ->
                                val color =
                                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(8.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = "바다 거북이 본 날",
                        modifier = Modifier.padding(top = 25.dp),
                        fontSize = 18.sp,
                        fontFamily = getRobotoFontFamily()
                    )
                    Text(
                        text = "0000.00.00.",
                        modifier = Modifier.padding(top = 5.dp),
                        fontSize = 12.sp,
                        fontFamily = getRobotoFontFamily()
                    )
                    Text(
                        text = "내용내용내용",
                        modifier = Modifier.padding(top = 20.dp),
                        fontSize = 12.sp,
                        fontFamily = getRobotoFontFamily()
                    )
                }
            }
        )
    }

    @Preview
    @Composable
    fun Preview() {
        ComposeUi()
    }

    override fun init() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }
}