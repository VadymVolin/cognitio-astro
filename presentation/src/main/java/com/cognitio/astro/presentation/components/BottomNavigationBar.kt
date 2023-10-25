package com.cognitio.astro.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cognitio.astro.presentation.navigation.BaseRoute

@Composable
fun BottomNavigationBar(
    items: List<BaseRoute>,
    navigationController: NavHostController,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        val navBackStackEntry by navigationController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    MaterialTheme.colorScheme.onSecondaryContainer,
                    MaterialTheme.colorScheme.onSurface,
                    MaterialTheme.colorScheme.secondaryContainer,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    MaterialTheme.colorScheme.onSurfaceVariant
                ),
                icon = {
                    when (item.route) {
                        BaseRoute.PODRoute.route -> Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = item.route
                        )
                        BaseRoute.GalleryRoute.route -> Icon(
                            imageVector = Icons.Filled.Image,
                            contentDescription = item.route
                        )
                        BaseRoute.SettingsRoute.route -> Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = item.route
                        )
                    }
                },
                label = { Text(stringResource(id = item.stringResourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    if (currentDestination?.hierarchy?.any { it.route == item.route } != true) {
                        navigationController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navigationController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // re-selecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }
                    }
                })
        }
    }
}