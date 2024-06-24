package com.pp.community.activity

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import com.pp.community.R
import com.pp.community.activity.main.route.MainNav
import com.pp.community.base.BaseActivity
import com.pp.community.ui.theme.color_f3f3f3
import com.pp.community.ui.theme.color_main
import com.pp.community.ui.theme.color_white
import com.pp.community.utils.FileUtils
import com.pp.community.utils.FileUtils.getFileInfo
import com.pp.community.viewmodel.UploadDiaryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class UploadDiaryActivity : BaseActivity<UploadDiaryViewModel>() {
    override val viewModel: UploadDiaryViewModel by viewModels()

    override fun observerViewModel() {
        with(mViewModel){
            postErrorEvent.onEach {
                Toast.makeText(this@UploadDiaryActivity, it, Toast.LENGTH_SHORT).show()
            }.launchIn(lifecycleScope)
            postSuccessEvent.onEach {
                Toast.makeText(this@UploadDiaryActivity, it, Toast.LENGTH_SHORT).show()
                finish()
            }.launchIn(lifecycleScope)
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ComposeUi() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        var title by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }
        var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) { uris: List<Uri> ->
            val maxSizeInBytes = 1048576 // 1MB

            val resizedUris = uris.take(3).mapNotNull { uri ->
                val bitmap = FileUtils.getBitmapFromUri(this, uri)
                bitmap?.let {
                    val resizedBitmap = FileUtils.resizeBitmap(it, 1024, 1024) // Adjust size as needed
                    val compressedImage = FileUtils.compressBitmap(resizedBitmap, maxSizeInBytes)
                    val compressedBitmap = BitmapFactory.decodeByteArray(compressedImage, 0, compressedImage.size)
                    FileUtils.saveBitmapToTempFile(this, compressedBitmap)
                }
            }
            Log.d("EJ_LOG","resizedUris : $resizedUris")

            // Handle the result
            selectedImageUris = resizedUris
        }

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
                        IconButton(onClick = { finish()}) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowLeft,
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { galleryLauncher.launch("image/*") },
                            modifier = Modifier
                                .size(65.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(color_f3f3f3),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = stringResource(id = R.string.btn_upload_image),
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            selectedImageUris.forEach { uri ->
                                Image(
                                    painter = rememberImagePainter(uri),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(65.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(Color.Gray)
                                        .border(1.dp, Color.Gray),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }

                    Text(
                        text = stringResource(id = R.string.sub_title_title),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 30.dp)
                    )

                    BasicTextField(
                        value = title,
                        onValueChange = { title = it },
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
                                if (title.isEmpty()) {
                                    Text(
                                        text = stringResource(id = R.string.hint_title),
                                        color = Color.Gray
                                    )
                                }
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
                        value = content,
                        onValueChange = { content = it },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .weight(1f)
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
                                if (content.isEmpty()) {
                                    Text(
                                        text = stringResource(id = R.string.hint_content),
                                        color = Color.Gray
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )

                    Button(
                        onClick = {
                            if (title.isBlank() || content.isBlank()) {
                                showShortToast("제목과 내용을 입력해주세요.")
                            } else {
                                when(mViewModel.uploadType){
                                    MainNav.MyDiary.name -> {
                                        viewModel.saveDiaryEntry(title, content, selectedImageUris)
                                        showShortToast("업로드에 성공했습니다.")
                                        finish()
                                    }
                                    MainNav.Community.name -> {
                                        when(selectedImageUris.isEmpty()){
                                            true -> mViewModel.uploadPost(title, content)
                                            false ->{
                                                val fileInfoList = selectedImageUris.map { uri -> getFileInfo(this@UploadDiaryActivity,"POST_IMAGE", uri) }

                                                mViewModel.getPreSignedUrl(title, content, fileInfoList)
                                            }
                                        }


                                    }
                                }

                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = color_main,
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
        val uploadType = intent.getStringExtra("type") ?: MainNav.Community.name
        mViewModel.uploadType = uploadType
    }
}
