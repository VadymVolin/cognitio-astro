package com.cognitio.astro.presentation.screen.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cognitio.astro.domain.usecases.GetPicturesOfTheDaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPicturesOfTheDaysUseCase: GetPicturesOfTheDaysUseCase
) : ViewModel() {

    companion object {
        val TAG: String = HomeViewModel::class.java.name
    }

    private val _state = mutableStateOf(HomeScreenState(isLoading = true))
    val state: State<HomeScreenState> = _state

    init {
        getData()
    }

    private fun getData() = getPicturesOfTheDaysUseCase.invoke()
        .onEmpty {
            Log.d(TAG, "No data received")
            _state.value = HomeScreenState(error = "No data, please refresh page")
        }
        .onStart {
            Log.d(TAG, "Loading started")
            _state.value = HomeScreenState(
                isLoading = true,
                data = _state.value.data,
                error = _state.value.error
            )
        }
        .onEach {
            Log.d(TAG, "Data is loaded, items[${it.size}]")
            if (it.isEmpty()) {
                Log.d(TAG, "No data received")
                _state.value = HomeScreenState(error = "No data, please refresh page")
            } else {
                _state.value = HomeScreenState(data = it)
            }
        }.launchIn(viewModelScope)

    fun refresh() = getData()

}