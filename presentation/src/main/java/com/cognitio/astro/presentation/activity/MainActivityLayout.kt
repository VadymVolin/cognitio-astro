package com.cognitio.astro.presentation.activity

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.cognitio.astro.presentation.components.BottomNavigationBar
import com.cognitio.astro.presentation.navigation.BaseRoute
import com.cognitio.astro.presentation.navigation.BottomBarNavHost

val items = listOf(
    BaseRoute.PODRoute, BaseRoute.GalleryRoute, BaseRoute.CameraRoute, BaseRoute.SettingsRoute
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainActivityLayout(items: List<BaseRoute>) {
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
        FloatingActionButton(
            modifier = Modifier.padding(bottom = 72.dp),
            onClick = {
                Log.d("FORTRA", "MainActivityLayout: CAMERA")
            }) {
            Icon(Icons.Filled.PhotoCamera, Icons.Filled.PhotoCamera.name)
        }
    }
}