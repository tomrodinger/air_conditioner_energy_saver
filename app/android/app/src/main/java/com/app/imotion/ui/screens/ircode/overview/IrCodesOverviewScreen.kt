package com.app.imotion.ui.screens.ircode.overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.imotion.R
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.IrCode
import com.app.imotion.ui.components.*
import com.app.imotion.ui.theme.PreviewTheme
import com.app.imotion.utils.AndroidUtils
import kotlin.math.absoluteValue

/**
 * Created by hani.fakhouri on 2023-06-07.
 */

@Composable
fun IrCodesOverviewScreen(
    deviceSerialNumber: DeviceSerialNumber,
    irCodes: List<IrCode>,
    onAddNewIrCode: (DeviceSerialNumber) -> Unit,
    openDashboard: () -> Unit,
    onBack: () -> Unit,
) {
    ScreenTemplate(
        headerContent = {
            Header(
                title = "IR Codes Management",
                iconAction1 = HeaderIconAction(
                    iconRes = R.drawable.add_simple,
                    action = {
                        onAddNewIrCode(deviceSerialNumber)
                    }
                ),
                onBack = onBack,
            )
        },
        modalContent = {
            IrCodesOverviewUi(
                deviceSerialNumber = deviceSerialNumber,
                irCodes = irCodes,
                openDashboard = openDashboard,
            )
        }
    )
}

@Composable
private fun IrCodesOverviewUi(
    deviceSerialNumber: DeviceSerialNumber,
    irCodes: List<IrCode>,
    openDashboard: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.0F)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(irCodes) { irCode ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        IrCodeEntry(
                            irCode = irCode,
                            onClick = { },
                            onDelete = { },
                        )
                        Divider()
                    }
                }
            }

            VerticalSpacer(space = 24.dp)
            Text(
                text = "To add a new IR code, Press the + on the top right.",
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.W500,
                style = MaterialTheme.typography.body1,
            )
            VerticalSpacer(space = 8.dp)
            Text(
                text = "To delete an IR code, swipe it left and press the red Bin.",
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.W500,
                style = MaterialTheme.typography.body1,
            )
        }
        Column {
            MotionButton(text = "Go To Dashboard") {
                openDashboard()
            }
        }
    }
}

@Composable
private fun IrCodeEntry(
    irCode: IrCode,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    val context = LocalContext.current
    var offsetX by remember { mutableStateOf(0f) }
    var showDeleteButton by remember { mutableStateOf(false) }
    val dragUntilShowDeleteDp = 32.dp
    Box(modifier = Modifier
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectHorizontalDragGestures(
                onDragEnd = {
                    offsetX = 0F
                },
                onDragCancel = {
                    offsetX = 0F
                },
            ) { change, dragAmount ->
                change.consume()
                if (dragAmount < 0 || offsetX < 0) {
                    offsetX += dragAmount
                }
                showDeleteButton =
                    AndroidUtils.pxToDp(
                        offsetX.absoluteValue,
                        context
                    ).dp >= dragUntilShowDeleteDp
            }
        }
    ) {
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = irCode.readableName,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W400,
            style = MaterialTheme.typography.body1,
        )
        Row(
            Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        showDeleteButton = false
                        onClick()
                    }
                    .background(color = MaterialTheme.colors.primary, shape = CircleShape)
                    .padding(4.dp),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(R.drawable.ir_code_hand),
                    contentDescription = null
                )
            }
            AnimatedVisibility(visible = showDeleteButton) {
                Image(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(16.dp)
                        .clickable {
                            showDeleteButton = false
                            onDelete()
                        },
                    painter = painterResource(R.drawable.bin),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IrCodesOverviewScreenPreview() {
    PreviewTheme {
        IrCodesOverviewScreen(
            deviceSerialNumber = DeviceSerialNumber.of(""),
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
            onAddNewIrCode = {},
            openDashboard = {},
            onBack = {},
        )
    }
}