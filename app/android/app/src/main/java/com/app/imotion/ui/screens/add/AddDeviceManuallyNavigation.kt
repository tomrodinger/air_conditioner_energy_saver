package com.app.imotion.ui.screens.add

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.app.imotion.navigation.NavRoute
import com.app.imotion.navigation.navigateTo
import com.app.imotion.ui.screens.devices.DevicesOverviewScreen

/**
 * Created by hani.fakhouri on 2023-06-09.
 */

object AddDeviceManuallyScreen : NavRoute(route = "device/add")

fun NavController.navigateToAddDeviceManually(navOptions: NavOptions? = null) {
    this.navigateTo(AddDeviceManuallyScreen, navOptions)
}

fun NavController.navigateToAddDeviceManuallyAndPopupToDashBoard() {
    this.navigateTo(
        navRoute = AddDeviceManuallyScreen,
        navOptions = navOptions {
            popUpTo(DevicesOverviewScreen.route)
        }
    )
}

fun NavGraphBuilder.addDeviceManuallyScreen(
    eventSink: (AddDeviceManuallyUiEvent) -> Unit,
) {
    composable(AddDeviceManuallyScreen.route) {
        AddDeviceManuallyRoute(eventSink)
    }
}