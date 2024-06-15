package com.pp.pp.activity.comment

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pp.pp.R
import com.pp.pp.activity.comment.ui.CommentUI
import com.pp.pp.base.BaseActivity
import com.pp.pp.ui.CommonCompose
import com.pp.pp.viewmodel.comment.CommentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentActivity : BaseActivity<CommentViewModel>() {
    override val viewModel: CommentViewModel by viewModels()

    override fun observerViewModel() {
    }

    @Composable
    override fun ComposeUi() {
        val commentList = remember{
            mViewModel.commentList
        }
        Column(Modifier.fillMaxSize()) {
            CommonCompose.CommonAppBarUI(title = stringResource(id = R.string.common_comment), isBackPressed = true){

            }
            CommentUI(
                list = commentList
            ){

            }
        }
    }

    override fun init() {
        // 전달된 Intent에서 값을 받음
        val postId = intent.getIntExtra("postId", 0)
        Log.d("EJ_LOG", "postId : $postId")
        with(mViewModel){
            setPostId(postId)
            getCommentList()
        }
    }

}