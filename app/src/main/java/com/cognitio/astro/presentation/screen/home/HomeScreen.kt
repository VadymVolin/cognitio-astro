package com.cognitio.astro.presentation.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cognitio.astro.presentation.components.PictureOfTheDayItem

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier,
    navigationController: NavHostController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val state = viewModel.state.value
            items(state) { item ->
                PictureOfTheDayItem(pictureOfTheDay = item, onItemClick = {
                    Log.d("FORTRA", "HomeScreen: ${it.title}")
                })
            }
        }
    }
}
