package pl.inpost.recruitmenttask.data.network.api

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.inpost.recruitmenttask.data.R
import javax.inject.Inject

class FakeJsonProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun getFakeJson(): String {
        return context.resources.openRawResource(R.raw.mock_shipment_api_response)
            .bufferedReader()
            .use { it.readText() }
    }
}
