package com.app.imotion.model.storage

import com.app.imotion.model.DeviceSerialNumber

/**
 * Created by hani.fakhouri on 2023-06-08.
 */
data class IrCodeStorageModel(
    val value: String = "-",
    val readableName: String,
    val associatedDeviceSerialNumber: DeviceSerialNumber,
)