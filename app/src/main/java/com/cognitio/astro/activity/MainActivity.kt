package com.cognitio.astro.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cognitio.astro.ui.navigation.BaseRoute
import com.cognitio.astro.ui.screen.AddScreen
import com.cognitio.astro.ui.screen.HomeScreen
import com.cognitio.astro.ui.screen.SettingsScreen
import com.cognitio.astro.ui.theme.CognitioAstroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CognitioAstroTheme {
                // A surface container using the 'background' color from the theme
                MainActivityLayout()
            }
        }
    }
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class
)
@Composable
fun MainActivityLayout() {
    val items = listOf(
        BaseRoute.HomeRoute, BaseRoute.AddRoute, BaseRoute.SettingsRoute
    )
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            BottomNavigationBar(
                items = items, navigationController = navController
            )
        }
    ) {
        val modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .consumeWindowInsets(it)
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Horizontal,
                ),
            )
        Row(modifier = modifier) {
            NavHost(
                navController = navController,
                startDestination = BaseRoute.HomeRoute.route,
                modifier = Modifier.padding(it)
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
        }
    }
}

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
                        BaseRoute.HomeRoute.route -> Icon(Icons.Filled.Home, contentDescription = item.route)
                        BaseRoute.AddRoute.route -> Icon(Icons.Filled.AddCircle, contentDescription = item.route)
                        BaseRoute.SettingsRoute.route -> Icon(Icons.Filled.Settings, contentDescription = item.route)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CognitioAstroTheme {
        MainActivityLayout()
    }
}