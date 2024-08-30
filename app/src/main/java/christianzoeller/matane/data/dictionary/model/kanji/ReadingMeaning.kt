package christianzoeller.matane.data.dictionary.model.kanji

data class ReadingMeaning(
    val groups: List<ReadingMeaningGroup>? = null,
    val nanori: List<String>? = null
)

data class ReadingMeaningGroup(
    val readings: List<Reading>? = null,
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

data class Reading(
    val type: ReadingType,
    val value: String
)

enum class ReadingType {
    PinYin,
    KoreanRomanized,
    KoreanHangul,
    Vietnam,
    Onyomi,
    Kunyomi
}

data class Meaning(
    val value: String,
    val languageCode: String? = null
)