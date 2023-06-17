package com.cognitio.astro.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.cognitio.astro.presentation.components.Greeting

@Composable
fun SettingsScreen(navigationController: NavHostController) {
    Greeting("Settings screen")
}