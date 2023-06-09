package com.app.imotion.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by hani.fakhouri on 2023-06-09.
 */

abstract class StatefulViewModel<UiState, VmEvent>(
    initUiState: UiState,
) : ViewModel() {

    private val _state = MutableStateFlow(initUiState)
    val uiState: StateFlow<UiState> = _state

    protected val state: UiState
        get() = _state.value

    private val _vmEvents = MutableSharedFlow<VmEvent>()
    val vmEvents: SharedFlow<VmEvent> = _vmEvents

    protected fun updateState(f: UiState.() -> UiState) {
        _state.update(f)
    }

    protected fun updateState(newState: UiState) {
        _state.value = newState
    }

    protected fun emitVmEvent(event: VmEvent) {
        viewModelScope.launch {
            _vmEvents.emit(event)
        }
    }
}