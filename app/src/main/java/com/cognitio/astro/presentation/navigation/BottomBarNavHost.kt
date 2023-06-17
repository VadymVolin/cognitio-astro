package com.cognitio.astro.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cognitio.astro.presentation.screen.AddScreen
import com.cognitio.astro.presentation.screen.home.HomeScreen
import com.cognitio.astro.presentation.screen.SettingsScreen

@Composable
fun BottomBarNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) = NavHost(
    navController = navController,
    startDestination = BaseRoute.HomeRoute.route,
    modifier = modifier
) {
    composable(BaseRoute.HomeRoute.route) {
        HomeScreen(navigationController = navController)
    }
    composable(BaseRoute.AddRoute.route) {
        AddScreen(navigationController = navController)
    }
    composable(BaseRoute.SettingsRoute.route) {
        SettingsScreen(navigationController = navController)
    }
}