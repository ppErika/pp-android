package com.pp.pp.activity.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pp.pp.viewmodel.MainViewModel
import com.pp.pp.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {
    override val viewModel: MainViewModel by viewModels()
    override fun observerViewModel() {
    }

    @Composable
    override fun ComposeUi() {

    }

    override fun init() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }

}
