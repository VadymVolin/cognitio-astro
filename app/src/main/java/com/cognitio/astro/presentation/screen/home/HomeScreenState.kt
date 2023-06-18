package com.cognitio.astro.presentation.screen.home

import com.cognitio.astro.domain.model.PictureOfTheDay
import com.cognitio.astro.presentation.screen.common.state.BaseScreenState
import com.cognitio.astro.util.StringUtils

class HomeScreenState(
    override val isLoading: Boolean = false,
    override val data: List<PictureOfTheDay> = emptyList(),
    override val error: String = StringUtils.EMPTY_STRING
) : BaseScreenState<List<PictureOfTheDay>>