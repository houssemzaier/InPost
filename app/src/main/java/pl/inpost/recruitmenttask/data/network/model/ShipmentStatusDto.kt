package pl.inpost.recruitmenttask.data.network.model

import pl.inpost.recruitmenttask.domain.model.ShipmentStatus

/**
 * Order of statuses
 * 1. CREATED
 * 2. CONFIRMED
 * 3. ADOPTED_AT_SOURCE_BRANCH
 * 4. SENT_FROM_SOURCE_BRANCH
 * 5. ADOPTED_AT_SORTING_CENTER
 * 6. SENT_FROM_SORTING_CENTER
 * 7. OTHER
 * 8. DELIVERED
 * 9. RETURNED_TO_SENDER
 * 10. AVIZO
 * 11. OUT_FOR_DELIVERY
 * 12. READY_TO_PICKUP
 * 13. PICKUP_TIME_EXPIRED
 */
enum class ShipmentStatusDto(
    val order: Int
) {
    NOT_READY(0),
    CREATED(1),
    CONFIRMED(2),
    ADOPTED_AT_SOURCE_BRANCH(3),
    SENT_FROM_SOURCE_BRANCH(4),
    ADOPTED_AT_SORTING_CENTER(5),
    SENT_FROM_SORTING_CENTER(6),
    OTHER(7),
    DELIVERED(8),
    RETURNED_TO_SENDER(9),
    AVIZO(10),
    OUT_FOR_DELIVERY(11),
    READY_TO_PICKUP(12),
    PICKUP_TIME_EXPIRED(13),
    ;

    companion object {
        fun ShipmentStatusDto.toDomain(): ShipmentStatus {
            return ShipmentStatus.valueOf(this.name)
        }
    }
}

