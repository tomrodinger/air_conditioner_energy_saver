package com.app.imotion.ui.screens.ircode.setup

import com.app.imotion.model.MotionDeviceListEntry

/**
 * Created by hani@fakhouri.eu on 2023-05-29.
 */
sealed interface IrCodeSetupScreenVMEvent {
    data class NavigateToSetupDeviceControls(val device: MotionDeviceListEntry) :
        IrCodeSetupScreenVMEvent
}