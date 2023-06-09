package com.app.imotion.ui.screens.device

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.navigation.NavRoute

/**
 * Created by hani.fakhouri on 2023-06-09.
 */

object DeviceOverviewScreen : NavRoute(route = "device/overview/{device-sn}") {
    fun buildRoute(
        deviceSerialNumber: DeviceSerialNumber
    ) = "device/overview/${deviceSerialNumber.value}"
}

fun NavController.navigateToDeviceOverview(
    deviceSerialNumber: DeviceSerialNumber,
    navOptions: NavOptions? = null
) {
    this.navigate(DeviceOverviewScreen.buildRoute(deviceSerialNumber), navOptions)
}

fun NavGraphBuilder.deviceOverviewScreen(
    onGoToDashboard: () -> Unit,
    onOpenIrCodesPage: (DeviceSerialNumber) -> Unit,
    onOpenTriggerRuleSetupPage: (DeviceSerialNumber) -> Unit,
    onOpenLearnIrCode: (DeviceSerialNumber) -> Unit,
    onBack: () -> Unit,
) {
    composable(
        route = DeviceOverviewScreen.route,
        arguments = listOf(
            navArgument("device-sn") {
                type = NavType.StringType
                defaultValue = ""
            },
        )
    ) {
        val deviceSn = it.arguments?.getString("device-sn") ?: ""
        val vm: DeviceOverviewScreenVm = hiltViewModel<DeviceOverviewScreenVm>().apply {
            onCreate(DeviceSerialNumber.of(deviceSn))
        }
        DeviceOverviewRoute(
            onGoToDashboard = onGoToDashboard,
            onOpenIrCodesPage = onOpenIrCodesPage,
            onOpenTriggerRuleSetupPage = onOpenTriggerRuleSetupPage,
            onOpenLearnIrCode = onOpenLearnIrCode,
            onBack = onBack,
            vm = vm,
        )
    }
}