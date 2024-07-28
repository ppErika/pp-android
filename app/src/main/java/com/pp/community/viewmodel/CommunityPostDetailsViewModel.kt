package com.pp.community.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pp.community.base.BaseViewModel
import com.pp.community.utils.DecodeJwtUtil
import com.pp.community.widget.SingleFlowEvent
import com.pp.domain.model.post.GetPostDetailsResponse
import com.pp.domain.usecase.datastore.GetAccessTokenUseCase
import com.pp.domain.usecase.post.DeletePostUseCase
import com.pp.domain.usecase.post.GetPostDetailsUseCase
import com.pp.domain.usecase.post.ReportPostUseCase
import com.pp.domain.usecase.post.ThumbsSidewaysPostUseCase
import com.pp.domain.usecase.post.ThumbsUpPostUseCase
import com.pp.domain.usecase.users.BlockUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CommunityPostDetailsViewModel @Inject constructor(
    private val getPostDetailsUseCase: GetPostDetailsUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val reportPostUseCase: ReportPostUseCase,
    private val thumbsUpPostUseCase: ThumbsUpPostUseCase,
    private val thumbsSidewaysPostUseCase: ThumbsSidewaysPostUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val decodeJwtUtil: DecodeJwtUtil,
    private val blockUserUseCase: BlockUserUseCase
) : BaseViewModel() {
    private val _postDetails = MutableStateFlow<GetPostDetailsResponse?>(null)
    val postDetails: StateFlow<GetPostDetailsResponse?> = _postDetails
    private val _reportPostSuccessEvent = SingleFlowEvent<String>()
    val reportPostSuccessEvent = _reportPostSuccessEvent.flow
    private val _deletePostSuccessEvent = SingleFlowEvent<String>()
    val deletePostSuccessEvent = _deletePostSuccessEvent.flow
    private val _blockUserSuccessEvent = SingleFlowEvent<String>()
    val blockUserSuccessEvent = _blockUserSuccessEvent.flow

    private var postId = -1

    fun setPostId(id: Int) {
        postId = id
    }

    fun getPostId(): Int {
        return postId
    }

    suspend fun getAccessToken(): String? {
        return withContext(Dispatchers.IO) {
            val token = getAccessTokenUseCase.invoke().first()
            Log.d("EJ_LOG", "getAccessToken : $token")
            token
        }
    }

    fun getUserId(): Int {
        var id = -1
        viewModelScope.launch {
            id = decodeJwtUtil.getUserId(token = getAccessToken() ?: "").toInt()
        }
        return id
    }

    fun getPostDetails() {
        viewModelScope.launch {
            val response = getPostDetailsUseCase.execute(this@CommunityPostDetailsViewModel, postId)
            Log.d("ErikaLog", "getPostDetails: $response")
            response?.let {
                _postDetails.value = it
            }
        }
    }

    fun deletePost() {
        viewModelScope.launch {
            val response = deletePostUseCase.execute(this@CommunityPostDetailsViewModel, postId)
            response?.let {
                Log.d("EJ_LOG", "deletePost : $it")
                _deletePostSuccessEvent.emit(it)
            }
        }
    }

    fun reportPost() {
        viewModelScope.launch {
            val response = reportPostUseCase.execute(this@CommunityPostDetailsViewModel, postId)
            response?.let {
                _reportPostSuccessEvent.emit(it)
            }
        }
    }

    fun blockUser(userId: Int){
        if(userId != -1){
            viewModelScope.launch {
                val response = blockUserUseCase.execute(this@CommunityPostDetailsViewModel, userId)
                response?.let{
                    _blockUserSuccessEvent.emit(it)
                }
            }
        }
    }
}