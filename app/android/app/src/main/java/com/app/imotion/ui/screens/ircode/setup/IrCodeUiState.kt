package com.app.imotion.ui.screens.ircode.setup

/**
 * Created by hani@fakhouri.eu on 2023-05-29.
 */
sealed interface IrCodeSyncState {
    object InProgress : IrCodeSyncState
    object Idle : IrCodeSyncState
}