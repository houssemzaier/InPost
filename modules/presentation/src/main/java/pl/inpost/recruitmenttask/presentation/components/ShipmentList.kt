package pl.inpost.recruitmenttask.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import pl.inpost.recruitmenttask.presentation.shipmentList.Section

@Composable
fun ShipmentList(list: List<Section>, onDelete: (Section.HasId) -> Unit) {
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.background(Color(0xFFF2F2F2)))
    {
        items(list) { section ->
            when (section) {
                is Section.SectionHeader -> {
                    HeaderItem.Divider(section.model)
                }

                is Section.SectionItemCourier -> {
                    CourierPackageItem.Card(section.model, onDeleteCard = {
                        onDelete(section)
                        Toast.makeText(
                            context,
                            "Deleted ${section.model.number}",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                    Spacer(modifier = Modifier.height(8.dp))
                }

                is Section.SectionItemParcelLocker -> {
                    ParcelLockerItem.Card(section.model, onDeleteCard = {
                        onDelete(section)
                        Toast.makeText(
                            context,
                            "Deleted ${section.model.number}",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
