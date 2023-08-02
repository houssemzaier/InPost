package pl.inpost.recruitmenttask.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import pl.inpost.recruitmenttask.domain.Shipment

interface ShipmentRepository {
    suspend fun getAllShipments()

    val allShipments: StateFlow<List<Shipment>>
}
