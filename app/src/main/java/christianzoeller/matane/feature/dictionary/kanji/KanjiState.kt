package christianzoeller.matane.feature.dictionary.kanji

import android.os.Parcelable
import christianzoeller.matane.data.dictionary.model.Kanji
import christianzoeller.matane.data.dictionary.model.KanjiInContext
import christianzoeller.matane.data.dictionary.model.RadicalInKanji
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiMocks
import kotlinx.parcelize.Parcelize

sealed interface KanjiOverviewState {
    sealed class Content(
        open val kanjiList: List<KanjiInContext>
    ) : KanjiOverviewState

    data object Loading : Content(
        kanjiList = List(15) { skeletonListItem(it) }
    )

    data class Data(
        override val kanjiList: List<KanjiInContext>
    ) : Content(
        kanjiList = kanjiList
    )

    data object Error : KanjiOverviewState
}

sealed interface KanjiDetailState {
    data object NoSelection : KanjiDetailState

    sealed class Content(
        open val kanji: Kanji,
        open val radicals: List<RadicalInKanji>
    ) : KanjiDetailState

    data object Loading : Content(
        kanji = skeletonDetailKanji,
        radicals = skeletonDetailRadicals
    )

    data class Data(
        override val kanji: Kanji,
        override val radicals: List<RadicalInKanji>
    ) : Content(
        kanji = kanji,
        radicals = radicals
    )

    data object Error : KanjiDetailState
}

@Parcelize
data class KanjiLiteral(
    val value: String
) : Parcelable

private fun skeletonListItem(id: Int) = KanjiInContext(
    id = id,
    literal = "海",
    reading = "うみ",
    onyomi = "カイ",
    kunyomi = "うみ",
    meanings = "sea, ocean",
    priority = "200"
)

private val skeletonDetailKanji = KanjiMocks.sortOfThing // TODO create a kanji that is optimized for showing a nice skeleton view
private val skeletonDetailRadicals = listOf(
    RadicalInKanji(id = 0, literal = "犬"),
    RadicalInKanji(id = 1, literal = "夕"),
    RadicalInKanji(id = 2, literal = "杰")
)