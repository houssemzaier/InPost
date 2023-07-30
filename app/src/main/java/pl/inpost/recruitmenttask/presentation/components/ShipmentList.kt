package pl.inpost.recruitmenttask.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.inpost.recruitmenttask.presentation.shipmentList.Section

@Composable
fun ShipmentList(list: List<Section>) {
    LazyColumn(modifier = Modifier.background(Color(0xFFF2F2F2)))
    {
        items(list) {
            when (it) {
                is Section.SectionHeader -> {
                    HeaderItem.Divider(it.model)
                }

                is Section.SectionItemCourier -> {
                    CourierPackageItem.Card(it.model)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                is Section.SectionItemParcelLocker -> {
                    ParcelLockerItem.Card(it.model)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
