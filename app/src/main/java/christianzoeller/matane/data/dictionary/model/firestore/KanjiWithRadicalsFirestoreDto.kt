package christianzoeller.matane.data.dictionary.model.firestore

import christianzoeller.matane.data.dictionary.model.RadicalInKanji
import com.google.firebase.firestore.PropertyName

data class KanjiWithRadicalsFirestoreDto(
    @PropertyName("Id")
    var id: Int? = null,
    @PropertyName("Literal")
    var literal: String? = null,
    @PropertyName("Radicals")
    var radicals: List<RadicalInKanjiFirestoreDto>? = null
)

fun KanjiWithRadicalsFirestoreDto.toRadicalsInKanji(): List<RadicalInKanji> {
    val radicalInKanji = radicals?.map { it.toRadicalInKanji() }!!

    require(radicalInKanji.isNotEmpty())

    return radicalInKanji
}

data class RadicalInKanjiFirestoreDto(
    @PropertyName("Id")
    var id: Int? = null,
    @PropertyName("Literal")
    var literal: String? = null
)

private fun RadicalInKanjiFirestoreDto.toRadicalInKanji() =
    RadicalInKanji(
        id = id!!,
        literal = literal!!
    )