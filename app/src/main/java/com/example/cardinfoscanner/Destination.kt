package com.example.cardinfoscanner

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface Destination {
    val route: String
    val screen: @Composable (navController: NavHostController, arguments: Bundle) -> Unit

    companion object {
        internal const val permissionRoute = "home/permission"
        internal const val cameraRoute = "home/camera"
    }
}

