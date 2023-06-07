package com.app.imotion.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.imotion.model.prefixZero

/**
 * Created by hani.fakhouri on 2023-06-06.
 */

@Composable
fun TimeComponent(
    value: Int,
    suffix: String = "",
) {
    Row {
        Text(
            text = value.prefixZero(),
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W500,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
        )
        if (suffix.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(bottom = 4.dp),
                text = suffix.lowercase(),
                color = MaterialTheme.colors.onBackground,
            )
        }
    }
}