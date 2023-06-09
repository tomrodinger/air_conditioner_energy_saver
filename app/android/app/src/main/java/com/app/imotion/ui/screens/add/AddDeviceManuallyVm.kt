package com.app.imotion.ui.screens.add

import androidx.lifecycle.viewModelScope
import com.app.imotion.model.DeviceFirmwareVersion
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.storage.DeviceStorageModel
import com.app.imotion.repo.device.DevicesRepo
import com.app.imotion.utils.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by hani.fakhouri on 2023-06-08.
 */

data class AddDeviceManuallyState(
    val deviceAdded: Boolean = false,
)

data class AddNewDeviceArgs(
    val name: String,
    val areaName: String,
    val serialNumber: DeviceSerialNumber
)

@HiltViewModel
class AddDeviceManuallyVm @Inject constructor(
    private val devicesRepo: DevicesRepo,
) : StatefulViewModel<AddDeviceManuallyState, Unit>(AddDeviceManuallyState()) {

    fun addNewDevice(args: AddNewDeviceArgs) {
        updateState { copy(deviceAdded = false) }
        viewModelScope.launch {
            devicesRepo.addDevice(
                DeviceStorageModel(
                    name = args.name,
                    areaName = args.areaName,
                    serialNumber = args.serialNumber,
                    isActive = true,
                    firmwareVersion = DeviceFirmwareVersion.of("1.0.0"),
                    batteryPercentage = 100,
                )
            )
            updateState { copy(deviceAdded = true) }
        }
    }

}