package com.app.imotion.repo.device

import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.storage.DeviceStorageModel
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by hani.fakhouri on 2023-06-08.
 */
class DevicesRepoImpl(
    private val dataSource: DevicesDataSource,
) : DevicesRepo {

    override suspend fun saveDevice(data: DeviceStorageModel): Result<Unit> =
        dataSource.saveDevice(data)

    override suspend fun deleteDevice(serialNumber: DeviceSerialNumber): Result<Unit> =
        dataSource.deleteDevice(serialNumber)

    override fun observeDevices(): StateFlow<List<DeviceStorageModel>> =
        dataSource.observeDevices()

}