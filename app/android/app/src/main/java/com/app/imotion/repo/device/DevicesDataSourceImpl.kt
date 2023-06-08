package com.app.imotion.repo.device

import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.storage.DeviceStorageModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by hani.fakhouri on 2023-06-08.
 */
class DevicesDataSourceImpl @Inject constructor() : DevicesDataSource {

    private val devices = MutableStateFlow<List<DeviceStorageModel>>(listOf())

    override suspend fun addDevice(data: DeviceStorageModel): Result<Unit> {
        val copy = mutableListOf<DeviceStorageModel>()
        copy.addAll(devices.value)
        copy.add(data)
        devices.update { copy }

        return Result.success(Unit)
    }

    override suspend fun deleteDevice(serialNumber: DeviceSerialNumber): Result<Unit> {
        val copy = mutableListOf<DeviceStorageModel>()
        copy.addAll(devices.value)
        copy.removeIf { it.serialNumber == serialNumber }
        devices.update { copy }

        return Result.success(Unit)
    }

    override fun observeDevices(): StateFlow<List<DeviceStorageModel>> {
        return devices.asStateFlow()
    }

}