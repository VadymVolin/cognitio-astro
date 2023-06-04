package com.cognitio.astro.presentation.screen.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cognitio.astro.domain.model.PictureOfTheDay
import com.cognitio.astro.domain.usecases.GetPicturesOfTheDaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPicturesOfTheDaysUseCase: GetPicturesOfTheDaysUseCase
) : ViewModel() {
    private val _state = mutableStateOf(emptyList<PictureOfTheDay>())
    val state: State<List<PictureOfTheDay>> = _state

    init {
        getData()
    }

    private fun getData() = getPicturesOfTheDaysUseCase.invoke().onEach {
        Log.d("FORTRA", "getData: ${it.size}")
        _state.value = it
    }.launchIn(viewModelScope)

}