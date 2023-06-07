package com.app.imotion.model

/**
 * Created by hani.fakhouri on 2023-06-07.
 */

data class DeviceData(
    val name: String,
    val serialNumber: DeviceSerialNumber,
    @androidx.annotation.FloatRange(from = 0.0, to = 100.0) val batterPercentage: Float,
    val firmwareVersion: DeviceFirmwareVersion,
    val irCodes: List<IrCode>,
)