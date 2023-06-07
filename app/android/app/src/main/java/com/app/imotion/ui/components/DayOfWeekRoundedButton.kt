package com.app.imotion.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Created by hani.fakhouri on 2023-06-06.
 */

@Composable
fun DayOfWeekRoundedButton(
    day: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
) {
    val backgroundColor = if (isSelected) MaterialTheme.colors.primary else
        MaterialTheme.colors.secondary
    val alpha = if (isSelected) 1.0F else 0.3F
    Box(
        modifier = Modifier.alpha(alpha),
        contentAlignment = Alignment.Center,
    ) {
        OutlinedButton(
            onClick = onSelected,
            shape = CircleShape,
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .size(42.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = backgroundColor,
                disabledContentColor = Color.Black.copy(alpha = 0.3F),
            )
        ) {
            // Leave empty
        }
        Text(
            text = day.uppercase(),
            color = if (isSelected) MaterialTheme.colors.onPrimary else
                MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W500,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
    }
}