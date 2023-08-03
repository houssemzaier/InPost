package pl.inpost.recruitmenttask.presentation.shipmentList

import org.junit.Assert.*
import org.junit.Test

import pl.inpost.recruitmenttask.presentation.shipmentList.ZonedDateTimerFormatter.formatZonedDateTime
import java.time.ZonedDateTime

class ZonedDateTimerFormatterTest {

    @Test
    fun `test ZonedDateTime format`() {
        // Given
        val date = ZonedDateTime.parse("2023-08-02T14:30:00+02:00")

        // When
        val formattedDate = date.formatZonedDateTime()

        // Then
        assertEquals("śr.| |02.08.23| |14:30", formattedDate)
    }
}
