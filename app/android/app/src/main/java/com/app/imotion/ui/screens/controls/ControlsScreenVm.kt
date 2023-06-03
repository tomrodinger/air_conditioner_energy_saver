package com.app.imotion.ui.screens.controls

import androidx.lifecycle.ViewModel
import com.app.imotion.model.TimerValue
import com.app.imotion.model.minusMinutes
import com.app.imotion.model.plusMinutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by hani.fakhouri on 2023-05-31.
 */
class ControlsScreenVm : ViewModel() {

    private val _state = MutableStateFlow(ControlsState())
    val state: StateFlow<ControlsState> = _state

    fun onTempUp() {
        val newValue = _state.value.temperatureState.value + 1
        if (newValue <= TemperatureControllerState.MAX_VALUE) {
            _state.update {
                it.copy(temperatureState = it.temperatureState.copy(value = newValue))
            }
        }
    }

    fun onTempDown() {
        val newValue = _state.value.temperatureState.value - 1
        if (newValue >= TemperatureControllerState.MIV_VALUE) {
            _state.update {
                it.copy(temperatureState = it.temperatureState.copy(value = newValue))
            }
        }
    }

    fun onTempToggleOnOff() {
        val currentValue = _state.value.temperatureState.isOn
        _state.update {
            it.copy(temperatureState = it.temperatureState.copy(isOn = !currentValue))
        }
    }

    fun onTimerUp() {
        val currentValue = _state.value.turnOffTimerState.value
        _state.update {
            it.copy(turnOffTimerState = it.turnOffTimerState.copy(value = currentValue.plusMinutes(1)))
        }
    }

    fun onTimerDown() {
        val currentValue = _state.value.turnOffTimerState.value
        _state.update {
            it.copy(
                turnOffTimerState = it.turnOffTimerState.copy(
                    value = currentValue.minusMinutes(
                        1
                    )
                )
            )
        }
    }

    fun onTimerToggleOnOff() {
        val currentValue = _state.value.turnOffTimerState.isOn
        _state.update {
            it.copy(turnOffTimerState = it.turnOffTimerState.copy(isOn = !currentValue))
        }
    }

    fun onMotionDetectionToggleOnOff() {
        val newValue = !_state.value.motionDetectionState.isOn
        if (newValue) { // On
            _state.update {
                it.copy(motionDetectionState = MotionDetectionControllerState(true))
            }
        } else {
            _state.update {
                it.copy(
                    motionDetectionState = MotionDetectionControllerState(
                        isOn = false,
                        turnOffTimerValue = TimerValue(minutes = 30)
                    )
                )
            }
        }
    }

    fun onMotionDetectionTimerUp() {
        val currentValue = _state.value.motionDetectionState.turnOffTimerValue!!
        val newState = _state.value.motionDetectionState.copy(
            turnOffTimerValue = currentValue.plusMinutes(1)
        )
        _state.update {
            it.copy(
                motionDetectionState = newState
            )
        }
    }

    fun onMotionDetectionTimerDown() {
        val currentValue = _state.value.motionDetectionState.turnOffTimerValue!!
        val newState = _state.value.motionDetectionState.copy(
            turnOffTimerValue = currentValue.minusMinutes(1)
        )
        _state.update {
            it.copy(
                motionDetectionState = newState
            )
        }
    }

    fun onMotionDetectionSelectTurnOffPerm() {
        _state.update {
            it.copy(
                motionDetectionState = MotionDetectionControllerState(
                    isOn = false,
                    turnOffPermanent = true,
                    turnOffTimerValue = null,
                )
            )
        }
    }

    fun onMotionDetectionSelectTurnOffTimer() {
        val currentState = _state.value.motionDetectionState.turnOffTimerValue
        if (currentState != null) {
            return
        }
        _state.update {
            it.copy(
                motionDetectionState = MotionDetectionControllerState(
                    isOn = false,
                    turnOffTimerValue = TimerValue(minutes = 30)
                )
            )
        }
    }

}