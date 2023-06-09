package com.app.imotion.ui.screens.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.imotion.model.DeviceSerialNumber
import com.app.imotion.model.MotionDeviceListEntry
import com.app.imotion.model.storage.DeviceStorageModel
import com.app.imotion.repo.device.DevicesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by hani.fakhouri on 2023-06-08.
 */

data class DevicesOverviewState(
    val loading: Boolean = false,
    val devices: List<MotionDeviceListEntry> = listOf(),
)

sealed interface DevicesOverviewUiEvent {
    object AddNewDevice : DevicesOverviewUiEvent
    data class OpenDeviceOverview(val serialNumber: DeviceSerialNumber) : DevicesOverviewUiEvent
}

@HiltViewModel
class DevicesOverviewScreenVm @Inject constructor(
    private val devicesRepo: DevicesRepo,
) : ViewModel() {

    private val _state = MutableStateFlow(DevicesOverviewState())
    val state: StateFlow<DevicesOverviewState> = _state

    private val _uiEvents = MutableSharedFlow<DevicesOverviewUiEvent>()
    val uiEvents: SharedFlow<DevicesOverviewUiEvent> = _uiEvents

    init {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            devicesRepo.observeDevices().collect { storedDevices ->
                _state.update {
                    it.copy(
                        loading = false,
                        devices = storedDevices.map(::mapStorageModelToViewModel)
                    )
                }
            }
        }
    }

    fun onUiEvent(event: DevicesOverviewUiEvent) {
        viewModelScope.launch {
            _uiEvents.emit(event)
        }
    }

    private fun mapStorageModelToViewModel(
        storageModel: DeviceStorageModel
    ): MotionDeviceListEntry {
        return MotionDeviceListEntry(
            name = storageModel.name,
            area = storageModel.areaName,
            isActive = storageModel.isActive,
            serialNumber = storageModel.serialNumber,
        )
    }

}