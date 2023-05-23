package com.example.cardinfoscanner.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cardinfoscanner.Destination.Companion.noteListRout
import com.example.cardinfoscanner.Destination.Companion.settingRout
import com.example.cardinfoscanner.MainViewModel
import com.example.cardinfoscanner.R
import com.example.cardinfoscanner.navigateSingleTopToGraph
import com.example.cardinfoscanner.stateholder.app.AppState
import com.example.cardinfoscanner.stateholder.app.rememberAppState
import com.example.cardinfoscanner.ui.common.BottomNavBar
import com.example.cardinfoscanner.ui.navigation.navhost.ScannerNavHost

enum class BottomNavItem(val route: String, @DrawableRes val icon: Int, @StringRes val title: Int) {
    Notes(noteListRout, R.drawable.ic_note_list_bulleted, R.string.bottom_navigation_note_list_title),
    Setting(settingRout, R.drawable.ic_settings, R.string.bottom_navigation_settings_title)
}

@Composable
fun CardScannerApp(
    appState: AppState = rememberAppState(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    Scaffold(
        bottomBar = {
            val navBackStackEntry by appState.navHostController.currentBackStackEntryAsState()

            val items = listOf(
                BottomNavItem.Notes,
                BottomNavItem.Setting
            )
            if(appState.bottomBarVisible) {
                BottomNavBar(items = items, navBackStackEntry = navBackStackEntry, onClickBottomMenu = { item -> appState.navHostController.navigateSingleTopToGraph(item.route) })
            }
        }
    ) { paddingValues ->
        ScannerNavHost(
            modifier = Modifier.padding(paddingValues = paddingValues),
            navController = appState.navHostController,
            appState = appState
        )
    }

    appState.navHostController.addOnDestinationChangedListener { _, destination, _ ->
        appState.bottomBarVisible = when(destination.route) {
            noteListRout, settingRout -> true
            else -> false
        }
    }
}