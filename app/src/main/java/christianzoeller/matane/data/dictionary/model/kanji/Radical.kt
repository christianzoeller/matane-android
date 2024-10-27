package christianzoeller.matane.data.dictionary.model.kanji

import christianzoeller.matane.data.dictionary.model.RadicalType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Radical(
    @SerialName("Type")
    val type: RadicalType,
    @SerialName("Value")
    val value: Int
)