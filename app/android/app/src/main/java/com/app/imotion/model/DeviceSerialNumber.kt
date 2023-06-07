package com.app.imotion.model

/**
 * Created by hani@fakhouri.eu on 2023-05-23.
 */
data class DeviceSerialNumber private constructor(val value: String) {
    companion object {
        fun of(sn: String) = DeviceSerialNumber(sn)
    }
}