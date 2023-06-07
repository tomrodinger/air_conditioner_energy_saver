package com.app.imotion.ui.screens.device

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.imotion.model.*
import com.app.imotion.ui.components.SimpleScreenTemplate
import com.app.imotion.ui.components.VerticalSpacer
import com.app.imotion.ui.theme.PreviewTheme
import com.app.imotion.R
import com.app.imotion.ui.components.MotionButton
import com.app.imotion.ui.theme.MotionBlack
import com.app.imotion.ui.theme.MotionBlue

/**
 * Created by hani.fakhouri on 2023-06-07.
 */

@Composable
fun DeviceOverviewScreen(
    device: DeviceData,
    openIrCodes: (DeviceSerialNumber) -> Unit,
    openAddNewTriggerRule: (DeviceSerialNumber) -> Unit,
    openDashboard: () -> Unit,
    onBack: () -> Unit,
) {
    SimpleScreenTemplate(
        title = device.name,
        onBack = onBack,
        content = {
            DeviceOverviewUi(
                device = device,
                openIrCodes = openIrCodes,
                openAddNewTriggerRule = openAddNewTriggerRule,
                openDashboard = openDashboard,
            )
        }
    )
}

@Composable
private fun DeviceOverviewUi(
    device: DeviceData,
    openIrCodes: (DeviceSerialNumber) -> Unit,
    openAddNewTriggerRule: (DeviceSerialNumber) -> Unit,
    openDashboard: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.0F)
        ) {
            BatteryIndicator(device.batterPercentage)
            VerticalSpacer(space = 24.dp)

            FirmwareVersion(device.firmwareVersion)
            VerticalSpacer(space = 24.dp)

            var selectedTabIndex by remember { mutableStateOf(0) }
            val tabs = listOf("IR Codes", "Trigger rules")

            Column {
                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = selectedTabIndex,
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    text = title,
                                )
                            }
                        )
                    }
                }
                when (selectedTabIndex) {
                    0 -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(device.irCodes) { irCode ->
                                Column(modifier = Modifier
                                    .clickable { }
                                    .fillMaxWidth()
                                ) {
                                    Text(
                                        modifier = Modifier.padding(vertical = 12.dp),
                                        text = irCode.readableName,
                                        color = MaterialTheme.colors.onBackground,
                                        fontWeight = FontWeight.W400,
                                        style = MaterialTheme.typography.body1,
                                    )
                                    Divider()
                                }
                            }
                        }
                    }
                    1 -> {
                        Text(
                            color = Color.Black,
                            text = "TODO",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }

        }

        Column(modifier = Modifier.fillMaxWidth()) {
            VerticalSpacer(space = 12.dp)
            MotionButton(text = "IR Codes", color = MotionBlack) {
                openIrCodes(device.serialNumber)
            }
            VerticalSpacer(space = 12.dp)
            MotionButton(text = "Add Control Rule", color = MotionBlue) {
                openAddNewTriggerRule(device.serialNumber)
            }
            VerticalSpacer(space = 12.dp)
            MotionButton(text = "Go To Dashboard") {
                openDashboard()
            }
        }
    }
}

@Composable
private fun FirmwareVersion(version: DeviceFirmwareVersion) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = "Firmware Version",
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W500,
            style = MaterialTheme.typography.body1,
        )
        Text(
            modifier = Modifier.align(Alignment.CenterEnd),
            text = version.value,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W500,
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
private fun BatteryIndicator(
    batterPercentage: Float,
) {
    val showBatteryPercentageOnIndicator = batterPercentage >= 25F
    val batteryPercentageValue = BatteryPercentageValue.of(batterPercentage)
    val batteryTitle = if (showBatteryPercentageOnIndicator) "Battery" else
        "Battery (${batterPercentage.toInt()}%)"
    Text(
        text = batteryTitle,
        color = MaterialTheme.colors.onBackground,
        fontWeight = FontWeight.W500,
        style = MaterialTheme.typography.body1,
    )
    VerticalSpacer(space = 16.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(
                color = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        val widthWeight = (batterPercentage / 100)
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(widthWeight)
                .background(
                    color = batterPercentage.BatteryToColor(),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        bottomStart = 16.dp,
                        topEnd = if (batterPercentage >= 98F) 16.dp else 0.dp,
                        bottomEnd = if (batterPercentage >= 98F) 16.dp else 0.dp,
                    )
                )
        ) {
            val color =
                if (batteryPercentageValue != BatteryPercentageValue.HIGH)
                    MaterialTheme.colors.onBackground else MaterialTheme.colors.onPrimary
            if (showBatteryPercentageOnIndicator) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.battery_power),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = color)
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "${batterPercentage.toInt()}%",
                        color = color,
                        fontWeight = FontWeight.W500,
                        style = MaterialTheme.typography.h5,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AllDevicesScreenPreview() {
    PreviewTheme {
        DeviceOverviewScreen(
            device = DeviceData(
                name = "Device Name",
                serialNumber = DeviceSerialNumber.of("1234-5678-90"),
                batterPercentage = 75F,
                firmwareVersion = DeviceFirmwareVersion.of("2.1.615"),
                irCodes = listOf(
                    IrCode(readableName = "IR Code 1"),
                    IrCode(readableName = "IR Code 2"),
                    IrCode(readableName = "IR Code 3"),
                    IrCode(readableName = "IR Code 4"),
                    IrCode(readableName = "IR Code 5"),
                    IrCode(readableName = "IR Code 6"),
                    IrCode(readableName = "IR Code 7"),
                    IrCode(readableName = "IR Code 8"),
                ),
            ),
            openIrCodes = {},
            openAddNewTriggerRule = {},
            openDashboard = {},
            onBack = {}
        )
    }
}