package com.example.cardinfoscanner.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cardinfoscanner.Destination
import com.example.cardinfoscanner.Destination.Companion.cameraHomeRoute
import com.example.cardinfoscanner.Destination.Companion.cameraRoute
import com.example.cardinfoscanner.Destination.Companion.noteHomeRout
import com.example.cardinfoscanner.Destination.Companion.noteListRout
import com.example.cardinfoscanner.Destination.Companion.settingHomeRout
import com.example.cardinfoscanner.Destination.Companion.settingRout
import com.example.cardinfoscanner.MainViewModel
import com.example.cardinfoscanner.R
import com.example.cardinfoscanner.navigateSingleTopTo
import com.example.cardinfoscanner.navigateSingleTopToGraph
import com.example.cardinfoscanner.ui.common.BottomNavBar
import com.example.cardinfoscanner.ui.navigation.graph.cameraGraph
import com.example.cardinfoscanner.ui.navigation.graph.noteGraph
import com.example.cardinfoscanner.ui.navigation.graph.settingGraph

enum class BottomNavItem(val route: String, @DrawableRes val icon: Int, @StringRes val title: Int) {
    Notes(noteListRout, R.drawable.ic_note_list_bulleted, R.string.bottom_navigation_note_list_title),
    Setting(settingRout, R.drawable.ic_settings, R.string.bottom_navigation_settings_title)
}

@Composable
fun CardScannerApp(
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()
    var bottomBarVisible by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val items = listOf(
                BottomNavItem.Notes,
                BottomNavItem.Setting
            )
            if(bottomBarVisible) {
                BottomNavBar(items = items, navBackStackEntry = navBackStackEntry, onClickBottomMenu = { item -> navController.navigateSingleTopToGraph(item.route) })
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = noteHomeRout,
            modifier = Modifier.padding(paddingValues)
        ) {
            noteGraph(
                navController = navController,
                startDestination = noteListRout,
                route = noteHomeRout,
                mainViewModel = mainViewModel
            )
            cameraGraph(
                navController = navController,
                startDestination = cameraRoute,
                route = cameraHomeRoute,
                mainViewModel = mainViewModel
            )
            settingGraph(
                navController = navController,
                startDestination = settingRout,
                route = settingHomeRout,
                mainViewModel = mainViewModel
            )
        }
    }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        bottomBarVisible = when(destination.route) {
            noteListRout, settingRout -> {
                true
            }
            else -> {
                false
            }
        }
    }
}