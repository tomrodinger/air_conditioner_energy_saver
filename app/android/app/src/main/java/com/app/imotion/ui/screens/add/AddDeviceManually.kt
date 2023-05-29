package com.app.imotion.ui.screens.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.imotion.ui.components.MotionButton
import com.app.imotion.ui.components.SimpleScreenTemplate
import com.app.imotion.ui.components.VerticalSpacer
import com.app.imotion.ui.theme.MotionGrey
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

@Composable
private fun TextInputUi(
    title: String,
    hint: String,
    userInput: String,
    isInFocus: Boolean,
    clearFocusWhenDone: Boolean = false,
    onInputChange: (String) -> Unit,
    onDoneClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    if (isInFocus) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.W700,
            color = MaterialTheme.colors.onBackground,
        )
        VerticalSpacer(space = 4.dp)
        OutlinedTextField(
            value = userInput,
            onValueChange = onInputChange,
            label = {
                if (userInput.isEmpty()) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.W400,
                        color = MotionGrey,
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                focusedBorderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.4F),
                cursorColor = MaterialTheme.colors.primary.copy(alpha = 0.5F),
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (clearFocusWhenDone) {
                        focusManager.clearFocus()
                    }
                    onDoneClick()
                }
            ),
            trailingIcon = {
                if (userInput.isNotEmpty()) {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onInputChange("")
                            },
                        colorFilter = ColorFilter.tint(color = MotionGrey),
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
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