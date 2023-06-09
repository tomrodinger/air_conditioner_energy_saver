package com.app.imotion.ui.screens.devices

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.navigation.NavRoute
import com.app.imotion.navigation.navigateTo

/**
 * Created by hani.fakhouri on 2023-06-09.
 */

object DevicesOverviewScreen : NavRoute(route = "devices/overview")

fun NavController.navigateToDevicesOverview(navOptions: NavOptions? = null) {
    this.navigateTo(DevicesOverviewScreen, navOptions)
}

fun NavController.popToDevicesOverview() {
    this.popBackStack(
        DevicesOverviewScreen.route,
        inclusive = false
    )
}

fun NavGraphBuilder.devicesOverviewScreen(
    onAddNewDevice: () -> Unit,
    onOpenDeviceOverview: (DeviceSerialNumber) -> Unit,
) {
    composable(route = DevicesOverviewScreen.route) {
        DevicesOverviewScreen(
            onAddNewDevice = onAddNewDevice,
            onOpenDeviceOverview = onOpenDeviceOverview,
        )
    }
}