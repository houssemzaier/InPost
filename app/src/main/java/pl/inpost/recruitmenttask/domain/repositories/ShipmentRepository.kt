package pl.inpost.recruitmenttask.domain.repositories

import pl.inpost.recruitmenttask.domain.Shipment

interface ShipmentRepository {
    suspend fun getAllShipments(): List<Shipment>
}
