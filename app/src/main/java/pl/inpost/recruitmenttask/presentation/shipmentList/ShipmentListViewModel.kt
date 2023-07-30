package pl.inpost.recruitmenttask.presentation.shipmentList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.domain.Shipment
import pl.inpost.recruitmenttask.domain.ShipmentStatus
import pl.inpost.recruitmenttask.domain.ShipmentType
import pl.inpost.recruitmenttask.domain.use_cases.GetAllShipmentsUseCase
import pl.inpost.recruitmenttask.presentation.components.CourierPackageItem
import pl.inpost.recruitmenttask.presentation.components.HeaderItem
import pl.inpost.recruitmenttask.presentation.components.ParcelLockerItem
import pl.inpost.recruitmenttask.presentation.components.ShipmentStatusUiModel.Companion.toUiModel
import pl.inpost.recruitmenttask.utils.CoroutineDispatcherProvider
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val getAllShipmentsUseCase: GetAllShipmentsUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {

    private val _mutableUiState = MutableLiveData(ShipmentListScreenUiState())
    val mutableUiState: LiveData<ShipmentListScreenUiState> = _mutableUiState

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch(coroutineDispatcherProvider.io) {
            _mutableUiState.postValue(_mutableUiState.value?.copy(isRefreshing = true))

            val shipments = getAllShipmentsUseCase()
            val sectionList = createScreenUiModel(shipments)

            _mutableUiState.postValue(
                _mutableUiState.value?.copy(
                    shipmentList = sectionList,
                    isRefreshing = false,
                )
            )
        }
    }

    private fun createScreenUiModel(shipments: List<Shipment>): List<Section> {
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


    private fun ZonedDateTime.formatZonedDateTime(): String {
        val formatter =
            DateTimeFormatter.ofPattern("E'| '|dd.MM.yy'| '|HH:mm", Locale.forLanguageTag("pl-PL"))
        return format(formatter)
    }

}
