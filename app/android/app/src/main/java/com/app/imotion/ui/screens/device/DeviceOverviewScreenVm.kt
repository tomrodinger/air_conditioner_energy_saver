package com.app.imotion.ui.screens.device

import androidx.lifecycle.viewModelScope
import com.app.imotion.model.DeviceData
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.IrCode
import com.app.imotion.repo.device.DevicesRepo
import com.app.imotion.repo.ircode.IrCodesRepo
import com.app.imotion.utils.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by hani.fakhouri on 2023-06-09.
 */

data class DeviceOverviewState(
    val loading: Boolean = false,
    val data: DeviceData? = null,
)

@HiltViewModel
class DeviceOverviewScreenVm @Inject constructor(
    private val devicesRepo: DevicesRepo,
    private val irCodesRepo: IrCodesRepo,
) : StatefulViewModel<DeviceOverviewState, Unit>(DeviceOverviewState()) {

    private lateinit var deviceSerialNumber: DeviceSerialNumber

    fun onCreate(deviceSerialNumber: DeviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber
        viewModelScope.launch { load() }
    }

    private suspend fun load() {
        updateState { copy(loading = true) }
        combine(
            devicesRepo.observeDevices(),
            irCodesRepo.observeIrCodes(deviceSerialNumber)
        ) { devices, irCodes ->
            val device = devices.first { it.serialNumber == deviceSerialNumber }
            DeviceData(
                name = device.name,
                serialNumber = deviceSerialNumber,
                batterPercentage = device.batteryPercentage.toFloat(),
                firmwareVersion = device.firmwareVersion,
                irCodes = irCodes.map { IrCode(value = it.value, readableName = it.readableName) },
            )
        }.collect { deviceData ->
            updateState {
                copy(
                    loading = false,
                    data = deviceData,
                )
            }
        }
    }

}