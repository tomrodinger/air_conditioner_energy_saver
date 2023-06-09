package com.app.imotion.ui.screens.add

import androidx.lifecycle.viewModelScope
import com.app.imotion.model.DeviceFirmwareVersion
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.storage.DeviceStorageModel
import com.app.imotion.repo.device.DevicesRepo
import com.app.imotion.utils.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by hani.fakhouri on 2023-06-08.
 */

sealed interface AddDeviceManuallyUiEvent {
    object GoBack : AddDeviceManuallyUiEvent
    object OpenAddAnotherDevice : AddDeviceManuallyUiEvent
    object GoToDashboard : AddDeviceManuallyUiEvent

    data class AddDevice(
        val name: String,
        val areaName: String,
        val serialNumber: DeviceSerialNumber,
    ) : AddDeviceManuallyUiEvent

    data class OpenLearnIrCode(val serialNumber: DeviceSerialNumber) : AddDeviceManuallyUiEvent
}

data class AddDeviceManuallyState(
    val deviceAdded: Boolean = false,
)

@HiltViewModel
class AddDeviceManuallyVm @Inject constructor(
    private val devicesRepo: DevicesRepo,
) : StatefulViewModel<AddDeviceManuallyState, AddDeviceManuallyUiEvent, Unit>(AddDeviceManuallyState()) {

    override fun onUiEvent(event: AddDeviceManuallyUiEvent) {
        when (event) {
            is AddDeviceManuallyUiEvent.AddDevice -> {
                _state.update { it.copy(deviceAdded = false) }
                viewModelScope.launch {
                    devicesRepo.addDevice(
                        DeviceStorageModel(
                            name = event.name,
                            areaName = event.areaName,
                            serialNumber = event.serialNumber,
                            isActive = true,
                            firmwareVersion = DeviceFirmwareVersion.of("1.0.0"),
                            batteryPercentage = 100,
                        )
                    )
                    _state.update { it.copy(deviceAdded = true) }
                }
            }
            else -> emitUiEvent(event)
        }
    }

}