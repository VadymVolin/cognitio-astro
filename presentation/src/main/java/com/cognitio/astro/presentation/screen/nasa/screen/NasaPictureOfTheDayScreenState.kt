package com.cognitio.astro.presentation.screen.nasa.screen

import com.cognitio.astro.domain.model.NasaPictureOfTheDay
import com.cognitio.astro.presentation.screen.common.state.BaseScreenState
import com.cognitio.astro.util.StringUtils

data class NasaPictureOfTheDayScreenState(
    override val stateStatus: BaseScreenState.StateStatus = BaseScreenState.StateStatus.IDLE,
    override val data: List<NasaPictureOfTheDay> = ArrayList(),
    override val error: String = StringUtils.EMPTY_STRING
) : BaseScreenState<List<NasaPictureOfTheDay>>