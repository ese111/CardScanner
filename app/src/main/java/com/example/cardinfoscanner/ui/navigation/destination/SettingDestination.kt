package com.example.cardinfoscanner.ui.navigation.destination

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.MainViewModel
import com.example.cardinfoscanner.R
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