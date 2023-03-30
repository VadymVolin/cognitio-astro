package com.cognitio.astro.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.cognitio.astro.ui.components.Greeting

@Composable
fun SettingsScreen(modifier: Modifier, navigationController: NavHostController) {
    Greeting("Settings screen")
}