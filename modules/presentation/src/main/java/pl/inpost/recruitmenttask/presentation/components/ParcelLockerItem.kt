package pl.inpost.recruitmenttask.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.inpost.recruitmenttask.presentation.R
import pl.inpost.recruitmenttask.presentation.components.utils.supportSwipingDelete
import java.util.Locale

object ParcelLockerItem {

    @Composable
    fun Card(model: Model, onDeleteCard: (model: Model) -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .supportSwipingDelete {
                    onDeleteCard(model)
                }
                .background(Color.White)
                .padding(16.dp),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(Modifier.padding(bottom = 8.dp)) {
                    Text(
                        text = stringResource(R.string.shipmentList_label_parcel_number).uppercase(
                            Locale.ROOT
                        ),
                        color = Color.Gray,
                    )
                    Text(
                        text = model.number,//"6567898654334567896543"
                        color = Color.DarkGray,
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_courier),
                    contentDescription = "Courier",
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            )
            {
                Column {
                    Text(
                        text = stringResource(R.string.shipmentList_label_status).uppercase(Locale.ROOT),
                        color = Color.Gray,
                    )
                    Text(
                        text = stringResource(model.status.nameRes),//"Widana do doreczenia"
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Column {
                    Text(
                        text = stringResource(R.string.shipmentList_label_awaitingPickupUntil).uppercase(
                            Locale.ROOT
                        ),
                        color = Color.Gray,
                    )
                    Text(
                        text = model.pickUpTime, //"pn.| 14.06.18| 11:3O",
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.End),
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.shipmentList_label_sender).uppercase(Locale.ROOT),
                        color = Color.Gray,
                    )
                    Text(
                        text = model.senderEmail
                            ?: model.senderName,//"Jan Kowalski" or "addressmail@mail.pl"
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(
                        text = stringResource(R.string.shipmentList_label_more).uppercase(Locale.ROOT),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { /*TODO*/ },
                    )
                    Icon(Icons.Default.ArrowForward, contentDescription = "More Icon")
                }
            }
        }
    }

    data class Model(
        val number: String,
        val status: ShipmentStatusUiModel,
        val senderName: String,
        val senderEmail: String?,
        val pickUpTime: String,
    )
}

@Preview
@Composable
private fun PreviewCard() {
    val dummyModel = ParcelLockerItem.Model(
        number = "6567898654334567896543",
        status = ShipmentStatusUiModel.DELIVERED,
        pickUpTime = "pn.| 14.06.18| 11:30",
        senderEmail = "addressmail@mail.pl",
        senderName = "Jan Kowalski"
    )

    ParcelLockerItem.Card(model = dummyModel) {}
}
