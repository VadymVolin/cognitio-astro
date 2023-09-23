package com.cognitio.astro.presentation.screen.gallery

import com.cognitio.astro.presentation.screen.common.state.BaseScreenState
import com.cognitio.astro.util.StringUtils

data class GalleryScreenState(
    override val stateStatus: BaseScreenState.StateStatus = BaseScreenState.StateStatus.IDLE,
    override val data: List<String> = ArrayList(),
    override val error: String = StringUtils.EMPTY_STRING
) : BaseScreenState<List<String>>