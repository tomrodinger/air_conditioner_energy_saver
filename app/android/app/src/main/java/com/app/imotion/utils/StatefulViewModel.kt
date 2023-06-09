package com.app.imotion.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by hani.fakhouri on 2023-06-09.
 */

abstract class StatefulViewModel<UiState, UiEvent, VmEvent>(
    initUiState: UiState,
) : ViewModel() {

    protected val _state = MutableStateFlow(initUiState)
    val state: StateFlow<UiState> = _state

    private val _uiEvents = MutableSharedFlow<UiEvent>()
    val uiEvents: SharedFlow<UiEvent> = _uiEvents

    private val _vmEvents = MutableSharedFlow<VmEvent>()
    val vmEvents: SharedFlow<VmEvent> = _vmEvents

    abstract fun onUiEvent(event: UiEvent)

    protected fun emitUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvents.emit(event)
        }
    }

    protected fun emitVmEvent(event: VmEvent) {
        viewModelScope.launch {
            _vmEvents.emit(event)
        }
    }
}