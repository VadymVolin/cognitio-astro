package com.cognitio.astro.presentation.screen.nasa.home

import com.cognitio.astro.domain.model.PictureOfTheDay
import com.cognitio.astro.presentation.screen.common.state.BaseScreenState
import com.cognitio.astro.util.StringUtils

data class HomeScreenState(
    override val stateStatus: BaseScreenState.StateStatus = BaseScreenState.StateStatus.IDLE,
    override val data: List<PictureOfTheDay> = emptyList(),
    override val error: String = StringUtils.EMPTY_STRING
) : BaseScreenState<List<PictureOfTheDay>>