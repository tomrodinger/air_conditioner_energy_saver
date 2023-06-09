package com.app.imotion.ui.screens.add

import com.app.imotion.model.DeviceSerialNumber

/**
 * Created by hani.fakhouri on 2023-06-09.
 */

sealed interface AddDeviceManuallyUiEvent {
    object GoBack : AddDeviceManuallyUiEvent
    object OpenAddAnotherDevice : AddDeviceManuallyUiEvent
    object GoToDashboard : AddDeviceManuallyUiEvent
    data class OpenLearnIrCode(val serialNumber: DeviceSerialNumber) : AddDeviceManuallyUiEvent
}