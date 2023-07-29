package pl.inpost.recruitmenttask.network.api

import pl.inpost.recruitmenttask.network.model.ShipmentNetworkDto

interface ShipmentApi {
    suspend fun getShipments(): List<ShipmentNetworkDto>
}
