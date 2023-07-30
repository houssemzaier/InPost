package pl.inpost.recruitmenttask.data.network.api

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.data.network.ApiTypeAdapter
import pl.inpost.recruitmenttask.data.network.model.ShipmentNetworkDto
import pl.inpost.recruitmenttask.data.network.model.ShipmentsResponseDto
import javax.inject.Inject

class MockShipmentApi @Inject constructor(
    @ApplicationContext private val context: Context,
    apiTypeAdapter: ApiTypeAdapter
) : ShipmentApi {

    private val response by lazy {
        val json = context.resources.openRawResource(R.raw.mock_shipment_api_response)
            .bufferedReader()
            .use { it.readText() }

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
