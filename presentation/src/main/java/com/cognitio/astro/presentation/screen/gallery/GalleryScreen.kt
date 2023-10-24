package com.cognitio.astro.presentation.screen.gallery

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cognitio.astro.presentation.common.DrawableUtils
import com.cognitio.astro.presentation.screen.common.DialogScreen
import com.cognitio.astro.presentation.screen.common.state.BaseScreenState
import com.cognitio.astro.presentation.screen.nasa.screen.TAG
import java.util.Objects

const val TAG = "GalleryScreen"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GalleryScreen(
    viewModel: GalleryScreenViewModel = hiltViewModel(),
    navigationController: NavHostController
) {
    val screenState = viewModel.state
    val refreshState = rememberPullRefreshState(
        screenState.value.stateStatus == BaseScreenState.StateStatus.PAGE_REFRESH,
        viewModel::refresh
    )

    val dialogVisibilityState = remember { mutableStateOf(false) }
    val dialogDataState = remember { mutableStateOf<String?>(null) }

    if (dialogVisibilityState.value) {
        DialogScreen(
            dialogContent = {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(dialogDataState.value)
                            .fallback(DrawableUtils.IconResource.PlanetPlaceholder.resourceId)
                            .placeholder(DrawableUtils.IconResource.PlanetPlaceholder.resourceId)
                            .error(DrawableUtils.IconResource.PlanetPlaceholder.resourceId)
                            .crossfade(true)
                            .build(),
                        placeholder = DrawableUtils.getPainterIcon(DrawableUtils.IconResource.PlanetPlaceholder),
                        fallback = DrawableUtils.getPainterIcon(DrawableUtils.IconResource.PlanetPlaceholder),
                        error = DrawableUtils.getPainterIcon(DrawableUtils.IconResource.PlanetPlaceholder),
                        contentDescription = dialogDataState.value,
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface)
                    )
                }
            },
            setShowDialog = {
                dialogVisibilityState.value = it
            })
    }

    Box(
        modifier = Modifier
            .pullRefresh(refreshState)
            .fillMaxSize()
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(3),
            verticalItemSpacing = 1.dp,
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            content = {
                items(
                    count = screenState.value.data.size,
                    key = { Objects.hash(screenState.value.data[it]) }
                ) { index ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(screenState.value.data[index])
                            .fallback(DrawableUtils.IconResource.PlanetPlaceholder.resourceId)
                            .placeholder(DrawableUtils.IconResource.PlanetPlaceholder.resourceId)
                            .error(DrawableUtils.IconResource.PlanetPlaceholder.resourceId)
                            .crossfade(true)
                            .build(),
                        placeholder = DrawableUtils.getPainterIcon(DrawableUtils.IconResource.PlanetPlaceholder),
                        fallback = DrawableUtils.getPainterIcon(DrawableUtils.IconResource.PlanetPlaceholder),
                        error = DrawableUtils.getPainterIcon(DrawableUtils.IconResource.PlanetPlaceholder),
                        contentDescription = screenState.value.data[index],
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth().height(145.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .clickable {
                                Log.d(TAG, "on item click: ${screenState.value.data[index]}")
                                dialogDataState.value = screenState.value.data[index]
                                dialogVisibilityState.value = true
                            }
                    )
                }
                item {
                    if (screenState.value.stateStatus == BaseScreenState.StateStatus.NEXT_PAGE_LOADING) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                trackColor = MaterialTheme.colorScheme.background,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        if (screenState.value.stateStatus == BaseScreenState.StateStatus.INITIAL_LOADING) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    trackColor = MaterialTheme.colorScheme.background,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        if (screenState.value.error.isNotBlank()) {
            Box(
                modifier = Modifier.fillMaxSize(),
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