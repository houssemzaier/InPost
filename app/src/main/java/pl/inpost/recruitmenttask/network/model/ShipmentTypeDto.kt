package pl.inpost.recruitmenttask.network.model

import pl.inpost.recruitmenttask.domain.ShipmentType

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
