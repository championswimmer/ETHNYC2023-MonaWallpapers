package tech.arnav.monawallpapers

import android.util.Log
import io.ktor.client.request.get
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class MonaRepository {
    val TAG = "MonaRepo"

    companion object {
        const val MONA_DATA =
            "https://raw.githubusercontent.com/championswimmer/ETHNYC2023-MonaScraper/main/data/monaverse.json"
    }

    suspend fun getMonaData() {
        val monaDataString = ktorClient.get<String>(MONA_DATA)
        val monaData = Json.decodeFromString<MonaData>(monaDataString)
        Log.d(TAG, "getMonaData: ")
        Log.d(TAG, "$monaData")
    }
}