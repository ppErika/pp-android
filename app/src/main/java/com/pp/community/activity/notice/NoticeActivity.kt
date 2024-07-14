package com.pp.community.activity.notice

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pp.community.activity.notice.route.NoticeNav
import com.pp.community.activity.notice.ui.NoticeDetailScreen
import com.pp.community.activity.notice.ui.NoticeScreen
import com.pp.community.base.BaseActivity
import com.pp.community.ui.CommonCompose
import com.pp.community.utils.customNavigate
import com.pp.community.viewmodel.NoticeViewModel
import com.pp.domain.model.notice.Notice
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeActivity: BaseActivity<NoticeViewModel>() {
    private lateinit var navController: NavController

    override val viewModel: NoticeViewModel by viewModels()

    override fun observerViewModel() {
    }

    @Composable
    override fun ComposeUi() {
        navController = rememberNavController()
        val noticeList = remember {
            mViewModel.noticeList
        }
        Column {
            CommonCompose.CommonAppBarUI(title = "공지사항", isBackPressed = true) {
                Log.d("EJ_LOG","navController.currentDestination?.navigatorName ${navController.currentDestination?.route}")
                when(navController.currentDestination?.route){
                    NoticeNav.Notice.name -> {finish()}
                    NoticeNav.NoticeDetail.name -> {navController.popBackStack()}
                }
            }
            NavHost(  modifier = Modifier.weight(1f),
                navController = navController as NavHostController,
                startDestination = NoticeNav.Notice.name ){
                composable(NoticeNav.Notice.name){
                    NoticeScreen(noticeList){
                        moveNavigate(NoticeNav.NoticeDetail.name,it)
                    }
                }
                composable(NoticeNav.NoticeDetail.name){
                    Log.d("EJ_LOG","noticeDetail ${it.arguments}")
                    it.arguments?.let{bundle ->
                        NoticeDetailScreen(
                            title = bundle.getString("title")?:"",
                            content = bundle.getString("content")?:"",
                            createDate = bundle.getString("createDate")?:""
                        )
                    }

                }

            }

        }
    }

    override fun init() {
        mViewModel.run{
            getNotice()
        }
    }

    private fun moveNavigate(destination: String, notice: Notice){
        if (::navController.isInitialized) {
            navController.customNavigate(
                route = destination,
                args = Bundle().apply {
                    putString("title",notice.title)
                    putString("content",notice.content)
                    putString("createDate",notice.createDate)
                }
            )
        }
    }

}