package pl.inpost.recruitmenttask.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.inpost.recruitmenttask.domain.model.Sender
import pl.inpost.recruitmenttask.domain.model.Shipment
import pl.inpost.recruitmenttask.domain.model.ShipmentStatus
import pl.inpost.recruitmenttask.domain.model.ShipmentType
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "shipment_table")
data class ShipmentTable(
    @PrimaryKey
    val number: String,
    val shipmentType: String,
    val status: String,
    val pickUpDate: String?,
    val senderName: String?,
    val senderEmail: String?,
) {
    companion object {

        fun Shipment.toShipmentTable(): ShipmentTable {
            return ShipmentTable(
                number = this.number,
                shipmentType = this.shipmentType.name,
                status = this.status.name,
                pickUpDate = this.pickUpDate?.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
                senderName = this.sender?.name,
                senderEmail = this.sender?.email,
            )
        }

        fun ShipmentTable.toShipment(): Shipment {
            return Shipment(
                number = this.number,
                shipmentType = ShipmentType.valueOf(this.shipmentType),
                status = ShipmentStatus.valueOf(this.status),
                pickUpDate = if (this.pickUpDate != null) {
                    ZonedDateTime.parse(this.pickUpDate, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                } else {
                    null
                },
                sender = Sender(
                    email = this.senderEmail,
                    name = this.senderName,
                    phoneNumber = null,
                )
            )
        }
    }
}
