package com.app.imotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.imotion.model.Device
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.ui.components.IMotionSurface
import com.app.imotion.ui.components.MainPageHeader
import com.app.imotion.ui.components.VerticalSpacer
import com.app.imotion.ui.screens.devices.AllDevicesScreen
import com.app.imotion.ui.theme.IMotionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMotionTheme {
                IMotionSurface {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        MainPageHeader {

                        }
                        VerticalSpacer(space = 16.dp)
                        Box(modifier = Modifier.fillMaxWidth()) {
                            AllDevicesScreen(
                                devices = listOf(
                                    Device(
                                        "Device 1",
                                        "Living Room",
                                        true,
                                        DeviceSerialNumber("QWEDSA")
                                    ),
                                    Device(
                                        "Device 2",
                                        "Kitchen Room",
                                        false,
                                        DeviceSerialNumber("QerDSA")
                                    ),
                                    Device(
                                        "Device Three",
                                        "Bedroom",
                                        true,
                                        DeviceSerialNumber("CCWRRDSA")
                                    ),
                                    Device(
                                        "Device Four",
                                        "Living Room",
                                        true,
                                        DeviceSerialNumber("TFWEDSA")
                                    ),
                                ),
                                onAddDevice = {}
                            )
                        }
                    }
                }
            }
        }
    }
}