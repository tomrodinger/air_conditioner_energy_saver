package com.app.imotion.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

/**
 * Created by hani.fakhouri on 2023-06-06.
 */

@Composable
fun SwitchWithText(
    text: String,
    checked: Boolean,
    onCheckChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = text,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W700,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
        Switch(
            modifier = Modifier.align(Alignment.CenterEnd),
            checked = checked,
            onCheckedChange = onCheckChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.primary,
            )
        )
    }
}