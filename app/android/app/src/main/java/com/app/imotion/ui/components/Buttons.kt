package com.app.imotion.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.imotion.ui.theme.MotionBackground

/**
 * Created by hani@fakhouri.eu on 2023-05-24.
 */

@Composable
fun MotionButton(
    text: String,
    @DrawableRes icon: Int? = null,
    color: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(
                color = color,
                shape = CircleShape
            )
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.let { icon ->
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(icon),
                    contentDescription = null,
                )
                HorizontalSpacer(space = 12.dp)
            }
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.W700,
                color = MotionBackground,
            )
        }
    }
}