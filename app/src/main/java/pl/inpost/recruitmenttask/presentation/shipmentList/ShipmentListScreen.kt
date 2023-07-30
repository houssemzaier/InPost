package pl.inpost.recruitmenttask.presentation.shipmentList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.inpost.recruitmenttask.presentation.components.ShipmentList

@Composable
fun ShipmentListScreen(padding: PaddingValues, shipmentList: List<Section>) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth()
    ) {
        ShipmentList(shipmentList)
    }
}
