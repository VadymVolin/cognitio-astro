package com.cognitio.astro.presentation.activity

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.cognitio.astro.presentation.components.BottomNavigationBar
import com.cognitio.astro.presentation.navigation.BaseRoute
import com.cognitio.astro.presentation.navigation.BottomBarNavHost


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainActivityLayout() {
    val items = listOf(
        BaseRoute.PODRoute, BaseRoute.GalleryRoute, BaseRoute.CameraRoute, BaseRoute.SettingsRoute
    )
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            BottomNavigationBar(items = items, navigationController = navController)
        }
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .consumeWindowInsets(it)
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal,
                ),
            )) {
            BottomBarNavHost(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }
    }
}