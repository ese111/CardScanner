package com.example.cardinfoscanner

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


fun NavHostController.navigateSingleTopToGraph(route: String) =
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }