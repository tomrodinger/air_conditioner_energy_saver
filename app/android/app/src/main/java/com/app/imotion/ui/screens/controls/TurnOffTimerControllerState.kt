package com.app.imotion.ui.screens.controls

import com.app.imotion.model.TimerValue

/**
 * Created by hani.fakhouri on 2023-05-31.
 */
data class TurnOffTimerControllerState(
    val isOn: Boolean = false,
    val value: TimerValue = TimerValue(minutes = 30),
)