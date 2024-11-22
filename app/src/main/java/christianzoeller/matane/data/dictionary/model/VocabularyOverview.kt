package christianzoeller.matane.data.dictionary.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VocabularyOverview(
    val id: Int,

    // The actual word. Note that this might be only the first version of
    // the word in case there are multiple versions.
    val word: String,

    // Other versions of the word as a comma-separated string. Might be missing
    // in case there none.
    @SerialName("other_versions")
    val otherVersions: String? = null,

    // The readings of the word as a comma-separated string. Might be missing
    // in case the word has no kanji element and is written in kana only.
    val readings: String? = null,

    // The meanings of the word as a comma-separated string.
    val meanings: String,

    // A relative priority of the word. For example, if the word is part of a
    // list of words ordered by the JLPT level, this could be the JLPT level.
    val priority: String? = null
)
