package christianzoeller.matane.data.dictionary.model.firestore

import christianzoeller.matane.data.dictionary.model.KanjiInContext
import com.google.firebase.firestore.PropertyName

data class KanjiInContextFirestoreDto(
    @PropertyName("Id")
    var id: Int? = null,
    @PropertyName("Literal")
    var literal: String? = null,
    @PropertyName("Meanings")
    var meanings: String? = null,
    @PropertyName("Reading")
    var reading: String? = null,
    @PropertyName("Onyomi")
    var onyomi: String? = null,
    @PropertyName("Kunyomi")
    var kunyomi: String? = null,
    @PropertyName("Priority")
    var priority: String? = null
)

fun KanjiInContextFirestoreDto.toKanjiInContext(
    requirePriority: Boolean
): KanjiInContext {
    val kanjiInContextId = id!! // This is supposed to throw
    val kanjiInContextLiteral = literal!!.trim()
    val kanjiInContextMeanings = meanings!!

    require(kanjiInContextId > 0)
    require(kanjiInContextLiteral.length == 1)
    require(onyomi != null || kunyomi != null)

    if (requirePriority) {
        require(priority != null)
    }

    return KanjiInContext(
        id = kanjiInContextId,
        literal = kanjiInContextLiteral,
        meanings = kanjiInContextMeanings,
        reading = reading,
        onyomi = onyomi,
        kunyomi = kunyomi,
        priority = priority
    )
}