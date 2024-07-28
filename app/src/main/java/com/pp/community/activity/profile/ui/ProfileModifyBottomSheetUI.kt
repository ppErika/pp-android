package com.pp.community.activity.profile.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pp.community.ui.CommonCompose
import com.pp.community.ui.CustomModifier.removeEffectClickable
import com.pp.community.ui.theme.color_white

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProfileModifyBottomSheetUI(
    isShow: Boolean,
    selectedProfileImage: MutableState<String>,
    inputNickname: MutableState<String>,
    onclickProfileImage: () -> Unit,
    onDismiss: () -> Unit,
    onModifyEvent: () -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val keyboardController = LocalSoftwareKeyboardController.current

    if (isShow) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = modalBottomSheetState
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlideImage(
                    model = selectedProfileImage.value,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .removeEffectClickable { onclickProfileImage() }
                        .size(58.dp)
                        .clip(CircleShape)
                )
                BasicTextField(
                    value = inputNickname.value,
                    onValueChange = {
                        inputNickname.value = it
                        Log.d("EJ_LOG","onValueChange : ${inputNickname.value} / $it")
                                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide() // 키보드 숨기기
                    }),
                    modifier = Modifier
                        .padding(10.dp)
                        .background(shape = RoundedCornerShape(4.dp), color = color_white)
                        .padding(10.dp)
                )
                CommonCompose.CommonButton(
                    text = "프로필 수정",
                    onClick = { onModifyEvent() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 20.dp)
                )
            }
        }
    }

}