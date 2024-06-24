package com.pp.community.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pp.community.R
import com.pp.community.ui.theme.color_black
import com.pp.community.ui.theme.color_main

object CommonCompose {
    @Composable
    fun CommonAppBarUI(
        title: String,
        titleColor: Color = color_black,
        titleWeight: FontWeight = FontWeight.Bold,
        isBackPressed: Boolean = true,
        onBackEvent: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (isBackPressed) {
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                ) {
                    BackPressedUI(iconColor = color_black) {
                        onBackEvent()
                    }
                }
            }
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = title,
                color = titleColor,
                textAlign = TextAlign.Center,
                fontWeight = titleWeight,
                fontSize = 20.sp,
                fontFamily = getRobotoFontFamily()
            )

            if (isBackPressed)
                Spacer(modifier = Modifier.weight(1f))
        }
    }

    @Composable
    inline fun BackPressedUI(
        painter: Painter = painterResource(id = R.drawable.ic_arrow_back),
        iconColor: Color,
        crossinline onClicked: () -> Unit,
    ) {
        Image(
            modifier = Modifier
                .clickable {
                    onClicked()
                },
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(iconColor)
        )
    }

    @Composable
    fun CommonButton(
        text: String,
        modifier: Modifier = Modifier,
        isEnabled: Boolean = true,
        onClick: () -> Unit
    ) {
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = color_main,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            ),
            enabled = isEnabled,
            shape = RoundedCornerShape(5.dp),
            modifier = modifier
                .padding(top = 35.dp),
        ) {
            Text(text = text)
        }
    }

    @Composable
    fun CommonDialog(
        isShow: Boolean,
        content: String,
        onClickOk: () -> Unit,
        onClickCancel: () -> Unit
    ) {
        if (isShow) {
            AlertDialog(
                onDismissRequest = { onClickCancel() },
                confirmButton = {
                    Button(
                        onClick = {
                            onClickOk()
                        }) {
                        Text("확인")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            onClickCancel()
                        }) {
                        Text("취소")
                    }
                },
                text = {
                    Text(
                        text = content,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    }
}
