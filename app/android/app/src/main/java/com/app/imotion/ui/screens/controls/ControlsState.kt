package com.app.imotion.ui.screens.controls

/**
 * Created by hani.fakhouri on 2023-05-31.
 */
data class ControlsState(
    val temperatureState: TemperatureControllerState = TemperatureControllerState(),
    val turnOffTimerState: TurnOffTimerControllerState = TurnOffTimerControllerState(),
    val motionDetectionState: MotionDetectionControllerState = MotionDetectionControllerState(),
)
