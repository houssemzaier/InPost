package pl.inpost.recruitmenttask.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import pl.inpost.recruitmenttask.domain.model.Shipment

interface ShipmentRepository {
    suspend fun getAllShipments()
    suspend fun delete(shipmentId: String)

    val allShipments: StateFlow<List<Shipment>>
}
