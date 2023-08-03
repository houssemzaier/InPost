package pl.inpost.recruitmenttask.presentation.shipmentList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import pl.inpost.recruitmenttask.domain.model.Sender
import pl.inpost.recruitmenttask.domain.model.Shipment
import pl.inpost.recruitmenttask.domain.model.ShipmentStatus
import pl.inpost.recruitmenttask.domain.model.ShipmentType
import pl.inpost.recruitmenttask.domain.use_cases.DeleteShipmentsUseCase
import pl.inpost.recruitmenttask.domain.use_cases.GetAllShipmentsUseCase
import pl.inpost.recruitmenttask.presentation.components.CourierPackageItem
import pl.inpost.recruitmenttask.presentation.components.HeaderItem
import pl.inpost.recruitmenttask.presentation.components.ParcelLockerItem
import pl.inpost.recruitmenttask.presentation.components.ShipmentStatusUiModel
import pl.inpost.recruitmenttask.utils.CoroutineDispatcherProvider
import java.time.ZoneId
import java.time.ZonedDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class ShipmentListViewModelTest {

    private lateinit var getAllShipmentsUseCase: GetAllShipmentsUseCase
    private lateinit var deleteShipmentsUseCase: DeleteShipmentsUseCase
    private lateinit var coroutineDispatcherProvider: CoroutineDispatcherProvider

    private lateinit var viewModel: ShipmentListViewModel

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        getAllShipmentsUseCase = mockk(relaxed = true)
        deleteShipmentsUseCase = mockk(relaxed = true)
        coroutineDispatcherProvider = mockk {
            every { io } returns Dispatchers.Unconfined
        }
        viewModel = ShipmentListViewModel(
            getAllShipmentsUseCase,
            deleteShipmentsUseCase,
            coroutineDispatcherProvider
        )
    }


    @Test
    fun `init should invoke getAllShipmentsUseCase`() = runTest {
        val shipments = listOf<Shipment>()
        every { getAllShipmentsUseCase.allShipments } returns MutableStateFlow(shipments)

        viewModel.refreshData()

        verify { getAllShipmentsUseCase.allShipments }
    }

    @Test
    fun `refreshData should post new state with isRefreshing set to true`() = runTest {
        viewModel.refreshData()

        val value = viewModel.mutableUiState.value
        assertEquals(true, value?.isRefreshing)
    }

    @Test
    fun `deleteShipment should post new state with isRefreshing set to true and invoke deleteShipmentsUseCase`() =
        runTest {
            val section: Section.HasId = Section.SectionItemCourier(
                model = CourierPackageItem.Model(
                    number = "number",
                    status = ShipmentStatusUiModel.DELIVERED,
                    senderEmail = "senderEmail",
                    senderName = "senderName",
                )
            )

            viewModel.deleteShipment(section)

            val value = viewModel.mutableUiState.value
            assertEquals(true, value?.isRefreshing)
            coVerify { deleteShipmentsUseCase.invoke("number") }
        }

    @Test
    fun `createScreenUiModel should group shipments by status`() {
        // Given
        val fakeShipments = listOf(
            Shipment(
                number = "12345",
                shipmentType = ShipmentType.PARCEL_LOCKER,
                status = ShipmentStatus.DELIVERED,
                pickUpDate = ZonedDateTime.of(2023, 8, 1, 12, 0, 0, 0, ZoneId.systemDefault()),
                sender = Sender(email = "alice@example.com", name = "Alice", phoneNumber = "111")
            ),
            Shipment(
                number = "67890",
                shipmentType = ShipmentType.COURIER,
                status = ShipmentStatus.OUT_FOR_DELIVERY,
                pickUpDate = null,
                sender = Sender(email = "bob@example.com", name = "Bob", phoneNumber = "2222")
            ),
            Shipment(
                number = "11223",
                shipmentType = ShipmentType.PARCEL_LOCKER,
                status = ShipmentStatus.OUT_FOR_DELIVERY,
                pickUpDate = ZonedDateTime.of(2023, 8, 11, 12, 0, 0, 0, ZoneId.systemDefault()),
                sender = Sender(email = "carol@example.com", name = "Carol", phoneNumber = "11")
            ),
            Shipment(
                number = "44556",
                shipmentType = ShipmentType.COURIER,
                status = ShipmentStatus.ADOPTED_AT_SORTING_CENTER,
                pickUpDate = ZonedDateTime.of(2023, 8, 12, 11, 10, 0, 0, ZoneId.systemDefault()),
                sender = null
            ),
            Shipment(
                number = "77889",
                shipmentType = ShipmentType.PARCEL_LOCKER,
                status = ShipmentStatus.DELIVERED,
                pickUpDate = ZonedDateTime.of(2023, 8, 1, 13, 0, 0, 0, ZoneId.systemDefault()),
                sender = Sender(email = "dave@example.com", name = "Dave", phoneNumber = "22")
            )
        )

        // When
        val uiModel = viewModel.createScreenUiModel(fakeShipments)

        val expectedList = listOf(
            Section.SectionHeader(
                model = HeaderItem.Model(status = ShipmentStatusUiModel.DELIVERED)
            ),
            Section.SectionItemParcelLocker(
                model = ParcelLockerItem.Model(
                    number = "12345",
                    status = ShipmentStatusUiModel.DELIVERED,
                    senderName = "Alice",
                    senderEmail = "alice@example.com",
                    pickUpTime = "wt.| |01.08.23| |12:00"
                )
            ),
            Section.SectionItemParcelLocker(
                model = ParcelLockerItem.Model(
                    number = "77889",
                    status = ShipmentStatusUiModel.DELIVERED,
                    senderName = "Dave",
                    senderEmail = "dave@example.com",
                    pickUpTime = "wt.| |01.08.23| |13:00"
                )
            ),
            Section.SectionHeader(
                model = HeaderItem.Model(status = ShipmentStatusUiModel.OUT_FOR_DELIVERY)
            ),
            Section.SectionItemCourier(
                model = CourierPackageItem.Model(
                    number = "67890",
                    status = ShipmentStatusUiModel.OUT_FOR_DELIVERY,
                    senderName = "Bob",
                    senderEmail = "bob@example.com"
                )
            ),
            Section.SectionItemParcelLocker(
                model = ParcelLockerItem.Model(
                    number = "11223",
                    status = ShipmentStatusUiModel.OUT_FOR_DELIVERY,
                    senderName = "Carol",
                    senderEmail = "carol@example.com",
                    pickUpTime = "pt.| |11.08.23| |12:00"
                )
            ),
            Section.SectionHeader(
                model = HeaderItem.Model(status = ShipmentStatusUiModel.ADOPTED_AT_SORTING_CENTER)
            ),
            Section.SectionItemCourier(
                model = CourierPackageItem.Model(
                    number = "44556",
                    status = ShipmentStatusUiModel.ADOPTED_AT_SORTING_CENTER,
                    senderName = "",
                    senderEmail = null
                )
            )
        )
        // Then
        assertEquals(expectedList, uiModel)
    }
}
