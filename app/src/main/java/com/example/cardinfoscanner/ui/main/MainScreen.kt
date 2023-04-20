package com.example.cardinfoscanner.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.cardinfoscanner.R
import com.example.cardinfoscanner.navigateSingleTopTo
import com.example.cardinfoscanner.ui.navigation.graph.cameraGraph
import com.example.cardinfoscanner.ui.navigation.graph.errorGraph
import com.example.cardinfoscanner.ui.navigation.graph.resultGraph

enum class BottomNavItem(val route: String, @DrawableRes val icon: Int, @StringRes val title: Int) {
    Notes("", R.drawable.ic_note_list_bulleted, R.string.bottom_navigation_note_list_title),
    Scan("", R.drawable.ic_camera, R.string.bottom_navigation_scan_title),
    Setting("", R.drawable.ic_settings, R.string.bottom_navigation_settings_title)
}

@Composable
fun CardScannerApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val items = listOf(
                BottomNavItem.Notes,
                BottomNavItem.Scan,
                BottomNavItem.Setting,
            )
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFF3F414E),
                tonalElevation = 5.dp,
                modifier = Modifier
                    .offset(y = 0.dp)
                    .navigationBarsPadding()
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = stringResource(id = item.title)
                            )
                        },
                        label = {
                            Text(
                                stringResource(id = item.title),
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Normal,
                                fontSize = 8.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = Color.Gray
                        ),
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        alwaysShowLabel = true,
                        onClick = {
                            navController.navigateSingleTopTo(item.route)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Destination.cameraHomeRoute,
            modifier = Modifier.padding(paddingValues)
        ) {
            cameraGraph(
                navController = navController,
                startDestination = Destination.cameraRoute,
                route = Destination.cameraHomeRoute
            )
            resultGraph(
                navController = navController,
                startDestination = Destination.resultRout,
                route = Destination.resultHomeRout
            )
            errorGraph(
                navController = navController,
                startDestination = Destination.errorRout,
                route = Destination.errorHomeRout
            )
        }
    }
}