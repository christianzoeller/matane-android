package christianzoeller.matane.data.dictionary.model

import christianzoeller.matane.data.dictionary.model.kanji.DictionaryReference
import christianzoeller.matane.data.dictionary.model.kanji.QueryCode
import christianzoeller.matane.data.dictionary.model.kanji.Misc
import christianzoeller.matane.data.dictionary.model.kanji.Radical as KanjiRadical
import christianzoeller.matane.data.dictionary.model.kanji.ReadingMeaning
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kanji(
    @SerialName("Id")
    val id: Int,
    @SerialName("Literal")
    val literal: String,
    @SerialName("CodePoints")
    val codePoints: List<CodePoint>,
    @SerialName("Radicals")
    val radicals: List<KanjiRadical>,
    @SerialName("Misc")
    val misc: Misc,
    @SerialName("DictionaryReferences")
    val dictionaryReferences: List<DictionaryReference>? = null,
    @SerialName("QueryCodes")
    val queryCodes: List<QueryCode>? = null,
    @SerialName("ReadingMeaning")
    val readingMeaning: ReadingMeaning? = null
) {
    fun keyMeaning() = readingMeaning?.groups
        ?.firstOrNull()
        ?.meanings
        ?.firstOrNull { it.languageCode == null }
        ?.value.orEmpty()
}

@Serializable
data class CodePoint(
    @SerialName("Type")
    val type: CodePointType,
    @SerialName("Value")
    val value: String
)

@Serializable
enum class CodePointType {
    @SerialName("jis208")
    Jis208,
    @SerialName("jis212")
    Jis212,
    @SerialName("jis213")
    Jis213,
    @SerialName("ucs")
    Ucs
}



@Serializable
enum class RadicalType {
    @SerialName("classical")
    Classical,
    @SerialName("nelson_c")
    ClassicNelson
}