package pl.inpost.recruitmenttask.presentation.shipmentList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pl.inpost.recruitmenttask.presentation.components.ShipmentList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShipmentListScreen(
    padding: PaddingValues,
    uiState: ShipmentListScreenUiState,
    onRefresh: () -> Unit,
    onDelete: (Section.HasId) -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = onRefresh,
    )

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .padding(padding)
            .fillMaxWidth()
    ) {

        ShipmentList(uiState.shipmentList, onDelete = onDelete)

        PullRefreshIndicator(
            uiState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

data class ShipmentListScreenUiState(
    val isRefreshing: Boolean = false,
    val shipmentList: List<Section> = emptyList()
)
