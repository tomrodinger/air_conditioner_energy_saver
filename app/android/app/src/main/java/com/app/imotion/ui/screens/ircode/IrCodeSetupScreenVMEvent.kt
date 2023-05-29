package com.app.imotion.ui.screens.ircode

import com.app.imotion.model.IMotionDevice

/**
 * Created by hani@fakhouri.eu on 2023-05-29.
 */
sealed interface IrCodeSetupScreenVMEvent {
    data class NavigateToSetupDeviceControls(val device: IMotionDevice) : IrCodeSetupScreenVMEvent
}