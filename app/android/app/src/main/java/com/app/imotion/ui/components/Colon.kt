package com.app.imotion.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Created by hani.fakhouri on 2023-06-06.
 */

@Composable
fun Colon() {
    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = ":",
        color = MaterialTheme.colors.onBackground,
        fontWeight = FontWeight.W500,
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
    )
}