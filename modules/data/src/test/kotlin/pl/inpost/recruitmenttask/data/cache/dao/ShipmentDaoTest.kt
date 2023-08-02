package pl.inpost.recruitmenttask.data.cache.dao


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import org.junit.runner.RunWith
import pl.inpost.recruitmenttask.data.cache.LocalRoomDatabase
import pl.inpost.recruitmenttask.data.cache.model.ShipmentTable


@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)

class ShipmentDaoTest {

    private lateinit var database: LocalRoomDatabase
    private lateinit var dao: ShipmentDao
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Set the main dispatcher to testDispatcher for testing
        Dispatchers.setMain(testDispatcher)

        // Initialize in-memory Room database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalRoomDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.shipmentDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndRetrieve() = runTest {
        val shipment = ShipmentTable(
            number = "dummyNumber",
            shipmentType = "dummyShipmentType",
            status = "dummyStatus",
            pickUpDate = "dummyPickUpDate",
            senderName = "dummySenderName",
            senderEmail = "dummySenderEmail"
        )
        dao.insert(listOf(shipment))

        val retrievedShipment = dao.getBy("dummyNumber").first()
        assertEquals(shipment, retrievedShipment)
    }

    @Test
    fun testDeleteAll() = runTest {
        val shipment1 = ShipmentTable(
            number = "dummyNumber_1",
            shipmentType = "dummyShipmentType_1",
            status = "dummyStatus_1",
            pickUpDate = "dummyPickUpDate_1",
            senderName = "dummySenderName_1",
            senderEmail = "dummySenderEmail_1"
        )
        val shipment2 = ShipmentTable(
            number = "dummyNumber_2",
            shipmentType = "dummyShipmentType_2",
            status = "dummyStatus_2",
            pickUpDate = "dummyPickUpDate_2",
            senderName = "dummySenderName_2",
            senderEmail = "dummySenderEmail_2"
        )
        dao.insert(listOf(shipment1))
        dao.insert(listOf(shipment2))

        assertTrue(dao.all.first().size == 2)

        dao.deleteAll()

        val allShipments = dao.all.first()
        assertTrue(allShipments.isEmpty())
    }
}
