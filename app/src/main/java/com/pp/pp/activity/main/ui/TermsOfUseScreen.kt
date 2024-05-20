package com.pp.pp.activity.main.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pp.pp.R
import com.pp.pp.ui.CommonCompose.CommonButton
import com.pp.pp.ui.CustomModifier.removeEffectClickable
import com.pp.pp.ui.theme.color_000b70
import com.pp.pp.ui.theme.color_bbbbbb

@Composable
fun TermsOfUseScreen(
    isSelectedTerms1: MutableState<Boolean>,
    isSelectedTerms2: MutableState<Boolean>,
    isSelectedAll: MutableState<Boolean>,
    onclickEvent: () -> Unit
) {

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        TermsOfUseItemUI(item = "개인정보 처리방침 (필수)", isSelected = isSelectedTerms1.value,
            onclickEvent = {
                isSelectedTerms1.value = !isSelectedTerms1.value
                isSelectedAll.value = isSelectedTerms1.value && isSelectedTerms2.value
            })
        TermsOfUseItemUI(item = "서비스 이용약관 (필수)", isSelected = isSelectedTerms2.value,
            onclickEvent = {
                isSelectedTerms2.value = !isSelectedTerms2.value
                isSelectedAll.value = isSelectedTerms1.value && isSelectedTerms2.value
            })
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.removeEffectClickable {
                isSelectedAll.value = !isSelectedAll.value
                isSelectedTerms1.value = isSelectedAll.value
                isSelectedTerms2.value = isSelectedAll.value
            }
        ) {
            Icon(
                modifier = Modifier.padding(end = 10.dp),
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Localized description",
                tint = if (isSelectedAll.value) color_000b70 else color_bbbbbb
            )
            Text(text = stringResource(id = R.string.terms_of_use_agree_all))
        }

        CommonButton(
            text = stringResource(id = R.string.btn_terms_of_use_agree),
            onClick = {onclickEvent()},
            modifier = Modifier.fillMaxWidth(),
            isEnabled = isSelectedAll.value
        )
    }
}

@Composable
fun TermsOfUseItemUI(isSelected: Boolean, item: String, onclickEvent: () -> Unit) {
    Row(
        modifier = Modifier
            .removeEffectClickable {
                onclickEvent()
            }
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .border(width = 1.dp, color = color_bbbbbb, shape = RoundedCornerShape(5.dp))
            .padding(10.dp)
    ) {
        Icon(
            modifier = Modifier.padding(end = 10.dp),
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Localized description",
            tint = if (isSelected) color_000b70 else color_bbbbbb
        )
        Text(text = item)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Localized description"
        )
    }
}