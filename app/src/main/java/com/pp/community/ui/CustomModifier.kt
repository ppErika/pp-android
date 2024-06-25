package com.pp.community.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

object CustomModifier {
    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    fun Modifier.removeEffectClickable(onClicked:() -> (Unit)) = composed {
        Modifier.clickable(
            interactionSource = remember {
                MutableInteractionSource()
            },
            indication = null
        ){
            onClicked()
        }
    }
}