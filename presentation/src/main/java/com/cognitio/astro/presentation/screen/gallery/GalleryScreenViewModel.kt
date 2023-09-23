package com.cognitio.astro.presentation.screen.gallery

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cognitio.astro.domain.usecases.GetAllAstroPhotosUseCase
import com.cognitio.astro.presentation.screen.common.state.BaseScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class GalleryScreenViewModel @Inject constructor(
    private val getAllAstroPhotosUseCase: GetAllAstroPhotosUseCase
) : ViewModel() {

    companion object {
        val TAG: String = GalleryScreenViewModel::class.java.name
        val MIN_REFRESH_DELAY = 5L.minutes.inWholeMilliseconds
    }

    private val _lastRefreshTime = AtomicLong(0)

    private val _state =
        mutableStateOf(GalleryScreenState(stateStatus = BaseScreenState.StateStatus.IDLE))
    val state: State<GalleryScreenState>
        get() = _state

    init {
        resetState()
        getGalleryData()
    }

    private fun getGalleryData(stateStatus: BaseScreenState.StateStatus = BaseScreenState.StateStatus.INITIAL_LOADING) {
        _lastRefreshTime.set(System.currentTimeMillis())
        getAllAstroPhotosUseCase
            .invoke()
            .onStart {
                Log.d(TAG, "Loading started")
                _state.value = _state.value.copy(
                    stateStatus = stateStatus
                )
            }
            .onEach {
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
            }
            .onEmpty {
                Log.d(TAG, "No data received")
                _state.value = _state.value.copy(
                    stateStatus = BaseScreenState.StateStatus.IDLE,
                    error = "No data, please refresh page"
                )
            }.launchIn(viewModelScope)
    }

    private fun isRefreshAllowed() = System.currentTimeMillis() - _lastRefreshTime.get() > MIN_REFRESH_DELAY

    fun refresh() {
        if (isRefreshAllowed()) {
            getGalleryData(BaseScreenState.StateStatus.PAGE_REFRESH)
        } else {
            Log.d(TAG, "Skip refresh")
        }
    }

    private fun resetState() {
        _state.value = GalleryScreenState(stateStatus = BaseScreenState.StateStatus.IDLE)
    }
}