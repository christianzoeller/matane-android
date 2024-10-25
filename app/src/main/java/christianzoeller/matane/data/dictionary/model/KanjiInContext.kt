package christianzoeller.matane.data.dictionary.model

import christianzoeller.matane.data.dictionary.model.kanji.ReadingType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * A much less detailed representation of a [Kanji].
 *
 * Should only be used in cases where the kanji is not shown on its own but
 * rather in a given context, e.g. a sentence or a word.
 */
@Serializable
data class KanjiInContext(
    /**
     * The ID of the kanji.
     */
    @SerialName("Id")
    val id: Int,

    /**
     * The actual kanji character.
     */
    @SerialName("Literal")
    val literal: String,

    /**
     * One possible reading of the kanji. Usually, kanji have several readings,
     * but here, only a single one is used that should be appropriate for a
     * given context. Might be missing, but then, [onyomi] or [kunyomi] should
     * be present.
     */
    @SerialName("Reading")
    val reading: String? = null,

    /**
     * The onyomi readings of the kanji as a comma-separated string. Might be
     * missing in case the [reading] is set.
     */
    @SerialName("Onyomi")
    val onyomi: String? = null,

    /**
     * The kunyomi readings of the kanji as a comma-separated string. Might be
     * missing in case the [reading] is set.
     */
    @SerialName("Kunyomi")
    val kunyomi: String? = null,

    /**
     * The meanings of the kanji as a comma-separated string.
     */
    @SerialName("Meanings")
    val meanings: String,

    /**
     * A relative priority of the kanji. For example, if the kanji is part of a
     * list of kanji ordered by the grade in which they are taught, this could
     * be the grade number.
     */
    @SerialName("Priority")
    val priority: String? = null
)

/**
 * Creates a [KanjiInContext] from this [Kanji].
 *
 * Requires this kanji to have English meanings and Japanese readings.
 * The [reading][KanjiInContext.reading] will never be set, but either
 * [onyomi][KanjiInContext.onyomi] or [kunyomi][KanjiInContext.kunyomi]
 * are guaranteed to be set (if this kanji is valid, that is).
 *
 * @param priority The [priority][KanjiInContext.priority] of this kanji
 * in the given context.
 */
fun Kanji.toKanjiInContext(priority: String?): KanjiInContext {
    if (readingMeaning?.groups.isNullOrEmpty()) {
        throw IllegalArgumentException("Kanji $literal has no readings or meanings")
    }

    val groups = readingMeaning.groups
    val meanings = groups
        .mapNotNull { group -> group.meanings }
        .takeUnless { it.isEmpty() }
        ?.reduce { lhs, rhs -> lhs + rhs }
        ?.filter { meaning -> meaning.languageCode == null }
        ?.map { meaning -> meaning.value }
    if (meanings.isNullOrEmpty()) {
        throw IllegalArgumentException("Kanji $literal has no English meanings")
    }

    val readings = groups
        .mapNotNull { group -> group.readings }
        .takeUnless { it.isEmpty() }
        ?.reduce { lhs, rhs -> lhs + rhs }
    if (readings.isNullOrEmpty()) {
        throw IllegalArgumentException("Kanji $literal has no readings")
    }

    val onyomi = readings
        .filter { it.type == ReadingType.Onyomi }
        .joinToString { it.value }
    val kunyomi = readings
        .filter { it.type == ReadingType.Kunyomi }
        .joinToString { it.value }
    if (onyomi.isEmpty() && kunyomi.isEmpty()) {
        throw IllegalArgumentException("Kanji $literal has no Japanese readings")
    }

    return KanjiInContext(
        id = id,
        literal = literal,
        reading = null,
        onyomi = onyomi.takeUnless { it.isEmpty() },
        kunyomi = kunyomi.takeUnless { it.isEmpty() },
        meanings = meanings.joinToString(),
        priority = priority
    )
}