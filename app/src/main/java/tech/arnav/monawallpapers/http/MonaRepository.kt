package tech.arnav.monawallpapers.http

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json


class MonaRepository {
    val TAG = "MonaRepo"

    companion object {
        const val MONA_DATA =
            "https://raw.githubusercontent.com/championswimmer/ETHNYC2023-MonaScraper/main/data/monaverse.json"
    }

    suspend fun getMonaData(): MonaData {
        val resp: String = ktorClient.get(MONA_DATA) {
        }.body()
        val monaData = Json.decodeFromString<MonaData>(resp)
        Log.d(TAG, "getMonaData: ")
        Log.d(TAG, "${monaData.size}")
        return monaData
    }
}