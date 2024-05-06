package com.pp.pp.activity.main

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kakao.sdk.user.UserApiClient
import com.pp.pp.R
import com.pp.pp.activity.main.route.MainNav
import com.pp.pp.activity.main.ui.DiaryScreen
import com.pp.pp.activity.main.ui.LoginScreen
import com.pp.pp.activity.main.ui.SettingScreen
import com.pp.pp.base.BaseActivity
import com.pp.pp.ui.CommonCompose
import com.pp.pp.ui.theme.color_000b70
import com.pp.pp.ui.theme.color_white
import com.pp.pp.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {
    override val viewModel: MainViewModel by viewModels()
    override fun observerViewModel() {
        mViewModel.run {
            movePageEvent.onEach {

            }.launchIn(this@MainActivity.lifecycleScope)
        }
    }

    @Composable
    override fun ComposeUi() {
        val navController = rememberNavController()
        val appBarTitle = remember {
            mViewModel.appBarTitle
        }.value
        val isLogin = remember {
            mViewModel.isLogin
        }.value
        Column(Modifier.fillMaxSize()) {
            CommonCompose.CommonAppBarUI(title = appBarTitle, isBackPressed = false) {}
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController, startDestination = MainNav.MyDiary.name
            ) {
                // 나의 일기
                composable(route = MainNav.MyDiary.name) {
                    mViewModel.setAppBarTitle(MainNav.MyDiary.name)
                    DiaryScreen()
                }
                // 커뮤니티
                composable(route = MainNav.Community.name) {
                    mViewModel.setAppBarTitle(MainNav.Community.name)
                    when (isLogin) {
                        true -> DiaryScreen()
                        false -> LoginScreen() {
                            kakaoLogin()
                        }
                    }

                }
                // 설정
                composable(route = MainNav.Setting.name) {
                    mViewModel.setAppBarTitle(MainNav.Setting.name)
                    SettingScreen()
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
                            .clickable { navController.navigate(item.name) },
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Icon(
                            painter = when (item.name) {
                                "MyDiary" -> painterResource(id = R.drawable.ic_navi_mydiary)
                                "Community" -> painterResource(id = R.drawable.ic_navi_community)
                                else -> painterResource(id = R.drawable.ic_navi_setting)
                            },
                            contentDescription = "${item.name} Icon",
                            tint = if (selected) color_000b70 else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = item.name,
                            fontWeight = FontWeight.Bold,
                            color = if (selected) color_000b70 else Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

        }

    }

    override fun init() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun kakaoLogin() {
        UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
            if (error != null) {
                Log.e("EJ_LOG", "로그인 실패", error)
            } else if (token != null) {
                Log.i("EJ_LOG", "로그인 성공 ${token.accessToken}")
                Log.i("EJ_LOG", "로그인 성공 ${token.idToken}")
                Log.i("EJ_LOG", "로그인 성공 ${token.scopes}")
                mViewModel.isUserRegistered(token.idToken ?: "")
            }
        }
    }

}
