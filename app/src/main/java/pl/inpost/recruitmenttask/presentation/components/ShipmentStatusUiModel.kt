package pl.inpost.recruitmenttask.presentation.components

import androidx.annotation.StringRes
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.network.model.ShipmentStatusDto

enum class ShipmentStatusUiModel(
    @StringRes val nameRes: Int,
) {

    NOT_READY(R.string.status_not_ready),
    CREATED(R.string.status_created),
    CONFIRMED(R.string.status_confirmed),
    ADOPTED_AT_SOURCE_BRANCH(R.string.status_adopted_at_source_branch),
    SENT_FROM_SOURCE_BRANCH(R.string.status_sent_from_source_branch),
    ADOPTED_AT_SORTING_CENTER(R.string.status_adopted_at_sorting_center),
    SENT_FROM_SORTING_CENTER(R.string.status_sent_from_sorting_center),
    OTHER(R.string.status_other),
    DELIVERED(R.string.status_delivered),
    RETURNED_TO_SENDER(R.string.status_returned_to_sender),
    AVIZO(R.string.status_avizo),
    OUT_FOR_DELIVERY(R.string.status_out_for_delivery),
    READY_TO_PICKUP(R.string.status_ready_to_pickup),
    PICKUP_TIME_EXPIRED(R.string.status_pickup_time_expired),
    ;

    companion object {
        fun ShipmentStatusDto.toUiModel(): ShipmentStatusUiModel {
            return ShipmentStatusUiModel.valueOf(this.name)
        }
    }
}
