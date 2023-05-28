package com.app.imotion.ui.screens.ircode

/**
 * Created by hani.fakhouri@verisure.com on 2023-05-29.
 */
sealed interface IrCodeSyncState {
    object InProgress : IrCodeSyncState
    object Idle : IrCodeSyncState
}