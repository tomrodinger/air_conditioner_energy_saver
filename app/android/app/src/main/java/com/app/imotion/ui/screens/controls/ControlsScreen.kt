package com.app.imotion.ui.screens.controls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.imotion.R
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.MotionDeviceListEntry
import com.app.imotion.model.TimerValue
import com.app.imotion.ui.components.*
import com.app.imotion.ui.theme.PreviewTheme

/**
 * Created by hani.fakhouri on 2023-05-31.
 */

@Composable
fun ControlsScreen(
    vm: ControlsScreenVm = viewModel(),
    device: MotionDeviceListEntry,
    onBack: () -> Unit,
) {
    SimpleScreenTemplate(
        title = device.name,
        onBack = onBack,
        content = {
            ControlsUi(vm)
        }
    )
}

@Composable
private fun ControlsUi(
    vm: ControlsScreenVm
) {
    val state by vm.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        val temperature = state.temperatureState
        DeviceTemperatureController(
            isOn = temperature.isOn,
            currentValue = temperature.value,
            onTurnOnOff = vm::onTempToggleOnOff,
            onUp = vm::onTempUp,
            onDown = vm::onTempDown,
        )
        VerticalSpacer(space = 24.dp)

        val turnOffTimer = state.turnOffTimerState
        DeviceTurnOffTimerController(
            isOn = turnOffTimer.isOn,
            currentValue = turnOffTimer.value,
            onTurnOnOff = vm::onTimerToggleOnOff,
            onAddOneMinute = vm::onTimerUp,
            onRemoveOneMinute = vm::onTimerDown,
        )
        VerticalSpacer(space = 24.dp)

        val motionDetection = state.motionDetectionState

        MotionDetectionController(
            isOn = motionDetection.isOn,
            valueTurnOffPerm = motionDetection.turnOffPermanent,
            valueTurnOffTimer = motionDetection.turnOffTimerValue,
            onTurnOnOff = vm::onMotionDetectionToggleOnOff,
            onTurnOffPermSelected = vm::onMotionDetectionSelectTurnOffPerm,
            onTurnOffTimerSelected = vm::onMotionDetectionSelectTurnOffTimer,
            onAddOneMinute = vm::onMotionDetectionTimerUp,
            onRemoveOneMinute = vm::onMotionDetectionTimerDown,
        )
    }
}

@Composable
private fun MotionDetectionController(
    isOn: Boolean,
    valueTurnOffPerm: Boolean? = null,
    valueTurnOffTimer: TimerValue? = null,
    onTurnOnOff: onSelected,
    onTurnOffPermSelected: onSelected,
    onTurnOffTimerSelected: onSelected,
    onAddOneMinute: onSelected,
    onRemoveOneMinute: onSelected,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        SwitchWithText(
            text = "Set Motion Detection",
            checked = isOn,
            onCheckChange = { onTurnOnOff() }
        )
        AnimatedVisibility(
            visible = !isOn,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        modifier = Modifier.clickable {
                            onTurnOffTimerSelected()
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = valueTurnOffTimer != null,
                            onClick = onTurnOffTimerSelected,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colors.primary,
                            )
                        )
                        Text(
                            text = "Turn Off for a while",
                            color = MaterialTheme.colors.onBackground,
                            style = MaterialTheme.typography.caption,
                        )
                    }

                    Row(
                        modifier = Modifier.clickable {
                            onTurnOffPermSelected()
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = valueTurnOffPerm == true,
                            onClick = onTurnOffPermSelected,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colors.primary,
                            )
                        )
                        Text(
                            text = "Turn Off permanently",
                            color = MaterialTheme.colors.onBackground,
                            style = MaterialTheme.typography.caption,
                        )
                    }
                }
                AnimatedVisibility(
                    visible = valueTurnOffTimer != null,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    valueTurnOffTimer?.let {
                        TimerUi(
                            value = it,
                            onAddOneMinute = onAddOneMinute,
                            onRemoveOneMinute = onRemoveOneMinute,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DeviceTurnOffTimerController(
    isOn: Boolean,
    currentValue: TimerValue,
    onTurnOnOff: onSelected,
    onAddOneMinute: onSelected,
    onRemoveOneMinute: onSelected,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        SwitchWithText(
            text = "Set Turn Off Time",
            checked = isOn,
            onCheckChange = { onTurnOnOff() }
        )
        AnimatedVisibility(
            visible = isOn,
            modifier = Modifier.fillMaxWidth()
        ) {
            TimerUi(
                value = currentValue,
                onAddOneMinute = onAddOneMinute,
                onRemoveOneMinute = onRemoveOneMinute,
            )
        }
        VerticalSpacer(space = 16.dp)
        Divider()
    }
}

@Composable
private fun DeviceTemperatureController(
    isOn: Boolean,
    currentValue: Int,
    onTurnOnOff: onSelected,
    onUp: onSelected,
    onDown: onSelected,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        SwitchWithText(
            text = "Set Turn Off Temperature",
            checked = isOn,
            onCheckChange = { onTurnOnOff() }
        )
        AnimatedVisibility(
            visible = isOn,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ValueController(
                    icon = R.drawable.arrow_up,
                    iconSize = 24.dp,
                    onClick = onUp
                )
                HorizontalSpacer(space = 28.dp)
                Row {
                    Text(
                        text = currentValue.toString(),
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.W500,
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = "°",
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.W500,
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center,
                    )
                }
                HorizontalSpacer(space = 28.dp)
                ValueController(
                    icon = R.drawable.arrow_down,
                    iconSize = 24.dp,
                    onClick = onDown
                )
            }
        }
        VerticalSpacer(space = 16.dp)
        Divider()
    }
}

@Composable
private fun TimerUi(
    value: TimerValue,
    onAddOneMinute: onSelected,
    onRemoveOneMinute: onSelected,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ValueController(
            icon = R.drawable.timer_minus,
            onClick = onRemoveOneMinute
        )
        HorizontalSpacer(space = 8.dp)

        Row {
            TimeComponent(value.hours, "hr")
            Colon()
            TimeComponent(value.minutes, "min")
        }

        HorizontalSpacer(space = 8.dp)
        ValueController(
            icon = R.drawable.timer_plus,
            onClick = onAddOneMinute
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ControlsScreenPreview() {
    PreviewTheme {
        ControlsScreen(
            device = MotionDeviceListEntry(
                name = "Device name",
                area = "Kitchen",
                isActive = true,
                serialNumber = DeviceSerialNumber("ABCD")
            ),
            onBack = {},
        )
    }
}