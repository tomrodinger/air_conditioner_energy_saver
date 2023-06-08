package com.app.imotion.repo.ircode

import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.storage.IrCodeStorageModel
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by hani.fakhouri on 2023-06-08.
 */
interface IrCodesRepo {
    suspend fun addIrCode(code: IrCodeStorageModel): Result<Unit>
    suspend fun deleteIrCode(
        deviceSerialNumber: DeviceSerialNumber,
        value: String,
    ): Result<Unit>

    fun observeIrCodes(deviceSerialNumber: DeviceSerialNumber): StateFlow<List<IrCodeStorageModel>>
}