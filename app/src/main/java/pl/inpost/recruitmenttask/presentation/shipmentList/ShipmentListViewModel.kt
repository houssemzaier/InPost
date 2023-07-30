package pl.inpost.recruitmenttask.presentation.shipmentList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.network.api.ShipmentApi
import pl.inpost.recruitmenttask.network.model.ShipmentNetworkDto
import pl.inpost.recruitmenttask.network.model.ShipmentStatusDto
import pl.inpost.recruitmenttask.network.model.ShipmentTypeDto
import pl.inpost.recruitmenttask.presentation.components.CourierPackageItem
import pl.inpost.recruitmenttask.presentation.components.HeaderItem
import pl.inpost.recruitmenttask.presentation.components.ParcelLockerItem
import pl.inpost.recruitmenttask.presentation.components.ShipmentStatusUiModel.Companion.toUiModel
import pl.inpost.recruitmenttask.util.setState
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentApi: ShipmentApi
) : ViewModel() {

    private val mutableViewState = MutableLiveData<List<Section>>(emptyList())
    val viewState: LiveData<List<Section>> = mutableViewState

    init {
        refreshData()
    }

    private fun refreshData() {
        GlobalScope.launch(Dispatchers.Main) {
            val shipments = shipmentApi.getShipments()
            val map = shipments.groupBy { it.status }
            val sectionList = buildList {
                map.forEach { (status: ShipmentStatusDto, list: List<ShipmentNetworkDto>) ->
                    println("$status = ${list.size}")
                    add(Section.SectionHeader(HeaderItem.Model(status.toUiModel())))
                    list.map {
                        when (it.shipmentType) {
                            ShipmentTypeDto.PARCEL_LOCKER -> {
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

                            ShipmentTypeDto.COURIER -> {
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

            mutableViewState.setState { sectionList }
        }
    }

    private fun ZonedDateTime.formatZonedDateTime(): String {
        val formatter =
            DateTimeFormatter.ofPattern("E'| '|dd.MM.yy'| '|HH:mm", Locale.forLanguageTag("pl-PL"))
        return format(formatter)
    }

}
