package pl.inpost.recruitmenttask.data.network.model

import java.time.ZonedDateTime

data class EventLogNetworkDto(
    val name: String,
    val date: ZonedDateTime,
)
