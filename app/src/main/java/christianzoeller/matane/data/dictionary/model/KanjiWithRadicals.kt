package christianzoeller.matane.data.dictionary.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KanjiWithRadicals(
    @SerialName("Id")
    val id: Int,
    @SerialName("Literal")
    val literal: String,
    @SerialName("Radicals")
    val radicals: List<RadicalInKanji>
)