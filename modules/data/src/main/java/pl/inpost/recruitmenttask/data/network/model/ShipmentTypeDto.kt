package pl.inpost.recruitmenttask.data.network.model

import pl.inpost.recruitmenttask.domain.model.ShipmentType

enum class ShipmentTypeDto {
    PARCEL_LOCKER,
    COURIER,
    ;

    companion object {
        fun ShipmentTypeDto.toDomain(): ShipmentType {
            return ShipmentType.valueOf(this.name)
        }
    }
}
