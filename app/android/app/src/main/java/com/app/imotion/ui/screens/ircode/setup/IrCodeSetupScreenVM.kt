package com.app.imotion.ui.screens.ircode.setup

import androidx.lifecycle.viewModelScope
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.storage.IrCodeStorageModel
import com.app.imotion.repo.ircode.IrCodesRepo
import com.app.imotion.utils.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

/**
 * Created by hani@fakhouri.eu on 2023-05-29.
 */

@HiltViewModel
class IrCodeSetupScreenVM @Inject constructor(
    private val irCodesRepo: IrCodesRepo
) : StatefulViewModel<IrCodeSetupUiState, IrCodeSetupScreenVMEvent>(IrCodeSetupUiState()) {

    private lateinit var deviceSerialNumber: DeviceSerialNumber

    fun onCreate(deviceSerialNumber: DeviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber
    }

    fun saveIrCode(name: String) {
        viewModelScope.launch {
            irCodesRepo.saveIrCode(
                IrCodeStorageModel(
                    value = Random.nextInt().toString(),
                    readableName = name,
                    associatedDeviceSerialNumber = deviceSerialNumber,
                )
            )
            emitVmEvent(IrCodeSetupScreenVMEvent.OpenDeviceOverviewPage(deviceSerialNumber))
        }
    }

    fun startSyncingIrCode() {
        updateState { copy(isSyncInProgress = true) }
        viewModelScope.launch {
            delay(2_000)
            updateState { copy(isSyncInProgress = false) }
        }
    }

    fun testIrCode() {
        updateState { copy(isTestingCode = true) }
        viewModelScope.launch {
            delay(3_000)
            updateState { copy(isTestingCode = false) }
        }
    }

}