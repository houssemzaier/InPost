package pl.inpost.recruitmenttask.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiTypeAdapter @Inject constructor() {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @FromJson
    fun toZonedDateTime(value: String): ZonedDateTime = formatter.parse(value, ZonedDateTime::from)

    @ToJson
    fun fromZonedDateTime(date: ZonedDateTime?): String? = date?.format(formatter)
}
