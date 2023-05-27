package com.app.imotion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.imotion.ui.components.Header
import com.app.imotion.ui.components.IMotionSurface
import com.app.imotion.ui.theme.PreviewTheme

/**
 * Created by hani@fakhouri.eu on 2023-05-25.
 */

@Composable
fun ScreenTemplate(
    headerContent: @Composable () -> Unit,
    modalContent: @Composable () -> Unit,
) {
    IMotionSurface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            headerContent()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colors.background,
                        shape = RoundedCornerShape(
                            topStart = 32.dp,
                            topEnd = 32.dp,
                        )
                    )
                    .padding(16.dp),
            ) {
                modalContent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenTemplatePreview() {
    PreviewTheme {
        ScreenTemplate(
            headerContent = {
                Header(
                    title = "Scan Code",
                    onBack = {}
                )
            },
            modalContent = {
                Text("Content", color = Color.Red)
            }
        )
    }
}