package com.pp.community.base

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.pp.community.ui.theme.PPTheme
import com.pp.community.ui.theme.color_white

abstract class BaseActivity<VM:ViewModel> : ComponentActivity(){
    protected lateinit var mViewModel: VM
    protected abstract val viewModel: VM

    protected abstract fun observerViewModel()

    @Composable
    abstract fun ComposeUi()
    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val compose = setContent {
            PPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = color_white
                ) {
                    ComposeUi()
                }
            }
        }

        initViewModel()
        observerViewModel()
        init()
        return compose
    }

    @Composable
    fun InitBackHandler(isEnabled: Boolean, onBackPressed:() -> Unit) {
        BackHandler(isEnabled) {
            onBackPressed()
        }
    }

    private fun initViewModel() {
        mViewModel = if (::mViewModel.isInitialized) mViewModel else viewModel
    }

    protected fun showShortToast(msg:String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}