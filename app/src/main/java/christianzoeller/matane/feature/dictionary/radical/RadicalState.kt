package christianzoeller.matane.feature.dictionary.radical

import android.os.Parcelable
import christianzoeller.matane.data.dictionary.model.KanjiInRadical
import christianzoeller.matane.data.dictionary.model.Radical
import christianzoeller.matane.feature.dictionary.radical.model.RadicalListItemModel
import kotlinx.parcelize.Parcelize

sealed interface RadicalOverviewState {
    sealed class Content(
        open val radicals: List<RadicalListItemModel>,
    ) : RadicalOverviewState

    data object Loading : Content(radicals = List(15) { skeletonListItem(it) })

    data class Data(
        override val radicals: List<RadicalListItemModel>
    ) : Content(radicals = radicals)

    data class LoadingMore(
        val currentContent: List<RadicalListItemModel>,
    ) : Content(
        radicals = currentContent + List(15) { skeletonListItem(it) },
    )

    data object Error : RadicalOverviewState
}

sealed interface RadicalDetailState {
    data object NoSelection : RadicalDetailState

    sealed class Content(open val radical: Radical) : RadicalDetailState

    data object Loading : Content(radical = skeletonDetailRadical)

    data class Data(override val radical: Radical) : Content(
        radical = radical
    )

    data object Error : RadicalDetailState
}

@Parcelize
data class RadicalLiteral(
    val value: String
) : Parcelable

private fun skeletonListItem(id: Int) = RadicalListItemModel(
    radical = Radical(
        id = id,
        kanji = List(24) {
            KanjiInRadical(
                id = it,
                literal = "罎"
            )
        },
        literal = "缶",
        strokeCount = 6
    ),
    isLoading = true
)

private val skeletonDetailRadical = Radical(
    id = 32566,
    kanji = List(24) {
        KanjiInRadical(
            id = it,
            literal = "罎"
        )
    },
    literal = "缶",
    strokeCount = 6
)