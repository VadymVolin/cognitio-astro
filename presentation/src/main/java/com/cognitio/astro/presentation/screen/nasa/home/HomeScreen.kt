package com.cognitio.astro.presentation.screen.nasa.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.cognitio.astro.presentation.screen.common.state.BaseScreenState
import com.cognitio.astro.presentation.screen.nasa.dialog.PictureOfTheDayDetailsDialog
import java.util.Objects

const val TAG = "HomeScreen"

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigationController: NavHostController
) {
    val screenState = viewModel.state
    val refreshState = rememberPullRefreshState(
        screenState.value.stateStatus == BaseScreenState.StateStatus.PAGE_REFRESH,
        viewModel::refresh
    )

    val dialogVisibilityState = remember { mutableStateOf(false) }
    val dialogDataState = remember { mutableStateOf<PictureOfTheDay?>(null) }

    if (dialogVisibilityState.value) {
        DialogScreen(
            dialogContent = { PictureOfTheDayDetailsDialog(pictureOfTheDay = dialogDataState.value) },
            dialogTitle = dialogDataState.value?.title,
            setShowDialog = {
                dialogVisibilityState.value = it
            })
    }

    Box(
        modifier = Modifier
            .pullRefresh(refreshState)
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                screenState.value.data.size,
                key = {
                    val pictureOfTheDay = screenState.value.data[it]
                    Objects.hash(
                        pictureOfTheDay.mediaType,
                        pictureOfTheDay.author,
                        pictureOfTheDay.description,
                        pictureOfTheDay.title,
                        pictureOfTheDay.date,
                        pictureOfTheDay.imageUrl,
                        pictureOfTheDay.videoUrl
                    )
                },
            ) { index ->
                PictureOfTheDayItemLayout(
                    Modifier.animateItemPlacement(),
                    pictureOfTheDay = screenState.value.data[index],
                    onItemClick = {
                        Log.d(TAG, "Select picture of the day: $it")
                        dialogDataState.value = it
                        dialogVisibilityState.value = true
                    }
                )
                if (index == screenState.value.data.size - 1 && screenState.value.stateStatus == BaseScreenState.StateStatus.IDLE) {
                    viewModel.loadNextPortion()
                }
            }
            item {
                if (screenState.value.stateStatus == BaseScreenState.StateStatus.NEXT_PAGE_LOADING) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            trackColor = MaterialTheme.colorScheme.background,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else if (screenState.value.stateStatus == BaseScreenState.StateStatus.INITIAL_LOADING) {
                    Box(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            trackColor = MaterialTheme.colorScheme.background,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
        if (screenState.value.error.isNotBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = screenState.value.error, color = MaterialTheme.colorScheme.error)
            }
        }
        PullRefreshIndicator(
            screenState.value.stateStatus == BaseScreenState.StateStatus.PAGE_REFRESH,
            refreshState,
            Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }

}
