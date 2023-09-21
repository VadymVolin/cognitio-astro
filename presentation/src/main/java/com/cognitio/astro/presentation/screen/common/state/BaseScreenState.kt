package com.cognitio.astro.presentation.screen.common.state

interface BaseScreenState<T> {
    val stateStatus: StateStatus
    val data: T
    val error: String

    enum class StateStatus {
        PAGE_REFRESH, INITIAL_LOADING, NEXT_PAGE_LOADING, ERROR, IDLE
    }
}