package com.example.cardinfoscanner.stateholder.setting

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.cardinfoscanner.stateholder.common.BaseUiState
import com.example.cardinfoscanner.stateholder.common.rememberUiState
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Stable
class SettingState(
    val uiState: BaseUiState
) {
    val startActivity: (String) -> Unit = {
        OssLicensesMenuActivity.setActivityTitle(it)
        uiState.context.startActivity(Intent(uiState.context, OssLicensesMenuActivity::class.java))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberSettingState(
    uiState: BaseUiState = rememberUiState()
) = remember(uiState) {
    SettingState(
        uiState = uiState
    )
}