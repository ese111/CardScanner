package com.example.cardinfoscanner

import androidx.navigation.NavHostController

fun NavHostController.navigateSingleTopTo(route: String) =
    navigate(route) {
        popUpTo(route) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }