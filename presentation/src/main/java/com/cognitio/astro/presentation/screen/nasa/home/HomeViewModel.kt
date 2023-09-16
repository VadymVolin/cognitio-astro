package com.cognitio.astro.presentation.screen.nasa.home

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
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPicturesOfTheDaysUseCase: GetPicturesOfTheDaysUseCase
) : ViewModel() {

    companion object {
        val TAG: String = HomeViewModel::class.java.name

        private val DATE_PORTION_STEP = 10L.days.inWholeMilliseconds
    }

    private val _state = mutableStateOf(HomeScreenState(isLoading = true))
    val state: State<HomeScreenState> = _state

    private val endDateTime: AtomicLong = AtomicLong(System.currentTimeMillis() - 1L.days.inWholeMilliseconds)
    private val startDateTime: AtomicLong = AtomicLong(endDateTime.get() - DATE_PORTION_STEP)

    init {
        resetDatePortionRange()
        getData()
    }

    private fun getData() = getPicturesOfTheDaysUseCase.invoke(startDate = startDateTime.get(), endDate = endDateTime.get())
        .onEmpty {
            Log.d(TAG, "No data received")
            _state.value = _state.value.copy(error = "No data, please refresh page")
        }
        .onStart {
            Log.d(TAG, "Loading started")
            _state.value = _state.value.copy(isLoading = true)
        }
        .onEach {
            Log.d(TAG, "Data is loaded, items[${it.size}]")
            if (it.isEmpty()) {
                Log.d(TAG, "No data received")
                _state.value = HomeScreenState(error = "No data, please refresh page")
            } else {
                startDateTime.set(startDateTime.get() - DATE_PORTION_STEP)
                endDateTime.set(endDateTime.get() - DATE_PORTION_STEP)
                _state.value = HomeScreenState(data = it)
            }
        }.launchIn(viewModelScope)

    fun loadNextPortion() = getData()

    fun refresh() {
        resetDatePortionRange()
        getData()
    }

    private fun resetDatePortionRange() {
        endDateTime.set(System.currentTimeMillis() - 1L.days.inWholeMilliseconds)
        startDateTime.set(endDateTime.get() - DATE_PORTION_STEP)
    }

}