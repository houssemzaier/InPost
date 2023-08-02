package pl.inpost.recruitmenttask.data

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.inpost.recruitmenttask.data.cache.dao.ShipmentDao
import pl.inpost.recruitmenttask.data.cache.model.ShipmentTable.Companion.toShipment
import pl.inpost.recruitmenttask.data.cache.model.ShipmentTable.Companion.toShipmentTable
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.network.model.ShipmentNetworkDto.Companion.toDomain
import pl.inpost.recruitmenttask.domain.model.Shipment
import pl.inpost.recruitmenttask.domain.repositories.ShipmentRepository
import javax.inject.Inject

class ShipmentRepositoryImpl @Inject constructor(
    private val shipmentApi: ShipmentApi,
    private val shipmentDao: ShipmentDao,
) : ShipmentRepository {

    private val _allShipments = MutableStateFlow(emptyList<Shipment>())
    override val allShipments: StateFlow<List<Shipment>> = _allShipments.asStateFlow()

    override suspend fun getAllShipments() {
        val storedList = shipmentDao.getAll()
        _allShipments.value = storedList.map { it.toShipment() }
        try {
            val networkList = shipmentApi.getShipments().map { it.toDomain() }
            if (networkList.isNotEmpty()) {
                shipmentDao.deleteAll()
                shipmentDao.insert(networkList.map { it.toShipmentTable() })
            }
            _allShipments.value = networkList
        } catch (e: Exception) {
            Log.e("ShipmentRepositoryImpl", "getAllShipments: ${e.message}")
        }
    }

    override suspend fun delete(shipmentId: String) {
        shipmentDao.deleteBy(shipmentId)
        val storedList = shipmentDao.getAll()
        _allShipments.value = storedList.map { it.toShipment() }
    }
}
