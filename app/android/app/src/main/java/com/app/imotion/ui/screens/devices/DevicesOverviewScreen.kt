package com.app.imotion.ui.screens.devices

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.imotion.R
import com.app.imotion.model.MotionDeviceListEntry
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.navigation.NavRoute
import com.app.imotion.ui.components.*
import com.app.imotion.ui.screens.welcome.WelcomeScreen
import com.app.imotion.ui.theme.MotionBackground
import com.app.imotion.ui.theme.MotionGrey
import com.app.imotion.ui.theme.MotionRed
import com.app.imotion.ui.theme.PreviewTheme

/**
 * Created by hani@fakhouri.eu on 2023-05-23.
 */

object DevicesOverviewScreenScreen : NavRoute(route = "devices/overview")

@Composable
fun DevicesOverviewScreen(
    vm: DevicesOverviewScreenVm = hiltViewModel()
) {
    IMotionSurface {
        val state by vm.state.collectAsStateWithLifecycle()
        when {
            state.loading ->
                Text("Loading...")
            state.devices.isEmpty() ->
                WelcomeScreen(eventSink = vm::onUiEvent)
            else -> {
                DevicesOverviewUi(
                    devices = state.devices,
                    eventSink = vm::onUiEvent,
                )
            }
        }
    }
}

@Composable
private fun DevicesOverviewUi(
    devices: List<MotionDeviceListEntry>,
    eventSink: (DevicesOverviewUiEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            )
    ) {
        MainPageHeader {}
        VerticalSpacer(space = 16.dp)
        Box {
            Card(
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "All Devices",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.W700,
                        color = MaterialTheme.colors.onSecondary,
                    )
                    VerticalSpacer(space = 16.dp)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .weight(1.0F)
                        ) {
                            items(devices) { device ->
                                DeviceCard(
                                    device = device,
                                    onClick = {
                                        eventSink(
                                            DevicesOverviewUiEvent.OpenDeviceOverview(device.serialNumber)
                                        )
                                    }
                                )
                            }
                        }
                        MotionButton(
                            text = "Add Device",
                            icon = R.drawable.add,
                            onClick = {
                                eventSink(DevicesOverviewUiEvent.AddNewDevice)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DeviceCard(
    device: MotionDeviceListEntry,
    onClick: (DeviceSerialNumber) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick(device.serialNumber) }
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.secondary)
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0F)
                    .background(color = MaterialTheme.colors.secondary)
                    .padding(8.dp)
            ) {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.W600,
                    color = MaterialTheme.colors.onSecondary,
                )
                VerticalSpacer(space = 8.dp)
                Text(
                    text = device.area,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W400,
                    color = MotionGrey,
                )
            }
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colors.secondary)
                    .padding(8.dp)
            ) {
                val boxColor = if (device.isActive) MaterialTheme.colors.primary else MotionRed
                val text = if (device.isActive) "Active" else "Inactive"
                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .background(
                            color = boxColor,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(vertical = 2.dp, horizontal = 8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = text,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W700,
                        color = MotionBackground,
                    )
                }
                VerticalSpacer(space = 8.dp)
                Row {
                    Text(
                        text = "SN - ",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W600,
                        color = MaterialTheme.colors.primary,
                    )
                    Text(
                        text = device.serialNumber.value.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.W600,
                        color = MaterialTheme.colors.onSecondary,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DevicesOverviewScreenPreview() {
    PreviewTheme {
        DevicesOverviewUi(
            devices = listOf(
                MotionDeviceListEntry(
                    "Device 1",
                    "Living Room",
                    true,
                    DeviceSerialNumber.of("QWEDSA")
                ),
                MotionDeviceListEntry(
                    "Device 2",
                    "Kitchen Room",
                    false,
                    DeviceSerialNumber.of("QerDSA")
                ),
                MotionDeviceListEntry(
                    "Device Three",
                    "Bedroom",
                    true,
                    DeviceSerialNumber.of("CCWRRDSA")
                ),
                MotionDeviceListEntry(
                    "Device Four",
                    "Living Room",
                    true,
                    DeviceSerialNumber.of("TFWEDSA")
                ),
            ),
            eventSink = {},
        )
    }
}