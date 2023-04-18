package com.example.cardinfoscanner

import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

interface Destination {
    val route: String
    val screen: @Composable (navController: NavHostController, arguments: Bundle) -> Unit

    companion object {
        internal const val cameraHomeRoute = "home/camera"
        internal const val permissionRoute = "home/camera/permission"
        internal const val cameraRoute = "home/camera/scan"
        internal const val resultHomeRout = "home/result"
        internal const val resultRout = "home/result/view"
        internal const val errorHomeRout = "home/error"
        internal const val errorRout = "home/error/view"
    }
}

