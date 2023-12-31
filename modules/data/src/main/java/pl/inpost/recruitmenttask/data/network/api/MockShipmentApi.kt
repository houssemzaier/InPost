package pl.inpost.recruitmenttask.data.network.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay
import pl.inpost.recruitmenttask.data.network.ApiTypeAdapter
import pl.inpost.recruitmenttask.data.network.model.ShipmentNetworkDto
import pl.inpost.recruitmenttask.data.network.model.ShipmentsResponseDto
import javax.inject.Inject

class MockShipmentApi @Inject constructor(
    fakeJsonProvider: FakeJsonProvider,
    apiTypeAdapter: ApiTypeAdapter
) : ShipmentApi {

    private val response by lazy {
        val json = fakeJsonProvider.getFakeJson()

        val jsonAdapter = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(apiTypeAdapter)
            .build()
            .adapter(ShipmentsResponseDto::class.java)

        jsonAdapter.fromJson(json) as ShipmentsResponseDto
    }

    override suspend fun getShipments(): List<ShipmentNetworkDto> {
        delay(1_500) //to fake loading

        val highlightedShipments = response.shipments.filter { it.operations.highlight }
        val nonHighlightedShipments = response.shipments.filterNot { it.operations.highlight }

        val sortedHighlighted = highlightedShipments.sortedWith(compareBy(
            { it.status.order },
            { it.storedDate },
            { it.pickUpDate },
            { it.expiryDate }
        ))

        val sortedNonHighlighted = nonHighlightedShipments.sortedWith(compareBy(
            { it.status.order },
            { it.storedDate },
            { it.pickUpDate },
            { it.expiryDate }
        ))

        return sortedHighlighted + sortedNonHighlighted
    }
}
