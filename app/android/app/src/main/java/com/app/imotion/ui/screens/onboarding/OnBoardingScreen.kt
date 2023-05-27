package com.app.imotion.ui.screens.onboarding

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.imotion.ui.components.VerticalSpacer
import com.app.imotion.ui.theme.PreviewTheme

/**
 * Created by hani@fakhouri.eu on 2023-05-22.
 */

private val steps = OnBoardingStepData.getStepsData()

@Composable
fun OnBoardingScreen(
    onDone: () -> Unit,
) {
    var currentStepIndex by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary),
    ) {
        Crossfade(
            targetState = currentStepIndex,
            modifier = Modifier
                .fillMaxSize()
                .weight(1.25F),
        ) { currentStepIndex ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when (currentStepIndex) {
                    0 -> {
                        Image(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .height(380.dp)
                                .padding(top = 76.dp),
                            contentScale = ContentScale.Crop,
                            painter = painterResource(steps[currentStepIndex].image),
                            contentDescription = "step image"
                        )
                    }
                    1 -> {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(380.dp)
                                .padding(top = 76.dp),
                            contentScale = ContentScale.FillBounds,
                            painter = painterResource(steps[currentStepIndex].image),
                            contentDescription = "step image"
                        )
                    }
                    2 -> {
                        Image(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .height(380.dp)
                                .padding(top = 76.dp),
                            contentScale = ContentScale.Crop,
                            painter = painterResource(steps[2].image),
                            contentDescription = "step image"
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.0F)
                .padding(vertical = 24.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = steps[currentStepIndex].title,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center,
                )
                VerticalSpacer(space = 8.dp)
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 72.dp),
                    text = steps[currentStepIndex].description,
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W400,
                    lineHeight = 16.sp,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(vertical = 8.dp),
            ) {
                DotsProgressLeftUi(
                    alpha = if (currentStepIndex == 0) 0.0F else 1.0F,
                    onClick = { currentStepIndex-- }
                )

                DotsUi(
                    totalNrSteps = steps.size,
                    currentStepIndex = currentStepIndex,
                    onDotClick = {
                        currentStepIndex = it
                    }
                )

                DotsProgressRightUi(
                    totalNrSteps = steps.size,
                    currentStepIndex = currentStepIndex,
                    onClick = {
                        if (currentStepIndex == steps.lastIndex) {
                            onDone()
                        } else {
                            currentStepIndex++
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun BoxScope.DotsProgressLeftUi(
    alpha: Float,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .align(Alignment.CenterStart)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(16.dp)
            .alpha(alpha),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            color = MaterialTheme.colors.onSecondary,
            text = "Back".uppercase(),
            fontWeight = FontWeight.W500,
        )
    }
}

@Composable
private fun BoxScope.DotsUi(
    totalNrSteps: Int,
    currentStepIndex: Int,
    onDotClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.align(Alignment.Center),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (totalNrSteps > 1) {
            for (index in 0 until totalNrSteps) {
                val stepProgressIndicatorSizeDp: Dp by animateDpAsState(
                    targetValue = if (currentStepIndex == index) 33.dp else 10.dp,
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = LinearEasing,
                    )
                )
                val stepProgressIndicatorColor = if (currentStepIndex == index) {
                    MaterialTheme.colors.primary
                } else {
                    Color(0xFFACDCA3)
                }
                Box(
                    Modifier
                        .padding(horizontal = 6.dp)
                        .width(stepProgressIndicatorSizeDp)
                        .height(10.dp)
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

@Composable
private fun BoxScope.DotsProgressRightUi(
    totalNrSteps: Int,
    currentStepIndex: Int,
    onClick: () -> Unit,
) {
    val nextText = if (currentStepIndex == totalNrSteps - 1) {
        "Done"
    } else {
        "Next"
    }
    Row(
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            color = MaterialTheme.colors.onSecondary,
            text = nextText.uppercase(),
            fontWeight = FontWeight.W500,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnBoardingScreenPreview() {
    PreviewTheme {
        OnBoardingScreen(
            onDone = {}
        )
    }
}