package com.app.imotion.model

/**
 * Created by hani.fakhouri on 2023-06-07.
 */
data class DeviceFirmwareVersion private constructor(val value: String) {
    companion object {
        fun of(version: String) = DeviceFirmwareVersion(version)
    }
}