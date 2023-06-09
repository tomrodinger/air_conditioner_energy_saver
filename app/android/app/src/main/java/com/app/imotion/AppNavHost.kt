package com.app.imotion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.imotion.navigation.NavRoute
import com.app.imotion.ui.screens.add.AddDeviceManuallyUiEvent
import com.app.imotion.ui.screens.add.addDeviceManuallyScreen
import com.app.imotion.ui.screens.add.navigateToAddDeviceManually
import com.app.imotion.ui.screens.add.navigateToAddDeviceManuallyAndPopupToDashBoard
import com.app.imotion.ui.screens.device.deviceOverviewScreen
import com.app.imotion.ui.screens.device.navigateToDeviceOverview
import com.app.imotion.ui.screens.devices.devicesOverviewScreen
import com.app.imotion.ui.screens.devices.popToDevicesOverview
import com.app.imotion.ui.screens.ircode.setup.irCodeSetupScreen
import com.app.imotion.ui.screens.ircode.setup.navigateToIrCodeSetup
import com.app.imotion.ui.screens.ircode.setup.navigateToIrCodeSetupAndPopupToDashBoard

/**
 * Created by hani.fakhouri on 2023-06-09.
 */

@Composable
fun AppNavHost(
    navController: NavHostController,
    startRoute: NavRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startRoute.route
    ) {

        devicesOverviewScreen(
            onAddNewDevice = navController::navigateToAddDeviceManually,
            onOpenDeviceOverview = navController::navigateToDeviceOverview
        )

        deviceOverviewScreen(
            onBack = navController::popBackStack,
            onGoToDashboard = navController::popToDevicesOverview,
            onOpenIrCodesPage = {

            },
            onOpenTriggerRuleSetupPage = {

            },
            onOpenLearnIrCode = navController::navigateToIrCodeSetup
        )

        addDeviceManuallyScreen {
            when (it) {
                AddDeviceManuallyUiEvent.GoBack ->
                    navController.popBackStack()
                AddDeviceManuallyUiEvent.GoToDashboard ->
                    navController.popToDevicesOverview()
                AddDeviceManuallyUiEvent.OpenAddAnotherDevice ->
                    navController.navigateToAddDeviceManuallyAndPopupToDashBoard()
                is AddDeviceManuallyUiEvent.OpenLearnIrCode ->
                    navController.navigateToIrCodeSetupAndPopupToDashBoard(it.serialNumber)
            }
        }

        irCodeSetupScreen(
            onGoToDashboard = navController::popToDevicesOverview,
            onBack = navController::popBackStack
        )
    }
}