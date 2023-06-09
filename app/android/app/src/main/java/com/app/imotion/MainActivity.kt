package com.app.imotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.app.imotion.extensions.collectWith
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.navigation.navigateTo
import com.app.imotion.permission.PermissionHandler
import com.app.imotion.permission.PermissionState
import com.app.imotion.settings.AppSettings
import com.app.imotion.ui.screens.PermissionRationaleScreen
import com.app.imotion.ui.screens.add.AddDeviceManuallyScreen
import com.app.imotion.ui.screens.add.AddDeviceManuallyUiEvent
import com.app.imotion.ui.screens.add.AddDeviceManuallyVm
import com.app.imotion.ui.screens.device.DeviceOverviewScreen
import com.app.imotion.ui.screens.device.DeviceOverviewScreenVm
import com.app.imotion.ui.screens.device.DeviceOverviewUiEvent
import com.app.imotion.ui.screens.devices.DevicesOverviewScreen
import com.app.imotion.ui.screens.devices.DevicesOverviewScreenScreen
import com.app.imotion.ui.screens.devices.DevicesOverviewScreenVm
import com.app.imotion.ui.screens.devices.DevicesOverviewUiEvent
import com.app.imotion.ui.screens.ircode.setup.IrCodeSetupScreen
import com.app.imotion.ui.screens.ircode.setup.IrCodeSetupScreenVM
import com.app.imotion.ui.screens.ircode.setup.IrCodeSetupScreenVMEvent
import com.app.imotion.ui.screens.onboarding.OnBoardingScreen
import com.app.imotion.ui.theme.IMotionTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settings: AppSettings

    private lateinit var permissionHandler: PermissionHandler
    private var bleReady by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val settings = AppSettings.getInstance(this.applicationContext)
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
                        AppScreen()
                    }
                }
            }
        }
    }

    @Composable
    private fun AppScreen() {
        val navController = rememberNavController()
        val lifecycleOwner = LocalLifecycleOwner.current

        NavHost(
            navController = navController,
            startDestination = DevicesOverviewScreenScreen.route
        ) {
            composable(DevicesOverviewScreenScreen.route) {
                val vm: DevicesOverviewScreenVm = hiltViewModel()
                LaunchedEffect(vm) {
                    vm.uiEvents.collectWith(lifecycleOwner) {
                        when (it) {
                            DevicesOverviewUiEvent.AddNewDevice ->
                                navController.navigateTo(AddDeviceManuallyScreen,
                                    navOptions {
                                        popUpTo(DevicesOverviewScreenScreen.route)
                                    }
                                )
                            is DevicesOverviewUiEvent.OpenDeviceOverview ->
                                navController.navigate(
                                    route = DeviceOverviewScreen.buildRoute(it.serialNumber),
                                    navOptions = navOptions {
                                        popUpTo(DevicesOverviewScreenScreen.route)
                                    }
                                )
                        }
                    }
                }
                DevicesOverviewScreen(vm)
            }
            composable(AddDeviceManuallyScreen.route) {
                val vm: AddDeviceManuallyVm = hiltViewModel()
                collectAddDeviceManuallyUiEvent(vm, navController, vm.uiEvents)
                AddDeviceManuallyScreen(vm)
            }

            composable(
                route = IrCodeSetupScreen.route,
                arguments = listOf(
                    navArgument("device-sn") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                )
            ) {
                val deviceSn = it.arguments?.getString("device-sn") ?: ""
                val vm: IrCodeSetupScreenVM = hiltViewModel<IrCodeSetupScreenVM>().apply {
                    onCreate(DeviceSerialNumber.of(deviceSn))
                }
                collectIrCodeSetupScreenVMEvent(vm, navController, vm.vmEvents)
                IrCodeSetupScreen(
                    vm = vm,
                    onBack = { navController.popBackStack() }
                )
            }

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
                collectDeviceOverviewScreenUiEvent(vm, navController, vm.uiEvents)
                DeviceOverviewScreen(vm)
            }

        }
    }

    @Composable
    private fun <T> collectDeviceOverviewScreenUiEvent(
        key: T,
        navController: NavHostController,
        events: SharedFlow<DeviceOverviewUiEvent>,
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(key) {
            events.collectWith(lifecycleOwner) {
                when (it) {
                    DeviceOverviewUiEvent.Back -> navController.popBackStack()
                    DeviceOverviewUiEvent.GoToDashboard ->
                        navController.popBackStack(
                            DevicesOverviewScreenScreen.route,
                            inclusive = false
                        )
                    is DeviceOverviewUiEvent.OpenIrCodesPage -> {}
                    is DeviceOverviewUiEvent.OpenTriggerRuleSetupPage -> {}
                }
            }
        }
    }

    @Composable
    private fun <T> collectIrCodeSetupScreenVMEvent(
        key: T,
        navController: NavHostController,
        events: SharedFlow<IrCodeSetupScreenVMEvent>
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(key) {
            events.collectWith(lifecycleOwner) {
                when (it) {
                    is IrCodeSetupScreenVMEvent.OpenDeviceOverviewPage -> {
                        navController.navigate(
                            route = DeviceOverviewScreen.buildRoute(it.deviceSerialNumber),
                            navOptions = navOptions {
                                popUpTo(DevicesOverviewScreenScreen.route)
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun <T> collectAddDeviceManuallyUiEvent(
        key: T,
        navController: NavHostController,
        events: SharedFlow<AddDeviceManuallyUiEvent>,
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(key) {
            events.collectWith(lifecycleOwner) {
                when (it) {
                    is AddDeviceManuallyUiEvent.AddDevice -> {}
                    AddDeviceManuallyUiEvent.GoBack ->
                        navController.popBackStack()
                    AddDeviceManuallyUiEvent.GoToDashboard -> {
                        /*
                        navController.popBackStack(
                            DevicesOverviewScreenScreen.route,
                            inclusive = false
                        )
                        */
                        navController.popBackStack()
                    }
                    AddDeviceManuallyUiEvent.OpenAddAnotherDevice ->
                        navController.navigateTo(
                            navRoute = AddDeviceManuallyScreen,
                            navOptions = navOptions {
                                popUpTo(DevicesOverviewScreenScreen.route)
                            }
                        )
                    is AddDeviceManuallyUiEvent.OpenLearnIrCode ->
                        navController.navigate(
                            route = IrCodeSetupScreen.buildRoute(it.serialNumber),
                            navOptions = navOptions {
                                popUpTo(DevicesOverviewScreenScreen.route)
                            }
                        )
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