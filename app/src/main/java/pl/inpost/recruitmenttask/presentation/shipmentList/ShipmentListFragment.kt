package pl.inpost.recruitmenttask.presentation.shipmentList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mobilez.elementskit.theme.InpostTheme
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.presentation.components.TopBar

@AndroidEntryPoint
class ShipmentListFragment : Fragment() {
    private val viewModel: ShipmentListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireActivity()).apply {
            setContent {
                val shipmentList = viewModel.viewState.observeAsState(emptyList()).value
                InpostTheme {
                    Scaffold(
                        topBar = {
                            TopBar()
                        },
                    ) { padding ->
                        ShipmentListScreen(padding, shipmentList)
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = ShipmentListFragment()
    }
}
