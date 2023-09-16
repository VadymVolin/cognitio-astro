package com.cognitio.astro.presentation.screen.nasa.home

import com.cognitio.astro.domain.model.PictureOfTheDay
import com.cognitio.astro.presentation.screen.common.state.BaseScreenState
import com.cognitio.astro.util.StringUtils

data class HomeScreenState(
    val stateStatus: StateStatus = StateStatus.IDLE,
    override val isLoading: Boolean = false,
    override val data: List<PictureOfTheDay> = emptyList(),
    override val error: String = StringUtils.EMPTY_STRING
) : BaseScreenState<List<PictureOfTheDay>> {
    enum class StateStatus {
        PAGE_REFRESH, NEXT_PAGE_LOADING, ERROR, IDLE
    }
}