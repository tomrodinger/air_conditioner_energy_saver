package com.app.imotion.model

/**
 * Created by hani@fakhouri.eu on 2023-05-23.
 */
data class Device(
    val name: String,
    val area: String,
    val isActive: Boolean,
    val serialNumber: DeviceSerialNumber,
)
