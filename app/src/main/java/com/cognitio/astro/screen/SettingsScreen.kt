package com.cognitio.astro.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.cognitio.astro.components.Greeting

@Composable
fun SettingsScreen(modifier: Modifier, navigationController: NavHostController) {
    Greeting("Settings screen")
}