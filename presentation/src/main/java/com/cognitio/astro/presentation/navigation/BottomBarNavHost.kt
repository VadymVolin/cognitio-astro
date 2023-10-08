package com.cognitio.astro.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cognitio.astro.presentation.screen.camera.CameraScreen
import com.cognitio.astro.presentation.screen.gallery.GalleryScreen
import com.cognitio.astro.presentation.screen.nasa.screen.NasaPictureOfTheDayScreen
import com.cognitio.astro.presentation.screen.settings.SettingsScreen

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
        NasaPictureOfTheDayScreen(navigationController = navController)
    }
    composable(BaseRoute.GalleryRoute.route) {
        GalleryScreen(navigationController = navController)
    }
    composable(BaseRoute.CameraRoute.route) {
        CameraScreen(navigationController = navController)
    }
    composable(BaseRoute.SettingsRoute.route) {
        SettingsScreen(navigationController = navController)
    }
}