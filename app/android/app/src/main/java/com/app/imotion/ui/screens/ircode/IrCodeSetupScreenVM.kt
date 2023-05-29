package com.app.imotion.ui.screens.ircode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.imotion.model.IMotionDevice
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by hani.fakhouri@verisure.com on 2023-05-29.
 */
class IrCodeSetupScreenVM : ViewModel() {

    private val _state = MutableStateFlow<IrCodeSyncState>(IrCodeSyncState.Idle)
    val state: StateFlow<IrCodeSyncState> = _state

    private val _events = MutableSharedFlow<IrCodeSetupScreenVMEvent>()
    val events: SharedFlow<IrCodeSetupScreenVMEvent> = _events

    private lateinit var device: IMotionDevice

    fun setDevice(device: IMotionDevice) {
        this.device = device
    }

    fun startSync() {
        _state.update { IrCodeSyncState.InProgress }
        viewModelScope.launch {
            delay(5_000)
            _state.update { IrCodeSyncState.Idle }
        }
    }

    fun navigateToSetupDeviceControls() {
        onVmEvent(IrCodeSetupScreenVMEvent.NavigateToSetupDeviceControls(device))
    }

    private fun onVmEvent(event: IrCodeSetupScreenVMEvent) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

}