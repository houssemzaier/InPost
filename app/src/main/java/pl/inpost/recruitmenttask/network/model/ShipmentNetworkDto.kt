package pl.inpost.recruitmenttask.network.model

import pl.inpost.recruitmenttask.domain.Shipment
import pl.inpost.recruitmenttask.network.model.SenderNetworkDto.Companion.toDomain
import pl.inpost.recruitmenttask.network.model.ShipmentStatusDto.Companion.toDomain
import pl.inpost.recruitmenttask.network.model.ShipmentTypeDto.Companion.toDomain
import java.time.ZonedDateTime

data class ShipmentNetworkDto(
    val number: String,
    val shipmentType: ShipmentTypeDto,
    val status: ShipmentStatusDto,
    val eventLog: List<EventLogNetworkDto>,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiver: CustomerNetworkDto?,
    val sender: SenderNetworkDto?,
    val operations: OperationsNetworkDto,
) {
    companion object {

        fun ShipmentNetworkDto.toDomain(): Shipment = Shipment(
            number = number,
            shipmentType = shipmentType.toDomain(),
            status = status.toDomain(),
            pickUpDate = pickUpDate,
            sender = sender?.toDomain(),
        )
    }
}
