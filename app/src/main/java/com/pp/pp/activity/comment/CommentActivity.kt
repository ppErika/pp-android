package com.pp.pp.activity.comment

import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pp.pp.R
import com.pp.pp.base.BaseActivity
import com.pp.pp.ui.CommonCompose
import com.pp.pp.viewmodel.comment.CommentViewModel
import com.pp.pp.viewmodel.comment.ui.CommentUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentActivity : BaseActivity<CommentViewModel>() {
    override val viewModel: CommentViewModel by viewModels()

    override fun observerViewModel() {
    }

    @Composable
    override fun ComposeUi() {
        Column(Modifier.fillMaxSize()) {
            CommonCompose.CommonAppBarUI(title = stringResource(id = R.string.common_comment), isBackPressed = true){

            }
            CommentUI()
        }
    }

    override fun init() {
    }

}