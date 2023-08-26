package com.cognitio.astro.presentation.screen.nasa.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cognitio.astro.domain.model.PictureOfTheDay
import com.cognitio.astro.presentation.components.PictureOfTheDayItemLayout
import com.cognitio.astro.presentation.screen.common.DialogScreen
import com.cognitio.astro.presentation.screen.nasa.dialog.PictureOfTheDayDetailsDialog

const val TAG = "HomeScreen"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigationController: NavHostController
) {
    val screenState = viewModel.state
    val refreshState = rememberPullRefreshState(screenState.value.isLoading, viewModel::refresh)
    val dialogState = remember { mutableStateOf(false) }
    val dialogStateModel = remember { mutableStateOf<PictureOfTheDay?>(null) }

    if (dialogState.value) {
        DialogScreen(
            dialogContent = { PictureOfTheDayDetailsDialog(pictureOfTheDay = dialogStateModel.value) },
            dialogTitle = dialogStateModel.value?.title,
            setShowDialog = {
                dialogState.value = it
            })
    }

    Box(
        modifier = Modifier
            .pullRefresh(refreshState)
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(screenState.value.data) { item ->
                PictureOfTheDayItemLayout(pictureOfTheDay = item, onItemClick = {
                    Log.d(TAG, "HomeScreen: $it")
                    dialogStateModel.value = it
                    dialogState.value = true
                })
            }
        }
        if (screenState.value.error.isNotBlank()) {
            Text(text = screenState.value.error, color = MaterialTheme.colorScheme.error)
        }
        PullRefreshIndicator(
            screenState.value.isLoading,
            refreshState,
            Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }

}
