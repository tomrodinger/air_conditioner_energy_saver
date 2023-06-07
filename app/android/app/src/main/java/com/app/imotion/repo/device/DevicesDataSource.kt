package com.app.imotion.repo.device

import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.storage.DeviceStorageModel
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by hani.fakhouri on 2023-06-08.
 */
interface DevicesDataSource {
    suspend fun saveDevice(data: DeviceStorageModel): Result<Unit>
    suspend fun deleteDevice(serialNumber: DeviceSerialNumber): Result<Unit>
    fun observeDevices(): StateFlow<List<DeviceStorageModel>>
}