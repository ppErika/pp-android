package com.pp.pp.viewmodel.comment

import androidx.lifecycle.viewModelScope
import com.pp.domain.model.comments.CommentModel
import com.pp.domain.model.comments.GetCommentsRequest
import com.pp.domain.usecase.comment.GetCommentsUseCase
import com.pp.pp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentsUseCase: GetCommentsUseCase
): BaseViewModel() {
    var commentList = mutableListOf<CommentModel>()
        private set

    private var _postId: Int? = null

    fun setPostId(postId: Int){
        _postId = postId
    }

    fun getCommentList(){
        _postId?.let{
            val commentsRequest = GetCommentsRequest().apply {
                this.postId = it
            }
            viewModelScope.launch {
                val result = commentsUseCase.execute(this@CommentViewModel,commentsRequest)
                result?.let{
                    commentList.addAll(it.comments)
                }
            }
        }
    }
}