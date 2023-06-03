package com.app.imotion.model

/**
 * Created by hani.fakhouri on 2023-05-31.
 */
data class TimerValue(
    val hours: Int = 0,
    val minutes: Int = 0,
)

fun TimerValue.plusMinutes(minutes: Int) : TimerValue {
    val newMinutes = this.minutes + minutes
    if (newMinutes < 60) {
        return TimerValue(this.hours, newMinutes)
    }
    val newHours = this.hours + 1
    if (newHours <= 23) {
        return TimerValue(newHours, 0)
    }
    return TimerValue(0, 0)
}

fun TimerValue.minusMinutes(minutes: Int) : TimerValue {
    val newMinutes = this.minutes - minutes
    if (newMinutes >= 0) {
        return TimerValue(this.hours, newMinutes)
    }
    val newHours = this.hours - 1
    if (newHours >= 0) {
        return TimerValue(newHours, 0)
    }
    return TimerValue(23, 59)
}

fun Int.prefixZero() = this.toString().padStart(2, '0')