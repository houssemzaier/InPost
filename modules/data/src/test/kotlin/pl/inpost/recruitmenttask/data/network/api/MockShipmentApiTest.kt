package pl.inpost.recruitmenttask.`data`.network.api

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import pl.inpost.recruitmenttask.data.network.ApiTypeAdapter


class MockShipmentApiTest {
    private val fakeJsonProvider = mock<FakeJsonProvider>()
    private val apiTypeAdapter = ApiTypeAdapter()

    private lateinit var mockShipmentApi: MockShipmentApi

    @Before
    fun setUp() {
        `when`(fakeJsonProvider.getFakeJson()).thenReturn(JSON_STRING)
        mockShipmentApi = MockShipmentApi(fakeJsonProvider, apiTypeAdapter)
    }

    @Test
    fun `test getShipments returns correct number of shipments`() = runTest {
        val result = mockShipmentApi.getShipments()
        assertEquals(9, result.size)
    }

    @Test
    fun `test getShipments returns highlighted shipments first`() = runTest {
        val result = mockShipmentApi.getShipments()
        val firstShipmentIsHighlighted = result.first().operations.highlight
        assertEquals(true, firstShipmentIsHighlighted)
    }

    private companion object {
        private const val JSON_STRING = """
 {
  "shipments": [
    {
      "number": "16730345345597442248333",
      "expiryDate": "2022-11-29T04:56:07Z",
      "storedDate": "2022-11-29T04:56:07Z",
      "openCode": "987654",
      "shipmentType": "PARCEL_LOCKER",
      "status": "READY_TO_PICKUP",
      "eventLog": [
        {
          "name": "READY_TO_PICKUP",
          "date": "2018-08-01T04:56:07Z"
        },
        {
          "name": "SENT_FROM_SOURCE_BRANCH",
          "date": "2018-08-18T04:56:07Z"
        },
        {
          "name": "CONFIRMED",
          "date": "2018-08-14T04:56:07Z"
        }
      ],
      "sender": {
        "email": "sender@example.com",
        "phoneNumber": "500500500",
        "name": "sender"
      },
      "receiver": {
        "email": "receiver@example.com",
        "phoneNumber": "500500500",
        "name": "IKEA"
      },
      "operations": {
        "delete": true,
        "manualArchive": true,
        "collect": true,
        "highlight": true,
        "expandAvizo": true,
        "endOfWeekCollection": false
      }
    },
    {
      "number": "26730345345597442248333",
      "expiryDate": "2022-11-29T04:56:07Z",
      "storedDate": "2022-11-29T04:56:07Z",
      "openCode": "987654",
      "shipmentType": "PARCEL_LOCKER",
      "status": "CONFIRMED",
      "eventLog": [],
      "sender": {
        "email": "sender@example.com",
        "phoneNumber": "500500500",
        "name": "sender"
      },
      "receiver": {
        "email": "receiver@example.com",
        "phoneNumber": "500500500",
        "name": "x-kom.pl"
      },
      "operations": {
        "delete": true,
        "manualArchive": true,
        "collect": false,
        "highlight": false,
        "expandAvizo": false,
        "endOfWeekCollection": false
      }
    },
    {
      "number": "36730345345597442248333",
      "expiryDate": "2022-11-27T04:56:07Z",
      "storedDate": "2022-11-25T04:56:07Z",
      "openCode": "987654",
      "shipmentType": "PARCEL_LOCKER",
      "status": "READY_TO_PICKUP",
      "eventLog": [],
      "sender": {
        "email": "sender@example.com",
        "phoneNumber": "500500500",
        "name": "sender"
      },
      "receiver": {
        "email": "receiver@example.com",
        "phoneNumber": "500500500",
        "name": "Offy"
      },
      "operations": {
        "delete": false,
        "manualArchive": false,
        "collect": false,
        "highlight": true,
        "expandAvizo": false,
        "endOfWeekCollection": false
      }
    },
    {
      "number": "46730345345597442248333",
      "expiryDate": "2022-12-05T04:56:07Z",
      "storedDate": "2022-11-25T04:56:07Z",
      "openCode": "987654",
      "shipmentType": "COURIER",
      "status": "OUT_FOR_DELIVERY",
      "eventLog": [],
      "sender": {
        "email": "sender@example.com",
        "phoneNumber": "500500500",
        "name": "Biker"
      },
      "operations": {
        "delete": false,
        "manualArchive": true,
        "collect": false,
        "highlight": false,
        "expandAvizo": false,
        "endOfWeekCollection": true
      }
    },
    {
      "number": "56730345345597442248333",
      "expiryDate": "2022-12-05T04:56:07Z",
      "storedDate": "2022-11-25T04:56:07Z",
      "openCode": "987654",
      "shipmentType": "PARCEL_LOCKER",
      "status": "OUT_FOR_DELIVERY",
      "eventLog": [],
      "sender": {
        "email": "sender@example.com",
        "phoneNumber": "500500500",
        "name": "sender"
      },
      "operations": {
        "delete": false,
        "manualArchive": true,
        "collect": false,
        "highlight": false,
        "expandAvizo": false,
        "endOfWeekCollection": true
      }
    },
    {
      "number": "66730345345597442248333",
      "openCode": "987654",
      "shipmentType": "PARCEL_LOCKER",
      "status": "NOT_READY",
      "eventLog": [],
      "sender": {
        "email": "sender@example.com",
        "phoneNumber": "500500500",
        "name": "sender"
      },
      "operations": {
        "delete": false,
        "manualArchive": false,
        "collect": false,
        "highlight": false,
        "expandAvizo": false,
        "endOfWeekCollection": false
      }
    },
    {
      "number": "76730345345597442248333",
      "expiryDate": "2021-12-29T04:56:07Z",
      "storedDate": "2021-07-29T04:56:07Z",
      "openCode": "987654",
      "shipmentType": "PARCEL_LOCKER",
      "status": "SENT_FROM_SOURCE_BRANCH",
      "eventLog": [],
      "sender": {
        "email": "sender@example.com",
        "phoneNumber": "500500500",
        "name": "sender"
      },
      "operations": {
        "delete": false,
        "manualArchive": false,
        "collect": false,
        "highlight": false,
        "expandAvizo": false,
        "endOfWeekCollection": false
      }
    },
    {
      "number": "86730345345597442248333",
      "expiryDate": "2022-11-05T04:56:07Z",
      "storedDate": "2022-11-04T04:56:07Z",
      "openCode": "987654",
      "shipmentType": "PARCEL_LOCKER",
      "status": "AVIZO",
      "eventLog": [],
      "sender": {
        "email": "sender@example.com",
        "phoneNumber": "500500500",
        "name": "sender"
      },
      "operations": {
        "delete": false,
        "manualArchive": false,
        "collect": false,
        "highlight": false,
        "expandAvizo": false,
        "endOfWeekCollection": false
      }
    },
    {
      "number": "96730345345597442248333",
      "pickUpDate": "2022-11-05T04:56:07Z",
      "openCode": "987654",
      "shipmentType": "PARCEL_LOCKER",
      "status": "CONFIRMED",
      "eventLog": [],
      "sender": {
        "email": "sender@example.com",
        "phoneNumber": "500500500",
        "name": "sender"
      },
      "operations": {
        "delete": false,
        "manualArchive": true,
        "collect": false,
        "highlight": false,
        "expandAvizo": false,
        "endOfWeekCollection": false
      }
    }
  ]
}

"""
    }
}
