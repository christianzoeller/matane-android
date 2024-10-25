package christianzoeller.matane.data.dictionary.model.kanji

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReadingMeaning(
    @SerialName("Groups")
    val groups: List<ReadingMeaningGroup>? = null,
    @SerialName("Nanori")
    val nanori: List<String>? = null
)

@Serializable
data class ReadingMeaningGroup(
    @SerialName("Readings")
    val readings: List<Reading>? = null,
    @SerialName("Meanings")
    val meanings: List<Meaning>? = null
) {
    fun englishMeanings(): String? = meanings
        ?.filter { it.languageCode == null }
        ?.takeIf { it.isNotEmpty() }
        ?.joinToString { it.value }

    fun onyomi(): String? = readings
        ?.filter { it.type == ReadingType.Onyomi }
        ?.takeIf { it.isNotEmpty() }
        ?.joinToString { it.value }

    fun kunyomi(): String? = readings
        ?.filter { it.type == ReadingType.Kunyomi }
        ?.takeIf { it.isNotEmpty() }
        ?.joinToString { it.value }
}

@Serializable
data class Reading(
    @SerialName("Type")
    val type: ReadingType,
    @SerialName("Value")
    val value: String
)

@Serializable
enum class ReadingType {
    @SerialName("pinyin")
    PinYin,
    @SerialName("korean_r")
    KoreanRomanized,
    @SerialName("korean_h")
    KoreanHangul,
    @SerialName("vietnam")
    Vietnam,
    @SerialName("ja_on")
    Onyomi,
    @SerialName("ja_kun")
    Kunyomi
}

@Serializable
data class Meaning(
    @SerialName("Value")
    val value: String,
    @SerialName("LanguageCode")
    val languageCode: String? = null
)