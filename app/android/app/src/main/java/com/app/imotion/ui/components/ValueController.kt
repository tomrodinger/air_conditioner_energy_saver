package com.app.imotion.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Created by hani.fakhouri on 2023-06-06.
 */

typealias onSelected = () -> Unit

object ValueControllerPressSensitivity {
    const val HIGH = 100L
    const val LOW = 500L
}

@Composable
fun ValueController(
    @DrawableRes icon: Int,
    iconSize: Dp = 28.dp,
    pressSensitivity: Long = ValueControllerPressSensitivity.LOW,
    onClick: onSelected,
) {
    ValueController(
        imageContent = {
            Image(
                modifier = Modifier.size(iconSize),
                painter = painterResource(icon),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
                contentDescription = null
            )
        },
        pressSensitivity = pressSensitivity,
        onClick = onClick,
    )
}

@Composable
private fun ValueController(
    imageContent: @Composable () -> Unit,
    pressSensitivity: Long,
    onClick: onSelected,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isPressed by remember { mutableStateOf(false) }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            isPressed = interaction is PressInteraction.Press
        }
    }
    LaunchedEffect(isPressed) {
        while (isPressed) {
            delay(pressSensitivity)
            onClick()
        }
    }
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        imageContent()
    }
}