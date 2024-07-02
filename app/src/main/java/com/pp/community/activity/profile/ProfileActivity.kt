package com.pp.community.activity.profile

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import com.pp.community.activity.profile.ui.ProfileScreen
import com.pp.community.base.BaseActivity
import com.pp.community.ui.CommonCompose
import com.pp.community.utils.FileUtils
import com.pp.community.viewmodel.ProfileViewModel
import com.pp.domain.model.users.GetUserProfileResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModels()
    override fun observerViewModel() {
        mViewModel.run{
            profileUpdateSuccessEvent.onEach {
                Log.d("EJ_LOG","profileUpdateSuccessEvent : $it")
                updateProfile()
                Toast.makeText(this@ProfileActivity, "프로필 수정 성공", Toast.LENGTH_SHORT).show()
            }.launchIn(this@ProfileActivity.lifecycleScope)
        }
    }

    @Composable
    override fun ComposeUi() {
        val profileInfo = remember {
            mViewModel.profileInfo
        }.value
        val inputNickname = remember {
            mViewModel.inputNickname
        }
        val selectedProfileImage = remember {
            mViewModel.selectedProfileImage
        }
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) { uris: List<Uri> ->
            if(uris.isEmpty()) return@rememberLauncherForActivityResult
            val maxSizeInBytes = 1048576 // 1MB

            val resizedUris = uris.take(1).mapNotNull { uri ->
                val bitmap = FileUtils.getBitmapFromUri(this, uri)
                bitmap?.let {
                    val resizedBitmap =
                        FileUtils.resizeBitmap(it, 1024, 1024) // Adjust size as needed
                    val compressedImage = FileUtils.compressBitmap(resizedBitmap, maxSizeInBytes)
                    val compressedBitmap =
                        BitmapFactory.decodeByteArray(compressedImage, 0, compressedImage.size)
                    FileUtils.saveBitmapToTempFile(this, compressedBitmap)
                }
            }
            Log.d("EJ_LOG", "resizedUris : $resizedUris")

            // Handle the result
            selectedProfileImage.value = resizedUris.first().toString()
        }
        Column {
            CommonCompose.CommonAppBarUI(title = "프로필", isBackPressed = true) {
                finish()
            }
            ProfileScreen(
                profileInfo = profileInfo,
                inputNickname = inputNickname,
                selectedProfileImage = selectedProfileImage,
                onclickProfileImage = {
                    galleryLauncher.launch("image/*")
                },
                onClickModify = {mViewModel.updateProfile()},
                onClickDetail = {}
            )
        }
    }

    override fun init() {
        val profileInfo =
            intent.getSerializableExtra("profileInfo", GetUserProfileResponse::class.java)
        profileInfo?.let {
            Log.d("profileInfo", it.toString())
            mViewModel.setProfileInfo(it)
        }
    }

}