package pl.inpost.recruitmenttask.presentation.shipmentList

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object ZonedDateTimerFormatter {
    fun ZonedDateTime.formatZonedDateTime(): String {
        val formatter =
            DateTimeFormatter.ofPattern("E'| '|dd.MM.yy'| '|HH:mm", Locale.forLanguageTag("pl-PL"))
        return format(formatter)
    }
}
