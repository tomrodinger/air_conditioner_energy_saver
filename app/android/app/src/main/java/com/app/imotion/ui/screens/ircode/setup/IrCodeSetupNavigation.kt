package com.app.imotion.ui.screens.ircode.setup

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.app.imotion.extensions.collectWith
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.navigation.NavRoute
import com.app.imotion.ui.screens.devices.DevicesOverviewScreen

/**
 * Created by hani.fakhouri on 2023-06-09.
 */

object IrCodeSetupScreen : NavRoute(route = "ircode/setup/{device-sn}") {
    fun buildRoute(
        deviceSerialNumber: DeviceSerialNumber
    ) = "ircode/setup/${deviceSerialNumber.value}"
}

fun NavController.navigateToIrCodeSetup(
    deviceSerialNumber: DeviceSerialNumber,
    navOptions: NavOptions? = null
) {
    this.navigate(IrCodeSetupScreen.buildRoute(deviceSerialNumber), navOptions)
}

fun NavController.navigateToIrCodeSetupAndPopupToDashBoard(
    deviceSerialNumber: DeviceSerialNumber
) {
    this.navigate(
        route = IrCodeSetupScreen.buildRoute(deviceSerialNumber),
        navOptions = navOptions {
            popUpTo(DevicesOverviewScreen.route)
        }
    )
}

fun NavGraphBuilder.irCodeSetupScreen(
    onGoToDashboard: () -> Unit,
    onBack: () -> Unit,
) {
    composable(
        route = IrCodeSetupScreen.route,
        arguments = listOf(
            navArgument("device-sn") {
                type = NavType.StringType
            },
        )
    ) {
        val deviceSn = it.arguments?.getString("device-sn") ?: ""
        val vm: IrCodeSetupScreenVM = hiltViewModel<IrCodeSetupScreenVM>().apply {
            onCreate(DeviceSerialNumber.of(deviceSn))
        }
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(vm) {
            vm.vmEvents.collectWith(lifecycleOwner) { event ->
                when (event) {
                    is IrCodeSetupScreenVMEvent.OpenDeviceOverviewPage -> onGoToDashboard()
                }
            }
        }
        IrCodeSetupRoute(
            vm = vm,
            onBack = onBack,
        )
    }
}