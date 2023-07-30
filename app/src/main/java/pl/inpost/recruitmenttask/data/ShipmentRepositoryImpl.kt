package pl.inpost.recruitmenttask.data

import pl.inpost.recruitmenttask.domain.Shipment
import pl.inpost.recruitmenttask.domain.repositories.ShipmentRepository
import pl.inpost.recruitmenttask.network.api.ShipmentApi
import pl.inpost.recruitmenttask.network.model.ShipmentNetworkDto.Companion.toDomain
import javax.inject.Inject

class ShipmentRepositoryImpl @Inject constructor(
    private val shipmentApi: ShipmentApi,
) : ShipmentRepository {
    override suspend fun getAllShipments(): List<Shipment> {
        return shipmentApi.getShipments().map {
            it.toDomain()
        }
    }
}
