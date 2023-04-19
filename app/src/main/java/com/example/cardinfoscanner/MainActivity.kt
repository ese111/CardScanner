package com.example.cardinfoscanner

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.cardinfoscanner.Destination.Companion.cameraHomeRoute
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.errorHomeRout
import com.example.cardinfoscanner.Destination.Companion.errorRout
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
import com.example.cardinfoscanner.Destination.Companion.resultHomeRout
import com.example.cardinfoscanner.Destination.Companion.resultRout
import com.example.cardinfoscanner.ui.navigation.graph.cameraGraph
import com.example.cardinfoscanner.ui.navigation.graph.errorGraph
import com.example.cardinfoscanner.ui.navigation.graph.resultGraph
import com.example.cardinfoscanner.ui.theme.CardInfoScannerTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
        onBackPressedDispatcher.addCallback { finish() }
    }
}
fun Context.getOutputDirectory(): File {
    return File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")

}
@Composable
fun CardScannerApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = cameraHomeRoute,
        modifier = Modifier
    ) {
        cameraGraph(
            navController = navController,
            startDestination = cameraRoute,
            route = cameraHomeRoute
        )
        resultGraph(
            navController = navController,
            startDestination = resultRout,
            route = resultHomeRout
        )
        errorGraph(
            navController = navController,
            startDestination = errorRout,
            route = errorHomeRout
        )
    }
}

