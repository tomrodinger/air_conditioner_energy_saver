package com.app.imotion.ui.screens.ircode.overview

import androidx.lifecycle.viewModelScope
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.IrCode
import com.app.imotion.model.storage.IrCodeStorageModel
import com.app.imotion.repo.ircode.IrCodesRepo
import com.app.imotion.utils.StatefulViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by hani.fakhouri on 2023-06-09.
 */

data class IrCodesOverviewUiState(
    val loading: Boolean = false,
    val irCodes: List<IrCode> = listOf()
)

@HiltViewModel
class IrCodesOverviewVm @Inject constructor(
    private val irCodesRepo: IrCodesRepo,
) : StatefulViewModel<IrCodesOverviewUiState, Unit>(IrCodesOverviewUiState()) {

    private lateinit var deviceSerialNumber: DeviceSerialNumber

    fun onCreate(deviceSerialNumber: DeviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber
        viewModelScope.launch { collectIrCodes() }
    }

    fun deleteIrCode(value: String) {
        viewModelScope.launch {
            irCodesRepo.deleteIrCode(deviceSerialNumber, value)
        }
    }

    private suspend fun collectIrCodes() {
        updateState { copy(loading = true) }
        irCodesRepo.observeIrCodes(deviceSerialNumber)
            .map { it.map(::mapStorageModelToUiModel) }
            .collect {
                updateState {
                    copy(
                        loading = false,
                        irCodes = it
                    )
                }
            }
    }

    private fun mapStorageModelToUiModel(storageModel: IrCodeStorageModel): IrCode =
        IrCode(
            value = storageModel.value,
            readableName = storageModel.readableName,
        )

}