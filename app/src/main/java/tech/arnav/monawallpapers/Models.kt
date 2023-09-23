package tech.arnav.monawallpapers

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray


typealias MonaData = Array<MonaDatum>

@Serializable
data class MonaDatum(
    val id: String,
    val properties: List<Property>,
    val artist: String,
    val title: String,
    val webmURL: String,
    val imgURL: String,
    val lastSalePrice: Double? = null,
    val views: Long
)

@Serializable
enum class Property(val value: String) {
    @SerialName("Abstract")
    Abstract("Abstract"),
    @SerialName("Architecture")
    Architecture("Architecture"),
    @SerialName("Classical")
    Classical("Classical"),
    @SerialName("Education")
    Education("Education"),
    @SerialName("Experimental")
    Experimental("Experimental"),
    @SerialName("Fantasy")
    Fantasy("Fantasy"),
    @SerialName("Fashion")
    Fashion("Fashion"),
    @SerialName("Gallery")
    Gallery("Gallery"),
    @SerialName("Music")
    Music("Music"),
    @SerialName("Nature")
    Nature("Nature"),
    @SerialName("Satire")
    Satire("Satire"),
    @SerialName("Sci-Fi")
    SciFi("Sci-Fi");
}
