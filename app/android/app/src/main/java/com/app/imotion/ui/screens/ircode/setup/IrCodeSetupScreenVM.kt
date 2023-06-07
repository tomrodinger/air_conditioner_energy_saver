package com.app.imotion.ui.screens.ircode.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.imotion.model.MotionDeviceListEntry
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by hani@fakhouri.eu on 2023-05-29.
 */
class IrCodeSetupScreenVM : ViewModel() {

    private val _state = MutableStateFlow<IrCodeSyncState>(IrCodeSyncState.Idle)
    val state: StateFlow<IrCodeSyncState> = _state

    private val _isTesting = MutableStateFlow(false)
    val isTesting: StateFlow<Boolean> = _isTesting

    private val _events = MutableSharedFlow<IrCodeSetupScreenVMEvent>()
    val events: SharedFlow<IrCodeSetupScreenVMEvent> = _events

    private lateinit var device: MotionDeviceListEntry

    fun setDevice(device: MotionDeviceListEntry) {
        this.device = device
    }

    fun onDone(irCodeName: String) {

    }

    fun startSync() {
        _state.update { IrCodeSyncState.InProgress }
        viewModelScope.launch {
            delay(500)
            _state.update { IrCodeSyncState.Idle }
        }
    }

    fun testIrCode() {
        _isTesting.update { true }
        viewModelScope.launch {
            delay(3_000)
            _isTesting.update { false }
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