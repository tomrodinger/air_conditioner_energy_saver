package com.app.imotion.ui.screens.welcome

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.imotion.ui.theme.MotionBlack
import com.app.imotion.ui.theme.PreviewTheme
import com.app.imotion.R
import com.app.imotion.ui.components.*
import com.app.imotion.ui.screens.devices.DevicesOverviewUiEvent

/**
 * Created by hani@fakhouri.eu on 2023-05-24.
 */

@Composable
fun WelcomeScreen(
    eventSink: (DevicesOverviewUiEvent) -> Unit
) {
    val hasCameraHardware = hasCameraHardware(LocalContext.current)
    var showAddDeviceModal by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MainPageHeader {}
        VerticalSpacer(space = 16.dp)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(24.dp)
                ),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                modifier = Modifier.padding(top = 96.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier.size(width = 169.dp, height = 132.dp),
                    painter = painterResource(R.drawable.no_device),
                    contentDescription = null
                )
                VerticalSpacer(space = 16.dp)
                Text(
                    text = "No Device Found",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.W700,
                    color = MaterialTheme.colors.onBackground,
                )
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(MotionBlack.copy(alpha = 0.2F), MotionBlack)
                )
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Welcome to the app",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.W700,
                color = MaterialTheme.colors.onPrimary,
            )
            VerticalSpacer(space = 12.dp)
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Click Add Device button to configure the equipment",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.W600,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
            )
            VerticalSpacer(space = 48.dp)
            MotionButton(
                text = "Add Device",
                icon = R.drawable.add,
                onClick = {
                    if (hasCameraHardware) {
                        showAddDeviceModal = true
                    } else {
                        eventSink(DevicesOverviewUiEvent.AddNewDevice)
                    }
                },
            )
            VerticalSpacer(space = 58.dp)
        }
        val scaleFrom = 1.0F
        val scaleTo = 0.75F
        val scale = remember { Animatable(scaleFrom) }
        LaunchedEffect(Unit) {
            scale.animateTo(
                targetValue = scaleTo,
                animationSpec = infiniteRepeatable(
                    animation = tween(800),
                    repeatMode = RepeatMode.Reverse,
                ),
            )
        }
        Image(
            modifier = Modifier
                .size(width = 40.dp, height = 80.dp)
                .scale(scale.value),
            painter = painterResource(R.drawable.hand_upwards),
            contentDescription = null,
        )
        AnimatedVisibility(
            showAddDeviceModal,
            enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f),
            exit = shrinkVertically() + fadeOut(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.background,
                        shape = RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp,
                        )
                    )
                    .padding(12.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Add Device",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.W700,
                        color = MaterialTheme.colors.onBackground,
                    )
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(24.dp)
                            .clickable {
                                showAddDeviceModal = false
                            },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
                VerticalSpacer(space = 24.dp)
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.onBackground,
                    thickness = 1.dp,
                )
                VerticalSpacer(space = 32.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.0F)
                            .height(120.dp)
                            .background(
                                color = MaterialTheme.colors.secondary,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .clickable {
                                eventSink(DevicesOverviewUiEvent.AddNewDevice)
                            }
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            modifier = Modifier.size(38.dp),
                            painter = painterResource(R.drawable.qr_code_small),
                            contentDescription = null,
                        )
                        VerticalSpacer(space = 12.dp)
                        Text(
                            text = "Scan Code",
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.W600,
                            color = MaterialTheme.colors.onBackground,
                        )
                    }
                    HorizontalSpacer(space = 16.dp)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.0F)
                            .height(120.dp)
                            .background(
                                color = MaterialTheme.colors.secondary,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .clickable {
                                eventSink(DevicesOverviewUiEvent.AddNewDevice)
                            }
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "SN",
                            style = MaterialTheme.typography.h4,
                            fontWeight = FontWeight.W700,
                            color = MaterialTheme.colors.primary,
                        )
                        Text(
                            text = "Add Manually",
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.W600,
                            color = MaterialTheme.colors.onBackground,
                        )
                    }
                }
                VerticalSpacer(space = 24.dp)
            }
        }
    }
}

private fun hasCameraHardware(context: Context): Boolean =
    context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)

@Preview(showBackground = true)
@Composable
private fun WelcomeOverlayPreview() {
    PreviewTheme {
        IMotionSurface {
            WelcomeScreen(eventSink = {})
        }
    }
}