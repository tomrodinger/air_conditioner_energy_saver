package com.app.imotion.ui.screens.ircode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by hani.fakhouri@verisure.com on 2023-05-29.
 */
class IrCodeSetupScreenVM : ViewModel() {

    private val _state = MutableStateFlow<IrCodeSyncState>(IrCodeSyncState.Idle)
    val state: StateFlow<IrCodeSyncState> = _state

    fun startSync() {
        _state.update { IrCodeSyncState.InProgress }
        viewModelScope.launch {
            delay(5_000)
            _state.update { IrCodeSyncState.Idle }
        }
    }

}