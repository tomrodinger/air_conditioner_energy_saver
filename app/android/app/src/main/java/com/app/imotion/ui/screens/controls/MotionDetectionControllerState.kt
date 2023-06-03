package com.app.imotion.ui.screens.controls

import com.app.imotion.model.TimerValue

/**
 * Created by hani.fakhouri on 2023-06-03.
 */
data class MotionDetectionControllerState(
    val isOn: Boolean = false,
    val turnOffPermanent: Boolean? = null,
    val turnOffTimerValue: TimerValue? = TimerValue(minutes = 30),
)