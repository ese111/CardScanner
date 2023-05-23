package com.example.cardinfoscanner.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.cardinfoscanner.R
import com.example.cardinfoscanner.stateholder.setting.SettingState
import com.example.cardinfoscanner.stateholder.setting.rememberSettingState
import com.example.cardinfoscanner.ui.common.BasicTopAppBar
import com.google.android.gms.oss.licenses.OssLicensesActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun SettingScreen(
    state: SettingState = rememberSettingState(),
    onClickBackButton : () -> Unit = {},
    startActivity:(String) -> Unit = {}
) {
    val title = stringResource(R.string.oss_title)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BasicTopAppBar(
                title = stringResource(id = R.string.setting_page_setting_text),
                backButtonVisible = true,
                onClickBackButton = onClickBackButton
            )
        }
    ) { paddingValues ->  
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 25.dp)
        ) {
            Text(
                text = stringResource(id = R.string.setting_page_info_text),
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.Gray
            )
            SettingScreenItem(
                icon = Icons.Default.Info,
                text = title,
                onClickItem = {
                    startActivity(title)
                }
            )
            Divider()
        }
    }
}

@Composable
fun SettingScreenItem(
    icon: ImageVector = Icons.Default.Settings,
    text: String = stringResource(id = R.string.setting_page_setting_text),
    onClickItem: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 18.dp)
            .clickable { onClickItem() }
    ) {
        Icon(imageVector = icon, contentDescription = "icon")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingItem() {
    SettingScreenItem(
        icon = Icons.Default.Info
    )
}
@Preview(showBackground = true)
@Composable
private fun SettingScreenPreview() {
    SettingScreen()
}