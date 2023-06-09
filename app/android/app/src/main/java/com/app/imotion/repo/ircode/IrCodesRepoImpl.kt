package com.app.imotion.repo.ircode

import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.storage.IrCodeStorageModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Created by hani.fakhouri on 2023-06-08.
 */
class IrCodesRepoImpl @Inject constructor(
    private val dataSource: IrCodesDataSource,
) : IrCodesRepo {
    override suspend fun saveIrCode(code: IrCodeStorageModel): Result<Unit> =
        dataSource.saveIrCode(code)

    override suspend fun deleteIrCode(
        deviceSerialNumber: DeviceSerialNumber,
        value: String
    ): Result<Unit>  = dataSource.deleteIrCode(deviceSerialNumber, value)

    override fun observeIrCodes(
        deviceSerialNumber: DeviceSerialNumber
    ): StateFlow<List<IrCodeStorageModel>> = dataSource.observeIrCodes(deviceSerialNumber)
}