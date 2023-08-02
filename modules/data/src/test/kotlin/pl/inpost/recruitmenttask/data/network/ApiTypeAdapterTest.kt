package pl.inpost.recruitmenttask.data.network

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime

class ApiTypeAdapterTest {

    private lateinit var apiTypeAdapter: ApiTypeAdapter

    @Before
    fun setUp() {
        apiTypeAdapter = ApiTypeAdapter()
    }

    // Test case to ensure that the method 'toZonedDateTime' accurately parses
    // an ISO offset date-time string into a ZonedDateTime object.
    @Test
    fun `test toZonedDateTime parses ISO offset date time string correctly`() {
        val testDateTimeString = "2023-08-01T12:30:40+02:00"
        val expectedDateTime = ZonedDateTime.parse(testDateTimeString)

        val result = apiTypeAdapter.toZonedDateTime(testDateTimeString)

        assertEquals(expectedDateTime, result)
    }

    // Test case to ensure that the method 'fromZonedDateTime' converts
    // a ZonedDateTime object into its accurate ISO offset date-time string representation.
    @Test
    fun `test fromZonedDateTime formats ZonedDateTime correctly`() {
        val testDateTime = ZonedDateTime.parse("2023-08-01T12:30:40+02:00")
        val expectedDateTimeString = "2023-08-01T12:30:40+02:00"

        val result = apiTypeAdapter.fromZonedDateTime(testDateTime)

        assertEquals(expectedDateTimeString, result)
    }

    // Test case to ensure that when the method 'fromZonedDateTime' is provided with
    // a null ZonedDateTime object, it returns null.
    @Test
    fun `test fromZonedDateTime returns null when date is null`() {
        assertNull(apiTypeAdapter.fromZonedDateTime(null))
    }
}

