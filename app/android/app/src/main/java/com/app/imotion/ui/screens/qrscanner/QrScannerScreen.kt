package com.app.imotion.ui.screens.qrscanner

import android.content.Intent
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.imotion.R
import com.app.imotion.ui.components.Header
import com.app.imotion.ui.components.MotionButton
import com.app.imotion.ui.components.ScreenTemplate
import com.app.imotion.ui.components.VerticalSpacer
import com.app.imotion.ui.theme.MotionGrey
import com.app.imotion.ui.theme.PreviewTheme
import com.google.accompanist.permissions.*

/**
 * Created by hani@fakhouri.eu on 2023-05-25.
 */

@Composable
fun QrScannerScreen() {
    ScreenTemplate(
        headerContent = {
            Header(
                title = "Scan Code",
                onBack = {}
            )
        },
        modalContent = {
            MainContent()
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun MainContent() {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    if (cameraPermissionState.status.isGranted) {
        QRScanner()
    } else {
        PermissionUi(cameraPermissionState)
    }
}

@Composable
private fun QRScanner() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(width = 160.dp, height = 178.dp),
            painter = painterResource(R.drawable.qr_code_big),
            contentDescription = null
        )
        VerticalSpacer(space = 42.dp)
        Text(
            text = "Please Put QR Code Within Frame To Scan",
            color = MotionGrey,
            fontWeight = FontWeight.W400,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
        VerticalSpacer(space = 16.dp)
        Text(
            text = "Please click on 'Open Camera' to start scanning.",
            color = MotionGrey,
            fontWeight = FontWeight.W400,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
        VerticalSpacer(space = 32.dp)
        val context = LocalContext.current
        MotionButton(text = "Open Camera") {
            context.startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionUi(cameraPermissionState: PermissionState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
            "In order to be able to scan QR Code the app needs access to the device's camera.\n\nPlease click on 'Open Settings' and grant access to Camera."
        } else {
            "In order to be able to scan QR Code the app needs access to the device's camera.\n\nPlease click on 'Grant Permission' to scan QR code."
        }
        val buttonText = if (cameraPermissionState.status.shouldShowRationale) {
            "Open Settings"
        } else {
            "Grant Permission"
        }
        val action = if (cameraPermissionState.status.shouldShowRationale) {
            { /* TODO: Open app settings */ }
        } else {
            { cameraPermissionState.launchPermissionRequest() }
        }
        Image(
            modifier = Modifier.size(width = 160.dp, height = 178.dp),
            painter = painterResource(R.drawable.qr_code_big),
            contentDescription = null
        )
        VerticalSpacer(space = 42.dp)
        Text(
            text = textToShow,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W500,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
        VerticalSpacer(space = 32.dp)
        MotionButton(text = buttonText) {
            action.invoke()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QrScannerScreenPreview() {
    PreviewTheme {
        QrScannerScreen()
    }
}