package christianzoeller.matane.data.dictionary.model.vocabulary

import kotlinx.serialization.Serializable

@Serializable
data class KanjiElement(
    val kanji: String,
    val info: List<String>? = null,
    val priorities: List<String>? = null
)
