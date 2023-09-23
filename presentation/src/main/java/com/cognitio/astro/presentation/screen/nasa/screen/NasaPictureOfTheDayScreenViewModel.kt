package com.cognitio.astro.presentation.screen.nasa.screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cognitio.astro.domain.usecases.GetAllAstroPhotosUseCase
import com.cognitio.astro.domain.usecases.GetNasaPicturesOfTheDaysUseCase
import com.cognitio.astro.presentation.screen.common.state.BaseScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

@HiltViewModel
class NasaPictureOfTheDayScreenViewModel @Inject constructor(
    private val getNasaPicturesOfTheDaysUseCase: GetNasaPicturesOfTheDaysUseCase,
    private val getAllAstroPhotosUseCase: GetAllAstroPhotosUseCase
) : ViewModel() {

    companion object {
        val TAG: String = NasaPictureOfTheDayScreenViewModel::class.java.name

        private val DATE_PORTION_STEP = 20L.days.inWholeMilliseconds
    }

    private val _state =
        mutableStateOf(NasaPictureOfTheDayScreenState(stateStatus = BaseScreenState.StateStatus.INITIAL_LOADING))
    val state: State<NasaPictureOfTheDayScreenState>
        get() = _state

    private val endDateTime: AtomicLong =
        AtomicLong(System.currentTimeMillis() - 1L.days.inWholeMilliseconds)
    private val startDateTime: AtomicLong = AtomicLong(endDateTime.get() - DATE_PORTION_STEP)

    init {
        resetState()
        getData(BaseScreenState.StateStatus.INITIAL_LOADING)
    }

    private fun getData(stateStatus: BaseScreenState.StateStatus) {
        getNasaPicturesOfTheDaysUseCase.invoke(
            startDate = startDateTime.get(),
            endDate = endDateTime.get()
        )
            .onEmpty {
                Log.d(TAG, "No data received")
                _state.value = _state.value.copy(
                    stateStatus = BaseScreenState.StateStatus.IDLE,
                    error = "No data, please refresh page"
                )
            }
            .onStart {
                Log.d(TAG, "Loading started")
                _state.value = _state.value.copy(
                    stateStatus = stateStatus
                )
            }
            .onEach {
                Log.d(TAG, "Data is loaded, items[${it.size}]")
                if (it.isEmpty()) {
                    when (stateStatus) {
                        BaseScreenState.StateStatus.INITIAL_LOADING -> {
                            Log.d(TAG, "No data received")
                            _state.value = _state.value.copy(
                                stateStatus = BaseScreenState.StateStatus.ERROR,
                                error = "No data, please refresh page",
                                data = ArrayList()
                            )
                        }

                        BaseScreenState.StateStatus.NEXT_PAGE_LOADING,
                        BaseScreenState.StateStatus.PAGE_REFRESH -> {
                            Log.d(TAG, "No data found, skip")
                        }

                        else -> {
                            Log.d(TAG, "No data received")
                            _state.value = _state.value.copy(
                                stateStatus = BaseScreenState.StateStatus.ERROR,
                                error = "No data, please refresh page",
                                data = ArrayList()
                            )
                        }
                    }

                } else {
                    _state.value = _state.value.copy(
                        stateStatus = BaseScreenState.StateStatus.IDLE,
                        data = _state.value.data + it
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun loadNextPortion() {
        startDateTime.set(startDateTime.get() - DATE_PORTION_STEP)
        endDateTime.set(endDateTime.get() - DATE_PORTION_STEP)
        getData(BaseScreenState.StateStatus.NEXT_PAGE_LOADING)
    }

    fun refresh() {
        getData(BaseScreenState.StateStatus.PAGE_REFRESH)
    }

    private fun resetState() {
        _state.value =
            NasaPictureOfTheDayScreenState(stateStatus = BaseScreenState.StateStatus.IDLE)
        endDateTime.set(System.currentTimeMillis() - 1L.days.inWholeMilliseconds)
        startDateTime.set(endDateTime.get() - DATE_PORTION_STEP)
    }

}