package com.app.imotion.ui.screens.ircode.overview

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.navigation.NavRoute

/**
 * Created by hani.fakhouri on 2023-06-09.
 */

object IrCodesOverviewScreen : NavRoute(route = "ircode/overview/{device-sn}") {
    fun buildRoute(
        deviceSerialNumber: DeviceSerialNumber
    ) = "ircode/overview/${deviceSerialNumber.value}"
}

fun NavController.navigateToIrCodesOverview(
    deviceSerialNumber: DeviceSerialNumber,
    navOptions: NavOptions? = null
) {
    this.navigate(IrCodesOverviewScreen.buildRoute(deviceSerialNumber), navOptions)
}

fun NavGraphBuilder.irCodesOverviewScreen(
    onAddNewIrCode: (DeviceSerialNumber) -> Unit,
    onGoToDashboard: () -> Unit,
    onBack: () -> Unit,
) {
    composable(
        route = IrCodesOverviewScreen.route,
        arguments = listOf(
            navArgument("device-sn") {
                type = NavType.StringType
            },
        )
    ) {
        val deviceSn = it.arguments?.getString("device-sn") ?: ""
        val serialNumber = DeviceSerialNumber.of(deviceSn)
        val vm: IrCodesOverviewVm = hiltViewModel<IrCodesOverviewVm>().apply {
            onCreate(serialNumber)
        }
        IrCodesOverviewRoute(
            deviceSerialNumber = serialNumber,
            onAddNewIrCode = onAddNewIrCode,
            onGoToDashboard = onGoToDashboard,
            onBack = onBack,
            vm = vm,
        )
    }
}