package com.example.cardinfoscanner.ui.navigation.destination.setting

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.stateholder.setting.rememberSettingState
import com.example.cardinfoscanner.ui.setting.SettingScreen

object SettingDestination: Destination {
    override val route = Destination.settingRout
    override val screen: @Composable (NavHostController, Bundle?, AppState?) -> Unit = { navController, _, _ ->
        navController.currentBackStackEntry?.let {
            val state = rememberSettingState()
            SettingScreen(
                state = state,
                onClickBackButton = {
                    navController.navigateUp()
                },
                startActivity = { title ->
                    state.startActivity(title)
                }
            )
        }
    }
}