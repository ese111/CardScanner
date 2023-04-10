package com.example.cardinfoscanner

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.navigation.compose.rememberNavController
import com.example.cardinfoscanner.Destination.Companion.permissionRoute
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
    }
}
fun Context.getOutputDirectory(): File {
    return File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")

}
@Composable
fun CardScannerApp() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            Text(text = "Card Scanner", modifier = Modifier.padding(18.dp))
        }
    ) { paddingValues ->
        CameraGraph(
            navController = navController,
            startDestination = permissionRoute,
            modifier = Modifier,
            paddingValues = paddingValues
        )
    }
}

