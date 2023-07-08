package com.app.imotion.ui.screens.add

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.navigation.NavRoute
import com.app.imotion.ui.components.MotionButton
import com.app.imotion.ui.components.SimpleScreenTemplate
import com.app.imotion.ui.components.TextInputUi
import com.app.imotion.ui.components.VerticalSpacer
import com.app.imotion.ui.theme.MotionBlack
import com.app.imotion.ui.theme.MotionBlue
import com.app.imotion.ui.theme.PreviewTheme

/**
 * Created by hani@fakhouri.eu on 2023-05-27.
 */

@Composable
fun AddDeviceManuallyRoute(
    eventSink: (AddDeviceManuallyUiEvent) -> Unit,
    vm: AddDeviceManuallyVm = hiltViewModel(),
) {
    val state by vm.uiState.collectAsStateWithLifecycle()
    SimpleScreenTemplate(
        title = "Add Manually",
        onBack = { eventSink(AddDeviceManuallyUiEvent.GoBack) },
        content = {
            AddDeviceManuallyScreen(
                state = state,
                onAddNewDevice = vm::addNewDevice,
                eventSink = eventSink,
            )
        }
    )
}

@Composable
private fun AddDeviceManuallyScreen(
    state: AddDeviceManuallyState,
    onAddNewDevice: (AddNewDeviceArgs) -> Unit,
    eventSink: (AddDeviceManuallyUiEvent) -> Unit,
) {
    var deviceName by remember { mutableStateOf("") }
    var areaName by remember { mutableStateOf("") }
    var serialNumber by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .verticalScroll(state = rememberScrollState(), enabled = true)
    ) {
        var focusIndex by remember { mutableStateOf(0) }

        TextInputUi(
            title = "Device Name",
            hint = "Enter Device Name",
            userInput = deviceName,
            isInFocus = false,
            onDoneClick = {
                focusIndex = 1
            },
            onInputChange = {
                deviceName = it
            }
        )
        VerticalSpacer(space = 24.dp)

        TextInputUi(
            title = "Area Name",
            hint = "Enter Area Name",
            userInput = areaName,
            isInFocus = focusIndex == 1,
            onDoneClick = {
                focusIndex = 2
            },
            onInputChange = {
                areaName = it
            }
        )
        VerticalSpacer(space = 24.dp)

        TextInputUi(
            title = "Serial Number",
            hint = "Enter Serial Number",
            userInput = serialNumber,
            isInFocus = focusIndex == 2,
            clearFocusWhenDone = true,
            onDoneClick = {
                focusIndex = 3
            },
            onInputChange = {
                serialNumber = it
            }
        )
        VerticalSpacer(space = 16.dp)

        AnimatedVisibility(visible = !state.deviceAdded) {
            Column(modifier = Modifier.fillMaxWidth()) {
                MotionButton(
                    text = "Add this Device",
                    enabled = deviceName.isNotEmpty() && areaName.isNotEmpty() && serialNumber.isNotEmpty(),
                    onClick = {
                        onAddNewDevice(
                            AddNewDeviceArgs(
                                name = deviceName,
                                areaName = areaName,
                                serialNumber = DeviceSerialNumber.of(serialNumber)
                            )
                        )
                    },
                )
                VerticalSpacer(space = 12.dp)
            }
        }

        AnimatedVisibility(visible = state.deviceAdded) {
            Column(modifier = Modifier.fillMaxWidth()) {
                MotionButton(
                    text = "Add another Device",
                    color = MotionBlack,
                ) {
                    eventSink(AddDeviceManuallyUiEvent.OpenAddAnotherDevice)
                }
                VerticalSpacer(space = 12.dp)
                MotionButton(
                    text = "Learn IR code for this Device",
                    enabled = serialNumber.isNotEmpty(),
                    color = MotionBlue,
                ) {
                    eventSink(
                        AddDeviceManuallyUiEvent.OpenLearnIrCode(
                            DeviceSerialNumber.of(
                                serialNumber
                            )
                        )
                    )
                }
                VerticalSpacer(space = 12.dp)
            }
        }

        MotionButton(text = "Go to Dashboard") {
            eventSink(AddDeviceManuallyUiEvent.GoToDashboard)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddDeviceManuallyPreview() {
    PreviewTheme {
        AddDeviceManuallyScreen(
            state = AddDeviceManuallyState(),
            onAddNewDevice = {},
            eventSink = {},
        )
    }
}