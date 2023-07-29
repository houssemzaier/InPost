package pl.inpost.recruitmenttask.network.model

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
)
