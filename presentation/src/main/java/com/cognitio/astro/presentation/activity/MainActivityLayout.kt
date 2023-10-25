package com.cognitio.astro.presentation.activity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cognitio.astro.presentation.components.BottomNavigationBar
import com.cognitio.astro.presentation.navigation.BaseRoute
import com.cognitio.astro.presentation.navigation.BottomBarNavHost
import com.cognitio.astro.presentation.screen.camera.CameraScreen

@Composable
fun MainActivityLayout(items: List<BaseRoute>) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isCameraOpened = remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            BottomNavigationBar(items = items, navigationController = navController)
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (navBackStackEntry?.destination?.route?.equals(BaseRoute.GalleryRoute.route) == true) {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = CircleShape,
                    onClick = {
                        if (!isCameraOpened.value) {
                            isCameraOpened.value = true
                        }
                    }
                ) {
                    Icon(Icons.Filled.PhotoCamera, Icons.Filled.PhotoCamera.name)
                }
            }
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
    if (isCameraOpened.value) {
        CameraScreen(setShowCameraScreen = {
            isCameraOpened.value = it
        })
    }
}