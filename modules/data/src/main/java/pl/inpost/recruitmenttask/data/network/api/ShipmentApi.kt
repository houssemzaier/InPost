package pl.inpost.recruitmenttask.data.network.api

import pl.inpost.recruitmenttask.data.network.model.ShipmentNetworkDto

interface ShipmentApi {
    suspend fun getShipments(): List<ShipmentNetworkDto>
}
