package com.cognitio.astro.presentation.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.cognitio.astro.presentation.components.Greeting

@Composable
fun GalleryScreen(navigationController: NavHostController) {
    Greeting("GalleryScreen screen")
}