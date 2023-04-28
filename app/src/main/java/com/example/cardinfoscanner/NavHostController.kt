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
fun NavHostController.navigateSingleTopTo(route: String, isInclusive: Boolean = false) =
    navigate(route) {
        popUpTo(route) {
            saveState = true
            inclusive = isInclusive
        }
        launchSingleTop = true
//        restoreState = true
    }
fun NavHostController.navigateClearTo(route: String, isInclusive: Boolean = false) =
    navigate(route) {
        popUpTo(route) {
            inclusive = isInclusive
        }
        launchSingleTop = true
        restoreState = true
    }