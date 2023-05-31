package com.app.imotion.permission

/**
 * Data class that represents BLE permission state
 *
 * Created by hani.fakhouri on 2023-05-29.
 */
data class PermissionState(
    val hasPermission: Boolean = false,
    val isEnabled: Boolean = false,
    val rationaleMessage: String? = null,
    val showPermissionRationalScreen: Boolean = false,
    var performCheckOnResume: Boolean = false,
)