package com.app.imotion.ui.screens.add

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.imotion.ui.theme.PreviewTheme

/**
 * Created by hani.fakhouri@verisure.com on 2023-05-27.
 */

@Composable
fun AddDeviceManually() {
    Text(text = "This is a test")
}

@Preview(showBackground = true)
@Composable
private fun AddDeviceManuallyPreview() {
    PreviewTheme {
        AddDeviceManually()
    }
}