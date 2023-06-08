package com.app.imotion.repo.ircode

import com.app.imotion.DataCoroutineScopeImpl
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.storage.IrCodeStorageModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by hani.fakhouri on 2023-06-08.
 */
class IrCodesDataSourceImpl @Inject constructor(
    private val scope: DataCoroutineScopeImpl
) : IrCodesDataSource {

    private val irCodes = MutableStateFlow<List<IrCodeStorageModel>>(listOf())

    override suspend fun addIrCode(code: IrCodeStorageModel): Result<Unit> {
        val copy = mutableListOf<IrCodeStorageModel>()
        copy.addAll(irCodes.value)
        copy.add(code)
        irCodes.update { copy }

        return Result.success(Unit)
    }

    override suspend fun deleteIrCode(
        deviceSerialNumber: DeviceSerialNumber,
        value: String,
    ): Result<Unit> {
        val copy = mutableListOf<IrCodeStorageModel>()
        copy.addAll(irCodes.value)
        copy.removeIf { it.associatedDeviceSerialNumber == deviceSerialNumber && it.value == value }
        irCodes.update { copy }

        return Result.success(Unit)
    }

    override fun observeIrCodes(
        deviceSerialNumber: DeviceSerialNumber
    ): StateFlow<List<IrCodeStorageModel>> {
        return irCodes.combine(MutableStateFlow<List<IrCodeStorageModel>>(listOf())) { list, _ ->
            list.filter { it.associatedDeviceSerialNumber == deviceSerialNumber }
        }.stateIn(
            scope,
            SharingStarted.Eagerly,
            emptyList()
        )
    }
}