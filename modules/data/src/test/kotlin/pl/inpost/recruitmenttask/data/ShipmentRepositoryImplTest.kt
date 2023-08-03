package pl.inpost.recruitmenttask.data

import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifyOrder
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import pl.inpost.recruitmenttask.data.cache.dao.ShipmentDao
import pl.inpost.recruitmenttask.data.cache.model.ShipmentTable
import pl.inpost.recruitmenttask.data.cache.model.ShipmentTable.Companion.toShipment
import pl.inpost.recruitmenttask.data.cache.model.ShipmentTable.Companion.toShipmentTable
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.network.model.CustomerNetworkDto
import pl.inpost.recruitmenttask.data.network.model.EventLogNetworkDto
import pl.inpost.recruitmenttask.data.network.model.OperationsNetworkDto
import pl.inpost.recruitmenttask.data.network.model.SenderNetworkDto
import pl.inpost.recruitmenttask.data.network.model.ShipmentNetworkDto
import pl.inpost.recruitmenttask.data.network.model.ShipmentNetworkDto.Companion.toDomain
import pl.inpost.recruitmenttask.data.network.model.ShipmentStatusDto
import pl.inpost.recruitmenttask.data.network.model.ShipmentTypeDto
import java.time.ZonedDateTime

class ShipmentRepositoryImplTest {

    // Mocks
    private val shipmentApi: ShipmentApi = mockk()
    private val shipmentDao: ShipmentDao = mockk()

    private lateinit var repository: ShipmentRepositoryImpl

    @Before
    fun setUp() {
        repository = ShipmentRepositoryImpl(shipmentApi, shipmentDao)
    }

    @Test
    fun `getAllShipments retrieves data from DAO and updates it from API`() = runTest {
        val fakeStoredList = listOf(
            ShipmentTable(
                number = "number",
                shipmentType = "PARCEL_LOCKER",
                status = "CREATED",
                pickUpDate = "2023-08-04T10:15:30+01:00",
                senderName = "senderName",
                senderEmail = "senderEmail",
            )
        )
        val fakeNetworkDtoList = listOf(
            ShipmentNetworkDto(
                number = "123456",
                shipmentType = ShipmentTypeDto.PARCEL_LOCKER,
                status = ShipmentStatusDto.DELIVERED,

                eventLog = listOf(
                    EventLogNetworkDto("Event1", ZonedDateTime.parse("2023-08-02T10:15:30+01:00")),
                    EventLogNetworkDto("Event2", ZonedDateTime.parse("2023-08-03T10:15:30+01:00"))
                ),

                openCode = "OPEN123",
                expiryDate = ZonedDateTime.parse("2023-08-10T10:15:30+01:00"),
                storedDate = ZonedDateTime.parse("2023-08-01T10:15:30+01:00"),
                pickUpDate = ZonedDateTime.parse("2023-08-04T10:15:30+01:00"),
                receiver = CustomerNetworkDto("John Doe", "john@example.com", "123456789"),
                sender = SenderNetworkDto("Jane Doe", "jane@example.com", "987654321"),
                operations = OperationsNetworkDto(
                    manualArchive = true,
                    delete = true,
                    collect = true,
                    highlight = true,
                    expandAvizo = true,
                    endOfWeekCollection = true,
                )
            ),
        )

        coEvery { shipmentDao.getAll() } returns fakeStoredList
        coEvery { shipmentApi.getShipments() } returns fakeNetworkDtoList
        coJustRun { shipmentDao.deleteAll() }
        coJustRun { shipmentDao.insert(any()) }

        repository.getAllShipments()

        val resultList = repository.allShipments.first()
        assertEquals(fakeNetworkDtoList.map { it.toDomain() }, resultList)

        coVerifySequence {
            shipmentDao.getAll()
            shipmentApi.getShipments()
            shipmentDao.deleteAll()
            shipmentDao.insert(fakeNetworkDtoList.map { it.toDomain() }
                .map { it.toShipmentTable() })
        }
    }

    @Test
    fun `delete method removes shipment and updates list`() = runTest {
        val shipmentId = "12345"
        val fakeStoredList = listOf(
            ShipmentTable(
                number = "number",
                shipmentType = "PARCEL_LOCKER",
                status = "CREATED",
                pickUpDate = "2023-08-04T10:15:30+01:00",
                senderName = "senderName",
                senderEmail = "senderEmail",
            )
        )

        coJustRun { shipmentDao.deleteBy(shipmentId) }
        coEvery { shipmentDao.getAll() } returns fakeStoredList

        repository.delete(shipmentId)

        val resultList = repository.allShipments.first()
        assertEquals(fakeStoredList.map { it.toShipment() }, resultList)

        coVerifyOrder {
            shipmentDao.deleteBy(shipmentId)
            shipmentDao.getAll()
        }
    }
}
