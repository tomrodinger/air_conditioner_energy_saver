package com.app.imotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.imotion.extensions.collectWith
import com.app.imotion.permission.PermissionHandler
import com.app.imotion.permission.PermissionState
import com.app.imotion.settings.AppSettings
import com.app.imotion.ui.screens.PermissionRationaleScreen
import com.app.imotion.ui.screens.onboarding.OnBoardingScreen
import com.app.imotion.ui.screens.welcome.WelcomeScreen
import com.app.imotion.ui.theme.IMotionTheme

class MainActivity : ComponentActivity() {

    private lateinit var permissionHandler: PermissionHandler
    private var bleReady by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settings = AppSettings.getInstance(this.applicationContext)
        permissionHandler = PermissionHandler(this)
        // Listen to permission state changes
        permissionHandler.state.collectWith(this) {
            onNewPermissionState(it)
        }
        permissionHandler.setPerformCheckOnResume(false)

        setContent {
            IMotionTheme {
                val permissionState by permissionHandler.state.collectAsStateWithLifecycle()
                LaunchedEffect(Unit) {
                    if (settings.onBoardingDone) {
                        permissionHandler.performBLEPermissionCheck()
                    }
                }
                when {
                    permissionState.showPermissionRationalScreen ->
                        PermissionRationaleScreen(message = permissionState.rationaleMessage ?: "")
                    !settings.onBoardingDone ->
                        OnBoardingScreen {
                            settings.onBoardingDone = true
                            permissionHandler.performBLEPermissionCheck()
                            permissionHandler.setPerformCheckOnResume(true)
                        }
                    else -> {
                        WelcomeScreen(
                            openQrScanner = {},
                            openManualAddDeviceModel = {},
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (permissionHandler.state.value.performCheckOnResume) {
            permissionHandler.performBLEPermissionCheck()
        }
    }

    private fun onNewPermissionState(state: PermissionState) {
        bleReady = state.hasPermission && state.isEnabled
    }
}