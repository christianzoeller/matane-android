package christianzoeller.matane.data.dictionary.model.vocabulary

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO get rid of custom serial names

@Serializable
data class Sense(
    val restrictedToKanji: List<String>? = null,
    @SerialName("RestrictedToReadings")
    val restrictedToReadings: List<String>? = null,
    val partOfSpeech: List<String>? = null,
    val references: List<String>? = null,
    val antonyms: List<String>? = null,
    val fields: List<String>? = null,
    val misc: List<String>? = null,
    val info: List<String>? = null,
    val sourceLanguages: List<SourceLanguage>? = null,
    val dialects: List<String>? = null,
    val glosses: List<Gloss>? = null
) {
    fun hasEnglishMeanings(): Boolean = glosses
        ?.any { it.language == null }
        ?: false

    fun englishMeanings(): String? = glosses
        ?.filter { it.language == null }
        ?.takeIf { it.isNotEmpty() }
        ?.joinToString { it.word }

    fun restrictedTo(): String? = restrictedToKanji
        ?.plus(restrictedToReadings.orEmpty())
        ?.joinToString()
}

@Serializable
data class SourceLanguage(
    val language: String? = null,
    val sourceWord: String,
    val type: String? = null,
    val wasei: String? = null
)

@Serializable
data class Gloss(
    val language: String? = null,
    val word: String
)
