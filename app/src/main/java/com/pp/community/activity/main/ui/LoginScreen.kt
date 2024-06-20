package com.pp.community.activity.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pp.community.ui.CustomModifier.removeEffectClickable
import com.pp.community.ui.getRobotoFontFamily
import com.pp.community.ui.theme.color_fdd73f

@Composable
fun LoginScreen(
    loginClickEvent:()->Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "커뮤니티 둘러보기",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = getRobotoFontFamily()
        )
        Text(
            text = "커뮤니티는 피피노트 회원들만 이용할 수 있어요!",
            fontSize = 12.sp,
            fontFamily = getRobotoFontFamily()
        )
        Row(
            modifier = Modifier
                .removeEffectClickable {
                    loginClickEvent()
                }
                .fillMaxWidth()
                .padding(top = 30.dp)
                .background(color = color_fdd73f, shape = RoundedCornerShape(10.dp))
                .padding(10.dp),

            ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "카카오로 시작하기",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}