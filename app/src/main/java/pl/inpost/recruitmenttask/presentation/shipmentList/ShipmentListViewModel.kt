package pl.inpost.recruitmenttask.presentation.shipmentList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.network.api.ShipmentApi
import pl.inpost.recruitmenttask.network.model.ShipmentNetworkDto
import pl.inpost.recruitmenttask.util.setState
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentApi: ShipmentApi
) : ViewModel() {

    private val mutableViewState = MutableLiveData<List<ShipmentNetworkDto>>(emptyList())
    val viewState: LiveData<List<ShipmentNetworkDto>> = mutableViewState

    init {
        refreshData()
    }

    private fun refreshData() {
        GlobalScope.launch(Dispatchers.Main) {
            val shipments = shipmentApi.getShipments()
            mutableViewState.setState { shipments }
        }
    }
}
