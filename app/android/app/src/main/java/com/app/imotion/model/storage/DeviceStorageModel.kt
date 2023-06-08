package com.app.imotion.model.storage

import com.app.imotion.model.DeviceFirmwareVersion
import com.app.imotion.model.DeviceSerialNumber

/**
 * Created by hani.fakhouri on 2023-06-08.
 */
data class DeviceStorageModel(
    val name: String,
    val areaName: String,
    val serialNumber: DeviceSerialNumber,
    val isActive: Boolean,
    val firmwareVersion: DeviceFirmwareVersion,
    val batteryPercentage: Int,
)