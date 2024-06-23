package com.pp.community.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pp.community.base.BaseViewModel
import com.pp.community.widget.SingleFlowEvent
import com.pp.data.entity.DiaryEntity
import com.pp.domain.model.post.GetPreSignedUrlRequest
import com.pp.domain.model.post.GetPreSignedUrlResponse
import com.pp.domain.model.post.PreSignedUploadUrl
import com.pp.domain.model.post.PreSignedUploadUrlTemp
import com.pp.domain.model.post.UploadPostRequest
import com.pp.domain.repository.RoomRepository
import com.pp.domain.usecase.post.GetPreSignedUrlUseCase
import com.pp.domain.usecase.post.UploadFileUseCase
import com.pp.domain.usecase.post.UploadPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadDiaryViewModel @Inject constructor(
    private val application: Application,
    private val roomRepository: RoomRepository,
    private val getPreSignedUrlUseCase: GetPreSignedUrlUseCase,
    private val uploadPostUseCase: UploadPostUseCase,
    private val uploadFileUseCase: UploadFileUseCase
) : BaseViewModel() {

    private val _postErrorEvent = SingleFlowEvent<String>()
    val postErrorEvent = _postErrorEvent.flow

    var uploadType: String = ""

    fun saveDiaryEntry(title: String, contents: String, imageUris: List<Uri>?) {
        viewModelScope.launch {
            val imageByteArrays = imageUris?.map { uri ->
                application.contentResolver.openInputStream(uri)?.use { inputStream ->
                    inputStream.readBytes()
                } ?: byteArrayOf()
            }

            val entry = DiaryEntity(
                title = title,
                contents = contents,
                images = imageByteArrays
            )
            roomRepository.insert(entry)
        }
    }

    fun getPreSignedUrl(title: String, contents: String, fileList: List<PreSignedUploadUrlTemp>) {
        val getPreSignedUrlRequest = GetPreSignedUrlRequest().apply {
            this.presignedUploadUrlRequests = fileList.map { temp ->
                PreSignedUploadUrl(
                    fileType = temp.fileType,
                    fileName = temp.fileName,
                    fileContentType = temp.fileContentType,
                    fileContentLength = temp.fileContentLength
                )
            }
        }
        viewModelScope.launch {
            val response =
                getPreSignedUrlUseCase.execute(this@UploadDiaryViewModel, getPreSignedUrlRequest)
            response?.let {
                uploadFile(title, contents, it, fileList)
                Log.d("EJ_LOG", "getPreSignedUrl : $it")
            }
        }
    }

    private fun uploadFile(
        title: String,
        contents: String,
        getPreSignedUrlResponse: GetPreSignedUrlResponse,
        fileList: List<PreSignedUploadUrlTemp>
    ) {
        val preSignedUploadUrlList = getPreSignedUrlResponse.presignedUploadFiles
        var uploadSuccess = true
        viewModelScope.launch {
            fileList.forEachIndexed { index, item ->
                val url = preSignedUploadUrlList[index].presignedUploadUrl
                val file = File(item.filePath)
                val contentType = item.fileContentType
                val requestFile = RequestBody.create(contentType.toMediaTypeOrNull(), file)

                try {
                    val response =
                        uploadFileUseCase.execute(this@UploadDiaryViewModel, url, requestFile)
                    response?.let {
                        Log.d("EJ_LOG", "uploadFile : $it")
                    } ?: run {
                        uploadSuccess = false
                        Log.e("EJ_LOG", "Upload failed for file: ${item.filePath}")
                    }
                } catch (e: Exception) {
                    uploadSuccess = false
                    Log.e("EJ_LOG", "Upload exception for file: ${item.filePath}", e)
                }
            }

            if (uploadSuccess) {
                uploadPost(
                    title,
                    contents,
                    getPreSignedUrlResponse.presignedUploadFiles.map { it.fileUploadId })
            } else {
                _postErrorEvent.emit("file upload fail")
            }
        }
    }

    fun uploadPost(title: String, contents: String, idList: List<String>? = null) {
        viewModelScope.launch {
            val uploadPostRequest = UploadPostRequest().apply {
                this.title = title
                this.content = contents
                if (idList != null) this.postImageFileUploadIds = idList
            }
            val response = uploadPostUseCase.execute(this@UploadDiaryViewModel, uploadPostRequest)
            response?.let {
                Log.d("EJ_LOG", "uploadPost : $response")
            }
        }
    }
}