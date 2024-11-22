package christianzoeller.matane.data.dictionary.model

import christianzoeller.matane.data.dictionary.model.vocabulary.KanjiElement
import christianzoeller.matane.data.dictionary.model.vocabulary.ReadingElement
import christianzoeller.matane.data.dictionary.model.vocabulary.Sense
import christianzoeller.matane.data.dictionary.model.vocabulary.WordReadingsGroup
import kotlinx.serialization.Serializable

@Serializable
data class VocabularyDetail(
    val id: Int,
    val kanjiElements: List<KanjiElement>? = null,
    val readingElements: List<ReadingElement>,
    val senses: List<Sense>
) {
    val mainWord = kanjiElements
        ?.firstOrNull()?.kanji
        ?: readingElements.firstOrNull()?.reading.orEmpty()

    val unrestrictedReadings = readingElements
        .filter { it.restrictedTo.isNullOrEmpty() }
        .map { it.reading }

    val isKanaOnly = kanjiElements.isNullOrEmpty()

    fun getWordReadings(): List<WordReadingsGroup> {
        return if (kanjiElements.isNullOrEmpty()) {
            readingElements.map {
                WordReadingsGroup(word = it.reading, readings = null)
            }
        } else {
            kanjiElements.map {
                WordReadingsGroup(
                    word = it.kanji,
                    readings = readingElements
                        .filter { reading ->
                            reading.restrictedTo.isNullOrEmpty() ||
                                    reading.restrictedTo.contains(it.kanji)
                        }
                        .map { reading -> reading.reading }
                )
            }
        }
    }
}

