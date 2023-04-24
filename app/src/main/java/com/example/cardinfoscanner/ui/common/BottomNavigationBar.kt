package com.example.cardinfoscanner.ui.common

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.cardinfoscanner.ui.main.BottomNavItem

@Composable
fun BottomNavBar(
    items: List<BottomNavItem> = emptyList(),
    navBackStackEntry: NavBackStackEntry? = null,
    onClickBottomMenu: (BottomNavItem) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 5.dp,
        modifier = Modifier.navigationBarsPadding()
    ) {
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
                        fontSize = 11.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.White,
                    unselectedTextColor = Color.Gray,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    disabledIconColor = MaterialTheme.colorScheme.onSurface,
                    disabledTextColor = MaterialTheme.colorScheme.tertiary
                ),
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                alwaysShowLabel = false,
                onClick = { onClickBottomMenu(item) }
            )
        }
    }
}