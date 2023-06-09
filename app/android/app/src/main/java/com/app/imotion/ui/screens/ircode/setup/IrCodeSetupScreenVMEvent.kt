package com.app.imotion.ui.screens.ircode.setup

import com.app.imotion.model.DeviceSerialNumber

/**
 * Created by hani.fakhouri on 2023-06-09.
 */
sealed interface IrCodeSetupScreenVMEvent {
    data class OpenDeviceOverviewPage(val deviceSerialNumber: DeviceSerialNumber) : IrCodeSetupScreenVMEvent
}