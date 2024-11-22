package christianzoeller.matane.data.dictionary.model.vocabulary

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReadingElement(
    val reading: String,
    val noTrueReading: Boolean = false,
    @SerialName("RestrictedToReadings")
    val restrictedTo: List<String>? = null,
    val info: List<String>? = null,
    val priorities: List<String>? = null
)
