package pl.inpost.recruitmenttask.presentation.shipmentList

import pl.inpost.recruitmenttask.presentation.components.CourierPackageItem
import pl.inpost.recruitmenttask.presentation.components.HeaderItem
import pl.inpost.recruitmenttask.presentation.components.ParcelLockerItem

sealed interface Section {
    sealed class HasId(
        val id: String
    )

    data class SectionHeader(val model: HeaderItem.Model) : Section
    data class SectionItemCourier(val model: CourierPackageItem.Model) : Section,
        HasId(model.number)

    data class SectionItemParcelLocker(val model: ParcelLockerItem.Model) : Section,
        HasId(model.number)
}

