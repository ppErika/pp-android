package com.pp.community.activity.terms

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pp.community.activity.main.ui.TermsOfUseScreen
import com.pp.community.base.BaseActivity
import com.pp.community.ui.CommonCompose
import com.pp.community.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsOfUseActivity : BaseActivity<MainViewModel>() {
    private lateinit var navController: NavController
    override val viewModel: MainViewModel by viewModels()
    override fun observerViewModel() {
    }

    @Composable
    override fun ComposeUi() {
        navController = rememberNavController()
        val appBarTitle = remember {
            mViewModel.appBarTitle
        }.value
        val isSelectedTerms1 = remember {
            mViewModel.isSelectedTerms1
        }
        val isSelectedTerms2 = remember {
            mViewModel.isSelectedTerms2
        }
        val isSelectedAll = remember {
            mViewModel.isSelectedTermsAll
        }
        mViewModel.setAppBarTitle("이용약관")
        Column(Modifier.fillMaxSize()) {
            CommonCompose.CommonAppBarUI(title = appBarTitle, isBackPressed = false) {}
            TermsOfUseScreen(
                isSelectedTerms1 = isSelectedTerms1,
                isSelectedTerms2 = isSelectedTerms2,
                isSelectedAll = isSelectedAll
            ) {
                mViewModel.getOauthToken()
                finish()
            }

        }

    }

    override fun init() {
        initData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun initData() {
        val idToken = intent.getStringExtra("idToken") ?: ""
        mViewModel.setKakaoIdToken(idToken)
    }

}
