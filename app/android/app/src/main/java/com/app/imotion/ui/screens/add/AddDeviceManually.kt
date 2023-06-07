package com.app.imotion.ui.screens.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.imotion.ui.components.MotionButton
import com.app.imotion.ui.components.SimpleScreenTemplate
import com.app.imotion.ui.components.TextInputUi
import com.app.imotion.ui.components.VerticalSpacer
import com.app.imotion.ui.theme.PreviewTheme

/**
 * Created by hani@fakhouri.eu on 2023-05-27.
 */

@Composable
fun AddDeviceManually(
    onBack: () -> Unit,
    onOpenLearnIrCode: () -> Unit,
) {
    SimpleScreenTemplate(
        title = "Add Manually",
        onBack = onBack,
        content = {
            DeviceDataUi(
                onOpenLearnIrCode = onOpenLearnIrCode
            )
        }
    )
}

@Composable
private fun DeviceDataUi(
    onOpenLearnIrCode: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .verticalScroll(state = rememberScrollState(), enabled = true)
    ) {
        var deviceName by remember { mutableStateOf("") }
        var areaName by remember { mutableStateOf("") }
        var serialNumber by remember { mutableStateOf("") }
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
        VerticalSpacer(space = 36.dp)

        MotionButton(
            text = "Learn IR Code",
            onClick = onOpenLearnIrCode,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddDeviceManuallyPreview() {
    PreviewTheme {
        AddDeviceManually(
            onBack = {},
            onOpenLearnIrCode = {},
        )
    }
}