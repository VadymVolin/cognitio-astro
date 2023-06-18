package com.cognitio.astro.presentation.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cognitio.astro.presentation.components.PictureOfTheDayItem

const val TAG = "HomeScreen"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigationController: NavHostController
) {
    val screenState = viewModel.state
    val refreshState = rememberPullRefreshState(screenState.value.isLoading, viewModel::refresh)
    Box(modifier = Modifier
        .pullRefresh(refreshState)
        .fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(screenState.value.data) { item ->
                PictureOfTheDayItem(pictureOfTheDay = item, onItemClick = {
                    Log.d(TAG, "HomeScreen: $it")
                })
            }
        }
        if (screenState.value.error.isNotBlank()) {
            Text(text = screenState.value.error, color = MaterialTheme.colors.error)
        }
        PullRefreshIndicator(screenState.value.isLoading, refreshState, Modifier.align(Alignment.TopCenter), backgroundColor = MaterialTheme.colors.background, contentColor = MaterialTheme.colors.primary)
    }

}
