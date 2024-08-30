package christianzoeller.matane.data.dictionary.model.firestore.kanji

import christianzoeller.matane.data.dictionary.model.kanji.Meaning
import christianzoeller.matane.data.dictionary.model.kanji.Reading
import christianzoeller.matane.data.dictionary.model.kanji.ReadingMeaning
import christianzoeller.matane.data.dictionary.model.kanji.ReadingMeaningGroup
import christianzoeller.matane.data.dictionary.model.kanji.ReadingType
import com.google.firebase.firestore.PropertyName

data class ReadingMeaningFirestoreDto(
    @PropertyName("Groups")
    var groups: List<ReadingMeaningGroupFirestoreDto>? = null,
    @PropertyName("Nanori")
    var nanori: List<String>? = null,
    @PropertyName("XmlName")
    var xmlName: Any? = null
)

fun ReadingMeaningFirestoreDto.toReadingMeaning(): ReadingMeaning {
    require(groups != null || nanori != null)

    return ReadingMeaning(
        groups = groups?.map { it.toReadingMeaningGroup() },
        nanori = nanori
    )
}

data class ReadingMeaningGroupFirestoreDto(
    @PropertyName("Readings")
    var readings: List<ReadingFirestoreDto>? = null,
    @PropertyName("Meanings")
    var meanings: List<MeaningFirestoreDto>? = null,
    @PropertyName("XmlName")
    var xmlName: Any? = null
)

private fun ReadingMeaningGroupFirestoreDto.toReadingMeaningGroup(): ReadingMeaningGroup {
    require(readings != null || meanings != null)

    return ReadingMeaningGroup(
        readings = readings?.map { it.toReading() },
        meanings = meanings?.map { it.toMeaning() }
    )
}

data class ReadingFirestoreDto(
    @PropertyName("Type")
    var type: String? = null,
    @PropertyName("Value")
    var value: String? = null
)

private fun ReadingFirestoreDto.toReading(): Reading {
    val readingType = when (type) {
        "pinyin" -> ReadingType.PinYin
        "korean_r" -> ReadingType.KoreanRomanized
        "korean_h" -> ReadingType.KoreanHangul
        "vietnam" -> ReadingType.Vietnam
        "ja_on" -> ReadingType.Onyomi
        "ja_kun" -> ReadingType.Kunyomi
        else -> throw IllegalArgumentException("Illegal reading type: $type")
    }

    val readingValue = value!!

    return Reading(
        type = readingType,
        value = readingValue
    )
}

data class MeaningFirestoreDto(
    @PropertyName("Value")
    var value: String? = null,
    @PropertyName("LanguageCode")
    var languageCode: String? = null
)

private fun MeaningFirestoreDto.toMeaning(): Meaning {
    val meaningValue = value!!

    return Meaning(
        value = meaningValue,
        languageCode = languageCode
    )
}