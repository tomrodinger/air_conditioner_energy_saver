package com.app.imotion.permission

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Class that is responsible for:
 * - Checking and requesting bluetooth permission. In case permission is not granted this class
 *   will take care of showing bluetooth permission dialog.
 * - Checking and enabling bluetooth state. In case bluetooth is turned off this class will take
 *   care of showing dialog to turn bluetooth on.
 *
 * Created by hani.fakhouri on 2023-05-29.
 */
class PermissionHandler(
    private val activity: ComponentActivity
) {

    private val _state = MutableStateFlow(PermissionState())
    val state: StateFlow<PermissionState> = _state

    private val bluetoothAdapter by lazy {
        activity.getSystemService(BluetoothManager::class.java).adapter
    }

    /**
     * Method that will do a permission check. In case permission is needed the _state variable will
     * be updated. In case permission is not granted or if bluetooth is disabled then this method
     * will also ask for permission and enable bluetooth.
     */
    fun performBLEPermissionCheck() {
        val hasPermission = isBluetoothPermissionGranted(activity)
        val rationaleMessage = if (!hasPermission) getRationaleMessage() else null
        val isEnabled = bluetoothAdapter.isEnabled
        val showPermissionRational = !hasPermission && !rationaleMessage.isNullOrEmpty()

        _state.value = _state.value.copy(
            hasPermission = hasPermission,
            isEnabled = isEnabled,
            rationaleMessage = rationaleMessage,
            showPermissionRationalScreen = showPermissionRational,
        )

        when {
            !isEnabled -> turnOnBluetooth()
            !hasPermission && rationaleMessage.isNullOrEmpty() -> requestBluetoothPermission()
        }
    }

    fun setPerformCheckOnResume(perform: Boolean) {
        _state.update { it.copy(performCheckOnResume = perform) }
    }

    private fun getRationaleMessage(): String? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S &&
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity, Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            return "In order to connect with BlueTooth the app needs to access device's location. Please open app Settings and grant that permission"
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.BLUETOOTH_SCAN
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.BLUETOOTH_CONNECT
                )
            ) {
                return "In order to connect with BlueTooth the app needs to scan for surrounding devices. Please open app Settings and grant that permission"
            }
        }
        return null
    }

    private fun turnOnBluetooth() {
        enableBluetoothLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
    }

    private fun
            requestBluetoothPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            permissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH
                )
            )
        } else {
            permissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN
                )
            )
        }
    }

    private fun isBluetoothPermissionGranted(context: Context): Boolean {
        val location = hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return location
        }
        val scan = hasPermission(context, Manifest.permission.BLUETOOTH_SCAN)
        val connect = hasPermission(context, Manifest.permission.BLUETOOTH_CONNECT)
        return scan && connect
    }

    private fun hasPermission(context: Context, permission: String): Boolean {
        return PermissionChecker.checkSelfPermission(context, permission) ==
                PermissionChecker.PERMISSION_GRANTED
    }

    // Launcher that will launch system dialog for bluetooth permissions
    private val permissionsLauncher: ActivityResultLauncher<Array<String>> =
        activity.registerForActivityResult(
            ActivityResultContracts
                .RequestMultiplePermissions()
        ) {
        }

    // Launcher that will launch system dialog for enabling bluetooth
    private val enableBluetoothLauncher: ActivityResultLauncher<Intent> =
        activity.registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) {
        }

}