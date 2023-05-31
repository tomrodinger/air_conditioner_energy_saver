package com.app.imotion.ui.screens

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.imotion.ui.components.IMotionSurface
import com.app.imotion.ui.components.MotionButton
import com.app.imotion.ui.components.VerticalSpacer

/**
 * Created by hani.fakhouri on 2023-05-30.
 */
@Composable
fun PermissionRationaleScreen(
    message: String,
) {
    val context = LocalContext.current
    IMotionSurface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.W700,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            VerticalSpacer(space = 16.dp)

            MotionButton(text = "Open App Settings") {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
        }
    }
}