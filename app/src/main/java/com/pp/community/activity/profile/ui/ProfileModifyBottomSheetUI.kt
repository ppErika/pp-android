package com.pp.community.activity.profile.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileModifyBottomSheetUI(
    openBottomSheetState: SheetState
) {
    ModalBottomSheet(onDismissRequest = { openBottomSheetState}) {

    }
}