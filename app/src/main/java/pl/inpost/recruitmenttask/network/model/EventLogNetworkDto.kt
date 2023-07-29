package pl.inpost.recruitmenttask.network.model

import java.time.ZonedDateTime

data class EventLogNetworkDto(
    val name: String,
    val date: ZonedDateTime,
)
