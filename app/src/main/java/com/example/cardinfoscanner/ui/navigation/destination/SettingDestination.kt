package com.example.cardinfoscanner.ui.navigation.destination

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.MainViewModel

object SettingDestination: Destination {
    override val route = Destination.settingRout
    private const val errorKey = "title"
    val routeWithArgs = "$route/{$errorKey}"
    val arguments = listOf(
        navArgument(errorKey) { type = NavType.StringType }
    )
    override val screen: @Composable (NavHostController, Bundle?, MainViewModel?) -> Unit = { navController, bundle, _ ->
        navController.currentBackStackEntry?.let {
            bundle?.getString(errorKey)?.let {
            }
        }
    }
}