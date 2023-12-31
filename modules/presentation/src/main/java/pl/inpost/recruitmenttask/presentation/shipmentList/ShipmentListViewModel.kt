package pl.inpost.recruitmenttask.presentation.shipmentList

import androidx.annotation.OpenForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.domain.model.Shipment
import pl.inpost.recruitmenttask.domain.model.ShipmentStatus
import pl.inpost.recruitmenttask.domain.model.ShipmentType
import pl.inpost.recruitmenttask.domain.use_cases.DeleteShipmentsUseCase
import pl.inpost.recruitmenttask.domain.use_cases.GetAllShipmentsUseCase
import pl.inpost.recruitmenttask.presentation.components.CourierPackageItem
import pl.inpost.recruitmenttask.presentation.components.HeaderItem
import pl.inpost.recruitmenttask.presentation.components.ParcelLockerItem
import pl.inpost.recruitmenttask.presentation.components.ShipmentStatusUiModel.Companion.toUiModel
import pl.inpost.recruitmenttask.presentation.shipmentList.ZonedDateTimerFormatter.formatZonedDateTime
import pl.inpost.recruitmenttask.utils.CoroutineDispatcherProvider
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val getAllShipmentsUseCase: GetAllShipmentsUseCase,
    private val deleteShipmentsUseCase: DeleteShipmentsUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {

    private val _mutableUiState = MutableLiveData(ShipmentListScreenUiState())
    val mutableUiState: LiveData<ShipmentListScreenUiState> = _mutableUiState

    init {
        getAllShipmentsUseCase.allShipments.onEach { shipments ->
            val sectionList = createScreenUiModel(shipments)

            _mutableUiState.postValue(
                _mutableUiState.value?.copy(
                    shipmentList = sectionList,
                    isRefreshing = false,
                )
            )
        }.launchIn(viewModelScope)
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            _mutableUiState.postValue(_mutableUiState.value?.copy(isRefreshing = true))
            getAllShipmentsUseCase()
        }
    }

    @OpenForTesting
    fun createScreenUiModel(shipments: List<Shipment>): List<Section> {
        val map = shipments.groupBy { it.status }
        val sectionList = buildList {
            map.forEach { (status: ShipmentStatus, list: List<Shipment>) ->
                println("$status = ${list.size}")
                add(Section.SectionHeader(HeaderItem.Model(status.toUiModel())))
                list.map {
                    when (it.shipmentType) {
                        ShipmentType.PARCEL_LOCKER -> {
                            Section.SectionItemParcelLocker(
                                ParcelLockerItem.Model(
                                    it.number,
                                    status.toUiModel(),
                                    senderEmail = it.sender?.email,
                                    senderName = it.sender?.name.orEmpty(),
                                    pickUpTime = it.pickUpDate?.formatZonedDateTime().orEmpty(),
                                )
                            )
                        }

                        ShipmentType.COURIER -> {
                            Section.SectionItemCourier(
                                CourierPackageItem.Model(
                                    it.number,
                                    status.toUiModel(),
                                    senderEmail = it.sender?.email,
                                    senderName = it.sender?.name.orEmpty(),
                                )
                            )
                        }
                    }
                }.let { addAll(it) }
            }
        }
        return sectionList
    }

    fun deleteShipment(section: Section.HasId) {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            _mutableUiState.postValue(_mutableUiState.value?.copy(isRefreshing = true))
            deleteShipmentsUseCase(section.id)
        }
    }
}
