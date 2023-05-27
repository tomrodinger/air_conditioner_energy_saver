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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.imotion.R
import com.app.imotion.model.Device
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.ui.components.MotionButton
import com.app.imotion.ui.components.VerticalSpacer
import com.app.imotion.ui.theme.MotionBackground
import com.app.imotion.ui.theme.MotionGrey
import com.app.imotion.ui.theme.MotionRed
import com.app.imotion.ui.theme.PreviewTheme

/**
 * Created by hani@fakhouri.eu on 2023-05-23.
 */

@Composable
fun AllDevicesScreen(
    devices: List<Device>,
    onAddDevice: () -> Unit,
) {
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

                                }
                            )
                        }
                    }
                    MotionButton(
                        text = "Add Device",
                        icon = R.drawable.add,
                        onClick = {
                            onAddDevice
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DeviceCard(
    device: Device,
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
                        text = device.serialNumber.sn.uppercase(),
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
private fun AllDevicesScreenPreview() {
    PreviewTheme {
        AllDevicesScreen(
            devices = listOf(
                Device("Device 1", "Living Room", true, DeviceSerialNumber("QWEDSA")),
                Device("Device 2", "Kitchen Room", false, DeviceSerialNumber("QerDSA")),
                Device("Device Three", "Bedroom", true, DeviceSerialNumber("CCWRRDSA")),
                Device("Device Four", "Living Room", true, DeviceSerialNumber("TFWEDSA")),
            ),
            onAddDevice = {}
        )
    }
}