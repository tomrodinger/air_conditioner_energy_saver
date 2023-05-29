package com.app.imotion.ui.screens.ircode

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.imotion.R
import com.app.imotion.ui.components.HorizontalSpacer
import com.app.imotion.ui.components.MotionButton
import com.app.imotion.ui.components.SimpleScreenTemplate
import com.app.imotion.ui.components.VerticalSpacer
import com.app.imotion.ui.theme.MotionGrey
import com.app.imotion.ui.theme.PreviewTheme
import com.app.imotion.ui.utils.ComposeUtils.pxToDp

/**
 * Created by hani@fakhouri.eu on 2023-05-28.
 */

private data class StepData(
    @DrawableRes val icon: Int,
    val iconText: String,
    @DrawableRes val primaryImage: Int? = null,
    @DrawableRes val secondaryImage: Int? = null,
    val title: String? = null,
    val description: String? = null,
)

private fun getSteps() = listOf(
    StepData(
        icon = R.drawable.ir_code_how_it_works,
        iconText = "How it works?",
        title = "Mobile uses infrared to communicate with motion sensor",
        description = "Aim the remote control into the top of the device and press the sync button of the function to connect the device",
    ),
    StepData(
        icon = R.drawable.ir_code_sync,
        iconText = "Synchronize",
        primaryImage = R.drawable.device_signal,
        secondaryImage = R.drawable.hand_with_phone,
        title = "Please Wait",
        description = "Fetching Remote Codes For Your Mobile"
    ),
    StepData(
        icon = R.drawable.ir_code_confirmation,
        iconText = "Confirmation",
        primaryImage = R.drawable.glowing_light_bulb,
        secondaryImage = R.drawable.hand_with_phone,
        title = "Did you hear device blink was light turned on?",
    ),
    StepData(
        icon = R.drawable.ir_code_success,
        iconText = "Success",
        primaryImage = R.drawable.round_green_check,
        secondaryImage = R.drawable.hand_with_phone,
        title = "Your device has been connected successfully",
    ),
)

@Composable
fun IrCodeSetupScreen(
    vm: IrCodeSetupScreenVM = viewModel(),
    onSetupDeviceControls: () -> Unit,
    onBack: () -> Unit,
) {
    SimpleScreenTemplate(
        title = "Learn IR code & Connect",
        onBack = onBack,
        content = {
            AllStepsUi(
                vm = vm,
                onSetupDeviceControls = onSetupDeviceControls,
            )
        }
    )
}

@Composable
private fun AllStepsUi(
    vm: IrCodeSetupScreenVM = viewModel(),
    onSetupDeviceControls: () -> Unit,
) {

    val syncState by vm.state.collectAsStateWithLifecycle()
    val steps = getSteps()
    val totalSteps = steps.size

    var currentStepIndex by remember { mutableStateOf(0) }
    val stepData = steps[currentStepIndex]

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Crossfade(
            targetState = currentStepIndex,
            modifier = Modifier
                .fillMaxSize()
                .weight(1.0F)
        ) { index ->
            StepDataUi(
                stepData = stepData,
                currentStepIndex = index,
                totalSteps = totalSteps,
                animatePrimaryImage = index == 1,
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            when (currentStepIndex) {
                0 -> {
                    MotionButton(text = "Synchronize Now") {
                        vm.startSync()
                        currentStepIndex++
                    }
                }
                1 -> {
                    currentStepIndex = when (syncState) {
                        IrCodeSyncState.Idle -> 2
                        IrCodeSyncState.InProgress -> 1
                    }
                }
                2 -> {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1.0F)
                        ) {
                            MotionButton(
                                text = "No",
                                color = MaterialTheme.colors.onBackground,
                            ) {
                                currentStepIndex = 0
                            }
                        }
                        HorizontalSpacer(space = 16.dp)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1.0F)
                        ) {
                            MotionButton(text = "Yes") {
                                currentStepIndex++
                            }
                        }
                    }
                }
                3 -> {
                    MotionButton(
                        text = "Set Controls",
                        onClick = {
                            vm.navigateToSetupDeviceControls()
                        },
                    )
                }
            }
        }
    }

}

