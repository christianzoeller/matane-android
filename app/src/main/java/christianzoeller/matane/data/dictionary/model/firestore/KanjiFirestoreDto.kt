package christianzoeller.matane.data.dictionary.model.firestore

import christianzoeller.matane.data.dictionary.model.CodePoint
import christianzoeller.matane.data.dictionary.model.CodePointType
import christianzoeller.matane.data.dictionary.model.Kanji
import christianzoeller.matane.data.dictionary.model.Radical
import christianzoeller.matane.data.dictionary.model.RadicalType
import christianzoeller.matane.data.dictionary.model.firestore.kanji.DictionaryReferenceFirestoreDto
import christianzoeller.matane.data.dictionary.model.firestore.kanji.MiscFirestoreDto
import christianzoeller.matane.data.dictionary.model.firestore.kanji.QueryCodeFirestoreDto
import christianzoeller.matane.data.dictionary.model.firestore.kanji.ReadingMeaningFirestoreDto
import christianzoeller.matane.data.dictionary.model.firestore.kanji.toDictionaryReference
import christianzoeller.matane.data.dictionary.model.firestore.kanji.toMisc
import christianzoeller.matane.data.dictionary.model.firestore.kanji.toQueryCode
import christianzoeller.matane.data.dictionary.model.firestore.kanji.toReadingMeaning
import com.google.firebase.firestore.PropertyName

data class KanjiFirestoreDto(
    @PropertyName("Id")
    var id: Int? = null,
    @PropertyName("Literal")
    var literal: String? = null,
    @PropertyName("CodePoints")
    var codePoints: List<CodePointFirestoreDto>? = null,
    @PropertyName("Radicals")
    var radicals: List<RadicalFirestoreDto>? = null,
    @PropertyName("Misc")
    var misc: MiscFirestoreDto? = null,
    @PropertyName("DictionaryReferences")
    var dictionaryReferences: List<DictionaryReferenceFirestoreDto>? = null,
    @PropertyName("QueryCodes")
    var queryCodes: List<QueryCodeFirestoreDto>? = null,
    @PropertyName("ReadingMeaning")
    var readingMeaning: ReadingMeaningFirestoreDto? = null,
    @PropertyName("XmlName")
    var xmlName: Any? = null
)

fun KanjiFirestoreDto.toKanji(): Kanji {
    val kanjiId = id!! // This is supposed to throw
    val kanjiLiteral = literal!!.trim()
    val kanjiCodePoints = codePoints?.map { it.toCodePoint() }!!
    val kanjiRadicals = radicals?.map { it.toRadical() }!!
    val kanjiMisc = misc?.toMisc()!!

    require(kanjiId > 0)
    require(kanjiLiteral.length == 1)
    require(kanjiCodePoints.isNotEmpty())
    require(kanjiRadicals.isNotEmpty())

    return Kanji(
        id = kanjiId,
        literal = kanjiLiteral,
        codePoints = kanjiCodePoints,
        radicals = kanjiRadicals,
        misc = kanjiMisc,
        dictionaryReferences = dictionaryReferences?.map { it.toDictionaryReference() },
        queryCodes = queryCodes?.map { it.toQueryCode() },
        readingMeaning = readingMeaning?.toReadingMeaning()
    )
}

data class CodePointFirestoreDto(
    @PropertyName("Type")
    var type: String? = null,
    @PropertyName("Value")
    var value: String? = null
)

private fun CodePointFirestoreDto.toCodePoint(): CodePoint {
    val codePointType = when (type) {
        "jis208" -> CodePointType.Jis208
        "jis212" -> CodePointType.Jis212
        "jis213" -> CodePointType.Jis213
        "ucs" -> CodePointType.Ucs
        else -> throw IllegalArgumentException("Illegal code point type: $type")
    }

    val codePointValue = value!!

    return CodePoint(
        type = codePointType,
        value = codePointValue
    )
}

data class RadicalFirestoreDto(
    @PropertyName("Type")
    var type: String? = null,
    @PropertyName("Value")
    var value: String? = null
)

private fun RadicalFirestoreDto.toRadical(): Radical {
    val radicalType = when (type) {
        "classical" -> RadicalType.Classical
        "nelson_c" -> RadicalType.ClassicNelson
        else -> throw IllegalArgumentException("Illegal radical type: $type")
    }

    val radicalValue = value?.toInt()!!

    require(radicalValue in 1..214)

    return Radical(
        type = radicalType,
        value = radicalValue
    )
}