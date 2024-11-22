package christianzoeller.matane.feature.dictionary.search

import android.os.Parcelable
import christianzoeller.matane.data.dictionary.model.VocabularyDetail
import christianzoeller.matane.data.dictionary.model.VocabularyOverview
import christianzoeller.matane.feature.dictionary.search.model.VocabularyMocks
import kotlinx.parcelize.Parcelize

sealed interface SearchOverviewState {
    data object Empty : SearchOverviewState

    sealed class Content(
        open val items: List<VocabularyOverview>
    ) : SearchOverviewState

    data object Loading : Content(
        items = List(15) { skeletonListItem(it) }
    )

    data class Data(
        override val items: List<VocabularyOverview>
    ) : Content(
        items = items
    )

    data object Error : SearchOverviewState
}

sealed interface SearchDetailState {
    data object Empty : SearchDetailState

    data object NoSelection : SearchDetailState

    sealed class Content(
        open val item: VocabularyDetail
    ) : SearchDetailState

    data object Loading : Content(
        item = skeletonDetail
    )

    data class Data(
        override val item: VocabularyDetail
    ) : Content(
        item = item
    )

    data object Error : SearchDetailState
}

@Parcelize
data class SelectedSearchListItem(
    val value: Int
) : Parcelable

private fun skeletonListItem(id: Int) = VocabularyMocks.kaijoujieikanOverview.copy(id = id)

private val skeletonDetail = VocabularyMocks.umi