package christianzoeller.matane.data.dictionary.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Radical(
    @SerialName("Id")
    val id: Int,
    @SerialName("Characters")
    val kanji: List<KanjiInRadical>,
    @SerialName("Literal")
    val literal: String,
    @SerialName("StrokeCount")
    val strokeCount: Int
)

@Serializable
data class KanjiInRadical(
    @SerialName("Id")
    val id: Int,
    @SerialName("Literal")
    val literal: String
)