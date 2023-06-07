package com.app.imotion.model

import androidx.compose.ui.graphics.Color
import com.app.imotion.ui.theme.BatteryLevel

/**
 * Created by hani.fakhouri on 2023-06-07.
 */
enum class BatteryPercentageValue {
    CRITICAL,
    LOW,
    MODERATE,
    HIGH,
    ;

    companion object {

        fun of(percentage: Float): BatteryPercentageValue = when (percentage) {
            in 0.0..10.0 -> CRITICAL
            in 11.0..30.0 -> LOW
            in 31.0..70.0 -> MODERATE
            else -> HIGH
        }

    }
}

fun Float.BatteryToColor(): Color = when (BatteryPercentageValue.of(this)) {
    BatteryPercentageValue.CRITICAL -> BatteryLevel.Critical
    BatteryPercentageValue.LOW -> BatteryLevel.Low
    BatteryPercentageValue.MODERATE -> BatteryLevel.Moderate
    BatteryPercentageValue.HIGH -> BatteryLevel.High
}