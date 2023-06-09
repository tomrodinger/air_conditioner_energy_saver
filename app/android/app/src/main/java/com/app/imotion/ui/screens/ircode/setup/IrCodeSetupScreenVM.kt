package com.app.imotion.ui.screens.ircode.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.storage.IrCodeStorageModel
import com.app.imotion.repo.ircode.IrCodesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

/**
 * Created by hani@fakhouri.eu on 2023-05-29.
 */

@HiltViewModel
class IrCodeSetupScreenVM @Inject constructor(
    private val irCodesRepo: IrCodesRepo
) : ViewModel() {

    private val _state = MutableStateFlow(IrCodeSetupState())
    val state: StateFlow<IrCodeSetupState> = _state

    private val _vmEvents = MutableSharedFlow<IrCodeSetupScreenVMEvent>()
    val vmEvents: SharedFlow<IrCodeSetupScreenVMEvent> = _vmEvents

    private lateinit var deviceSerialNumber: DeviceSerialNumber

    fun onCreate(deviceSerialNumber: DeviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber
    }

    fun onUiEvent(event: IrCodeSetupScreenUiEvent) {
        when (event) {
            is IrCodeSetupScreenUiEvent.SaveIrCode -> {
                viewModelScope.launch {
                    irCodesRepo.saveIrCode(
                        IrCodeStorageModel(
                            value = Random.nextInt().toString(),
                            readableName = event.name,
                            associatedDeviceSerialNumber = deviceSerialNumber,
                        )
                    )
                    _vmEvents.emit(
                        IrCodeSetupScreenVMEvent.OpenDeviceOverviewPage(deviceSerialNumber))
                }
            }
            IrCodeSetupScreenUiEvent.StartSyncingIrCode -> {
                _state.update { it.copy(isSyncInProgress = true) }
                viewModelScope.launch {
                    delay(2_000)
                    _state.update { it.copy(isSyncInProgress = false) }
                }
            }
            IrCodeSetupScreenUiEvent.TestIrCode -> {
                _state.update { it.copy(isTestingCode = true) }
                viewModelScope.launch {
                    delay(3_000)
                    _state.update { it.copy(isTestingCode = false) }
                }
            }
        }
    }

}