@Composable
private fun StepDataUi(
    stepData: StepData,
    currentStepIndex: Int,
    totalSteps: Int,
    animatePrimaryImage: Boolean = false,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(48.dp)
                .background(color = MaterialTheme.colors.primary)
                .padding(0.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(stepData.icon),
                contentDescription = null
            )
        }
        Text(
            text = stepData.iconText,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W700,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
        VerticalSpacer(space = 16.dp)

        var stepWidth by remember { mutableStateOf(0) }
        val stepWidthSizeDp: Dp by animateDpAsState(
            targetValue = (currentStepIndex * stepWidth).pxToDp(),
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing,
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 72.dp)
                .onGloballyPositioned {
                    if (it.isAttached && stepWidth == 0) {
                        stepWidth = (it.size.width / (totalSteps - 1))
                    }
                },
            contentAlignment = Alignment.Center,
        ) {
            DotsUi(
                totalNrSteps = totalSteps,
                currentStepIndex = currentStepIndex,
                onDotClick = {}
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .background(color = MaterialTheme.colors.primary)
                    .size(height = 2.dp, width = stepWidthSizeDp)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .background(color = MaterialTheme.colors.primary)
                    .size(
                        height = 2.dp,
                        width = if (currentStepIndex <= 1) 0.dp else ((currentStepIndex - 1) * stepWidth).pxToDp()
                    )
            )
        }

        val alphaFrom = 1.0F
        val alphaTo = 0.5F
        val alpha = remember { Animatable(alphaFrom) }
        if (animatePrimaryImage) {
            LaunchedEffect(Unit) {
                alpha.animateTo(
                    targetValue = alphaTo,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800),
                        repeatMode = RepeatMode.Reverse,
                    ),
                )
            }
        }

        stepData.primaryImage?.let { primaryImage ->
            VerticalSpacer(space = 36.dp)
            Image(
                modifier = Modifier
                    .size(88.dp)
                    .alpha(alpha.value),
                painter = painterResource(primaryImage),
                contentDescription = null
            )
            VerticalSpacer(space = 36.dp)
        }

        stepData.secondaryImage?.let { secondaryImage ->
            VerticalSpacer(space = 36.dp)
            Image(
                modifier = Modifier.size(82.dp),
                painter = painterResource(secondaryImage),
                contentDescription = null
            )
            VerticalSpacer(space = 36.dp)
        }

        stepData.title?.let { title ->
            VerticalSpacer(space = 36.dp)
            Text(
                text = title,
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.W700,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
            )
        }

        stepData.description?.let { description ->
            VerticalSpacer(space = 8.dp)
            Text(
                text = description,
                color = MotionGrey,
                fontWeight = FontWeight.W400,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
            )
            VerticalSpacer(space = 16.dp)
        }
    }
}

@Composable
private fun DotsUi(
    totalNrSteps: Int,
    currentStepIndex: Int,
    onDotClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (totalNrSteps > 1) {
            for (index in 0 until totalNrSteps) {
                val stepProgressIndicatorColor = if (currentStepIndex >= index) {
                    MaterialTheme.colors.primary
                } else {
                    Color(0xFFACDCA3)
                }
                Box(
                    Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .clickable {
                            onDotClick(index)
                        }
                        .background(color = stepProgressIndicatorColor)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StepUiPreview() {
    PreviewTheme {
        StepDataUi(
            stepData = StepData(
                icon = R.drawable.ir_code_sync,
                iconText = "Synchronize",
                primaryImage = R.drawable.device_signal,
                secondaryImage = R.drawable.hand_with_phone,
                title = "Please Wait",
                description = "Fetching Remote Codes For Your Mobile"
            ),
            currentStepIndex = 1,
            totalSteps = 4,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LearIrCodeScreenPreview() {
    PreviewTheme {
        IrCodeSetupScreen(
            onBack = {},
            onSetupDeviceControls = {},
        )
    }
}