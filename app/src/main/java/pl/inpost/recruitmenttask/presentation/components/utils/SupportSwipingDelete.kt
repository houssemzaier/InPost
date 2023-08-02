package pl.inpost.recruitmenttask.presentation.components.utils

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
fun Modifier.supportSwipingDelete(onDelete: () -> Unit): Modifier = composed {
    val swipeState = rememberSwipeableState(initialValue = false)
    val anchors = mapOf(0f to false, -250f to true) // 0f - closed, -250f - opened
    LaunchedEffect(key1 = swipeState.currentValue) {
        if (swipeState.currentValue) {
            onDelete()
            swipeState.animateTo(false)
        }
    }

    this
        .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
        .swipeable(
            state = swipeState,
            anchors = anchors,
            thresholds = { _, _ -> FractionalThreshold(0.5f) },
            orientation = Orientation.Horizontal,
        )
}
