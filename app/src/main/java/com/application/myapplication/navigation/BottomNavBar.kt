package com.application.myapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.application.myapplication.R

sealed class Screen(val route: String, val title: String) {
    object Dashboard : Screen("dashboard", "Dashboard")
    object Payment : Screen("payment", "Payment")
    object Schedule : Screen("schedule", "Schedule")
    object Notification : Screen("notification", "Notification")
}

@Composable
fun getScreenIcon(screen: Screen): ImageVector {
    return when (screen) {
        is Screen.Dashboard -> ImageVector.vectorResource(id = R.drawable.bill_icon)
        is Screen.Payment -> ImageVector.vectorResource(id = R.drawable.receipt)
        is Screen.Schedule -> ImageVector.vectorResource(id = R.drawable.chat_bubble_icon)
        is Screen.Notification -> ImageVector.vectorResource(id = R.drawable.bell)
    }
}



@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        Screen.Dashboard,
        Screen.Payment,
        Screen.Schedule,
        Screen.Notification
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = getScreenIcon(screen), contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


