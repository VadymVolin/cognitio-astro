package com.cognitio.astro.presentation.screen.common.state

interface BaseScreenState<T> {
    val isLoading: Boolean
    val data: T
    val error: String
}