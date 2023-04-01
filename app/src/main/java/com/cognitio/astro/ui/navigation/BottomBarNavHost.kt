package com.cognitio.astro.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cognitio.astro.ui.screen.AddScreen
import com.cognitio.astro.ui.screen.HomeScreen
import com.cognitio.astro.ui.screen.SettingsScreen

@Composable
fun BottomBarNavHost(
    modifier: Modifier = Modifier,
    navHostModifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) = NavHost(
    navController = navController,
    startDestination = BaseRoute.HomeRoute.route,
    modifier = navHostModifier
) {
    composable(BaseRoute.HomeRoute.route) {
        HomeScreen(modifier = modifier, navigationController = navController)
    }
    composable(BaseRoute.AddRoute.route) {
        AddScreen(modifier = modifier, navigationController = navController)
    }
    composable(BaseRoute.SettingsRoute.route) {
        SettingsScreen(modifier = modifier, navigationController = navController)
    }
}