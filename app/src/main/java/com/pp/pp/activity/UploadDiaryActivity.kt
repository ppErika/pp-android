package com.pp.pp.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.pp.pp.viewmodel.UploadDiaryViewModel
import com.pp.pp.base.BaseActivity
import com.pp.pp.ui.theme.color_white

class UploadDiaryActivity : BaseActivity<UploadDiaryViewModel>() {
    override val viewModel: UploadDiaryViewModel by viewModels()

    override fun observerViewModel() {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeUi() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        var text by remember { mutableStateOf("Hello") }

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.title_upload_diary),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
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
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Localized description"
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
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .size(65.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(colorResource(id = R.color.background_upload_image)),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = stringResource(id = R.string.btn_upload_image),
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.sub_title_title),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 30.dp)
                    )

                    BasicTextField(
                        value = text, onValueChange = { text = it },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .height(40.dp)
                            .fillMaxWidth()
                            .background(color = Color.White),
                        singleLine = true,
                        maxLines = 1,
                        decorationBox = { innerTextField ->
                            Row(
                                modifier = Modifier
                                    .background(Color.White)
                                    .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                innerTextField()
                            }
                        }
                    )

                    Text(
                        text = stringResource(id = R.string.sub_title_content),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 30.dp)
                    )

                    BasicTextField(
                        value = text, onValueChange = { text = it },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .height(416.dp)
                            .fillMaxWidth()
                            .background(color = Color.White),
                        singleLine = true,
                        maxLines = 1,
                        decorationBox = { innerTextField ->
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(size = 5.dp))
                                    .background(color = color_white)
                                    .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top
                            ) {
                                innerTextField()
                            }
                        }
                    )

                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.main_color),
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        ),
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 35.dp),
                    ) {
                        Text(text = stringResource(id = R.string.btn_completion_completed))
                    }
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