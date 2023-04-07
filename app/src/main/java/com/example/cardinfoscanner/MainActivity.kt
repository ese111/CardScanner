package com.example.cardinfoscanner

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.ui.theme.CardInfoScannerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CardInfoScannerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CardScannerApp()
                }
            }
        }
    }
}
fun Context.getOutputDirectory(): File {
    return File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")

}
@Composable
fun CardScannerApp() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { Text(text = "Card Scanner", modifier = Modifier.padding(18.dp)) }
    ) { paddingValues ->
        CameraGraph(
            navController = navController,
            startDestination = permissionRoute,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

