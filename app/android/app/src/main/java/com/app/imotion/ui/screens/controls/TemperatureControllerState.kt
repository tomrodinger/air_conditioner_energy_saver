package com.app.imotion.ui.screens.controls

/**
 * Created by hani.fakhouri on 2023-05-31.
 */
data class TemperatureControllerState(
    val isOn: Boolean = false,
    val value: Int = 28,
) {
    companion object {
        const val MAX_VALUE = 35
        const val MIV_VALUE = -5
    }
}
