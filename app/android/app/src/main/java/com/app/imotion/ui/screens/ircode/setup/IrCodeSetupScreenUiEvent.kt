package com.app.imotion.ui.screens.ircode.setup

/**
 * Created by hani@fakhouri.eu on 2023-05-29.
 */
sealed interface IrCodeSetupScreenUiEvent {
    object StartSyncingIrCode : IrCodeSetupScreenUiEvent
    object TestIrCode : IrCodeSetupScreenUiEvent
    data class SaveIrCode(val name: String) : IrCodeSetupScreenUiEvent
}