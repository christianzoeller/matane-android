package christianzoeller.matane.data.dictionary.model

import christianzoeller.matane.data.dictionary.model.kanji.DictionaryReference
import christianzoeller.matane.data.dictionary.model.kanji.QueryCode
import christianzoeller.matane.data.dictionary.model.kanji.Misc
import christianzoeller.matane.data.dictionary.model.kanji.ReadingMeaning

data class Kanji(
    val id: Int,
    val literal: String,
    val codePoints: List<CodePoint>,
    val radicals: List<Radical>,
    val misc: Misc,
    val dictionaryReferences: List<DictionaryReference>? = null,
    val queryCodes: List<QueryCode>? = null,
    val readingMeaning: ReadingMeaning? = null
) {
    fun keyMeaning() = readingMeaning?.groups
        ?.firstOrNull()
        ?.meanings
        ?.firstOrNull { it.languageCode == null }
        ?.value.orEmpty()
}

data class CodePoint(
    val type: CodePointType,
    val value: String
)

enum class CodePointType {
    Jis208,
    Jis212,
    Jis213,
    Ucs
}

data class Radical(
    val type: RadicalType,
    val value: Int
)

enum class RadicalType {
    Classical,
    ClassicNelson
}