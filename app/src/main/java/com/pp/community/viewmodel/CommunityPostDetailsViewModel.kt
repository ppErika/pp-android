package com.pp.community.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pp.community.base.BaseViewModel
import com.pp.domain.model.post.PostDetailsModel
import com.pp.domain.usecase.post.GetPostDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityPostDetailsViewModel @Inject constructor(
    private val getPostDetailsUseCase: GetPostDetailsUseCase
): BaseViewModel() {
    private val _postDetails = MutableStateFlow<PostDetailsModel?>(null)
    val postDetails: StateFlow<PostDetailsModel?> = _postDetails

    suspend fun getPostDetails(postId: Int) {
        viewModelScope.launch {
            val response = getPostDetailsUseCase.execute(this@CommunityPostDetailsViewModel, postId)
            Log.d("ErikaLog", "getPostDetails: $response")
            response?.let{
                _postDetails.value = response.post
            }
        }
    }
}