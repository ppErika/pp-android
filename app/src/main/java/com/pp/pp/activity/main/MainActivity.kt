package com.pp.pp.activity.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kakao.sdk.user.UserApiClient
import com.pp.pp.R
import com.pp.pp.activity.main.route.MainNav
import com.pp.pp.activity.main.ui.DiaryScreen
import com.pp.pp.activity.main.ui.LoginScreen
import com.pp.pp.activity.main.ui.SettingScreen
import com.pp.pp.activity.main.ui.TermsOfUseScreen
import com.pp.pp.activity.terms.TermsOfUseActivity
import com.pp.pp.base.BaseActivity
import com.pp.pp.ui.CommonCompose
import com.pp.pp.ui.theme.color_main
import com.pp.pp.ui.theme.color_white
import com.pp.pp.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {
    private lateinit var navController: NavController
    override val viewModel: MainViewModel by viewModels()
    override fun observerViewModel() {
        mViewModel.run {
            movePageEvent.onEach {
                when (it) {
                    "termsOfUse" -> {
                        val intent = Intent(this@MainActivity, TermsOfUseActivity::class.java)
                        intent.putExtra("idToken", getKakaoIdToken())
                        startActivity(intent)
                    }
                }
            }.launchIn(this@MainActivity.lifecycleScope)
        }
    }

    @Composable
    override fun ComposeUi() {
        navController = rememberNavController()
        val appBarTitle = remember {
            mViewModel.appBarTitle
        }.value
        val isLogin = remember {
            mViewModel.isLogin
        }.value
        val communityPostList = remember {
            mViewModel.communityPostList
        }
        val packageInfo =
            applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0)

        LaunchedEffect(key1 = isLogin) {
            mViewModel.getPostList()
        }
        Column(Modifier.fillMaxSize()) {
            CommonCompose.CommonAppBarUI(title = appBarTitle, isBackPressed = false) {}
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController as NavHostController,
                startDestination = MainNav.MyDiary.name
            ) {
                // 나의 일기
                composable(route = MainNav.MyDiary.name) {
                    mViewModel.setAppBarTitle(MainNav.MyDiary.name)
                    DiaryScreen(emptyList())
                }
                // 커뮤니티
                composable(route = MainNav.Community.name) {
                    mViewModel.setAppBarTitle(MainNav.Community.name)
                    when (isLogin) {
                        true -> DiaryScreen(
                            communityPostList = communityPostList
                        )

                        false -> LoginScreen(

                        ) {
                            kakaoLogin()
                        }
                    }
                }
                // 설정
                composable(route = MainNav.Setting.name) {
                    mViewModel.setAppBarTitle(MainNav.Setting.name)
                    SettingScreen(
                        isLogin = isLogin,
                        version = packageInfo.versionName ?: "Unknown"
                    ) {

                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = color_white)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MainNav.values().forEach { item ->
                    val selected = item.name == appBarTitle

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable { moveNavigate(item.name) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = when (item.name) {
                                "MyDiary" -> painterResource(id = R.drawable.ic_navi_mydiary)
                                "Community" -> painterResource(id = R.drawable.ic_navi_community)
                                else -> painterResource(id = R.drawable.ic_navi_setting)
                            },
                            contentDescription = "${item.name} Icon",
                            tint = if (selected) color_main else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = item.name,
                            fontWeight = FontWeight.Bold,
                            color = if (selected) color_main else Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
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
        mViewModel.getAccessToken()
    }

    private fun kakaoLogin() {
        UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
            if (error != null) {
                Log.e("EJ_LOG", "로그인 실패", error)
            } else if (token != null) {
                mViewModel.isUserRegistered(token.idToken ?: "")
            }
        }
    }

    private fun moveNavigate(destination: String) {
        if (::navController.isInitialized) {
            navController.navigate(destination)
        }
    }
}
