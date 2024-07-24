package com.pp.community.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.pp.community.base.BaseViewModel
import com.pp.community.utils.DecodeJwtUtil
import com.pp.community.utils.FileUtils
import com.pp.community.widget.SingleFlowEvent
import com.pp.domain.model.post.GetPreSignedUrlRequest
import com.pp.domain.model.post.PreSignedUploadUrl
import com.pp.domain.model.users.GetUserProfileResponse
import com.pp.domain.model.users.UpdateUserProfileRequest
import com.pp.domain.usecase.datastore.GetAccessTokenUseCase
import com.pp.domain.usecase.post.GetPreSignedUrlUseCase
import com.pp.domain.usecase.post.UploadFileUseCase
import com.pp.domain.usecase.users.GetUserProfileUseCase
import com.pp.domain.usecase.users.UpdateUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val application: Application,
    private val getPreSignedUrlUseCase: GetPreSignedUrlUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val decodeJwtUtil: DecodeJwtUtil
    ) : BaseViewModel() {
    var profileInfo = mutableStateOf(GetUserProfileResponse())
        private set
    var inputNickname = mutableStateOf("")
    var selectedProfileImage = mutableStateOf("")
    private val _profileUpdateSuccessEvent = SingleFlowEvent<String>()
    val profileUpdateSuccessEvent = _profileUpdateSuccessEvent.flow

    fun setProfileInfo(profile: GetUserProfileResponse) {
        profileInfo.value = profile
        inputNickname.value = profile.nickname
        selectedProfileImage = mutableStateOf(profile.profileImageUrl)
    }

    fun updateProfile() {
        if (selectedProfileImage.value != profileInfo.value.profileImageUrl) {
            updateProfileWithImage()
            return
        }
        val updateUserProfileRequest = UpdateUserProfileRequest().apply {
            nickname = inputNickname.value.takeIf { it != profileInfo.value.nickname }
        }
        viewModelScope.launch {
            val response =
                updateUserProfileUseCase.execute(
                    this@ProfileViewModel,
                    profileInfo.value.id,
                    updateUserProfileRequest
                )
            response?.let {
                Log.d("EJ_LOG", "updateProfile : $it")
            }
        }
    }

    private fun updateProfileWithImage() {
        val fileInfo = listOf(
            FileUtils.getFileInfo(
                application,
                "PROFILE_IMAGE",
                selectedProfileImage.value.toUri()
            )
        )
        val getPreSignedUrlRequest = GetPreSignedUrlRequest().apply {
            this.presignedUploadUrlRequests = fileInfo.map { temp ->
                PreSignedUploadUrl(
                    fileType = temp.fileType,
                    fileName = temp.fileName,
                    fileContentType = temp.fileContentType,
                    fileContentLength = temp.fileContentLength
                )
            }
        }
        viewModelScope.launch {
            val response1 =
                getPreSignedUrlUseCase.execute(this@ProfileViewModel, getPreSignedUrlRequest)
            response1?.let {
                val url = it.presignedUploadFiles.first().presignedUploadUrl
                val file = File(fileInfo.first().filePath)
                val contentType = fileInfo.first().fileContentType
                val requestFile = RequestBody.create(contentType.toMediaTypeOrNull(), file)
                val response2 =
                    uploadFileUseCase.execute(this@ProfileViewModel, url, requestFile)
                response2?.let {
                    Log.d("EJ_LOG", "uploadFile : $it")
                    val updateUserProfileRequest = UpdateUserProfileRequest().apply {
                        nickname = inputNickname.value.takeIf { it != profileInfo.value.nickname }
                        profileImageFileUploadId =
                            response1.presignedUploadFiles.first().fileUploadId
                    }
                    val response3 =
                        updateUserProfileUseCase.execute(
                            this@ProfileViewModel,
                            profileInfo.value.id,
                            updateUserProfileRequest
                        )
                    response3?.let {
                        Log.d("EJ_LOG", "updateProfile : $it")
                        _profileUpdateSuccessEvent.emit("success")
                    }
                }
            }
        }
    }
    fun getUserProfile(){
        viewModelScope.launch {
            val userId = decodeJwtUtil.getUserId(token = getAccessToken2()?:"").toInt()
            val response = getUserProfileUseCase.execute(this@ProfileViewModel,userId)
            response?.let{
                profileInfo.value = it
                Log.d("EJ_LOG","getUserProfile : $it")
            }
        }
    }
    suspend fun getAccessToken2(): String? {
        return withContext(Dispatchers.IO) {
            val token = getAccessTokenUseCase.invoke().first()
            Log.d("EJ_LOG", "getAccessToken : $token")
            token
        }
    }
}