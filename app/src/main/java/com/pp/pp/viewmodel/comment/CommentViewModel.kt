package com.pp.pp.viewmodel.comment

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.pp.domain.model.comments.CommentModel
import com.pp.domain.model.comments.GetCommentsRequest
import com.pp.domain.model.comments.PostCommentRequest
import com.pp.domain.usecase.comment.GetCommentsUseCase
import com.pp.domain.usecase.comment.PostCommentUseCase
import com.pp.domain.usecase.comment.ReportCommentUseCase
import com.pp.pp.base.BaseViewModel
import com.pp.pp.widget.SingleFlowEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentsUseCase: GetCommentsUseCase,
    private val postCommentUseCase: PostCommentUseCase,
    private val reportCommentUseCase: ReportCommentUseCase
) : BaseViewModel() {
    var inputComment = mutableStateOf("")
        private set
    var commentList = mutableStateListOf<CommentModel>()
        private set
    private val _reportCommentSuccessEvent = SingleFlowEvent<String>()
    val reportCommentSuccessEvent = _reportCommentSuccessEvent.flow

    private var _postId: Int? = null
    private var _lastId: Int? = null

    fun setPostId(postId: Int) {
        _postId = postId
    }

    fun setLastId(lastId: Int?) {
        _lastId = lastId
    }

    fun getCommentList(isFirst: Boolean = true) {
        _postId?.let {
            Log.d("EJ_LOG", "_lastId______ : $_lastId")
            val commentsRequest = if (isFirst) {
                GetCommentsRequest().apply {
                    this.postId = it
                }
            } else {
                _lastId?.let {
                    GetCommentsRequest().apply {
                        this.postId = it
                        if (_lastId != null) this.lastId = _lastId
                    }
                }
            }
            commentsRequest?.let {
                viewModelScope.launch {
                    val result = commentsUseCase.execute(this@CommentViewModel, commentsRequest)
                    result?.let {
                        Log.d("EJ_LOG", "getcomment reuslt : $it")
                        commentList.addAll(it.comments)
                        if (it.comments.size == 20) {
                            setLastId(commentList.last().id)
                        } else {
                            setLastId(null)
                        }
                        Log.d("EJ_LOG", "_lastId : $_lastId")
                    }
                }
            }

        }
    }

    fun postComment() {
        if (inputComment.value.isNotBlank()) {
            _postId?.let { postId ->
                val postCommentRequest = PostCommentRequest().apply {
                    this.content = inputComment.value
                }
                viewModelScope.launch {
                    val result =
                        postCommentUseCase.execute(
                            this@CommentViewModel,
                            postId,
                            postCommentRequest
                        )
                    Log.d("EJ_LOG", "postcomment reuslt : $result")
                    result?.let {
                        inputComment.value = ""
                        commentList.clear()
                        getCommentList()
                    }
                }
            }
        }
    }

    fun reportComment(commentModel: CommentModel) {
        viewModelScope.launch {
            val result = reportCommentUseCase.execute(this@CommentViewModel, commentModel.id)
            Log.d("EJ_LOG", "reportcomment reuslt : $result")
            result?.let {
                commentList.remove(commentModel)
                _reportCommentSuccessEvent.emit(it)
            }
        }
    }

}