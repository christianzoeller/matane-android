@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package christianzoeller.matane.feature.dictionary.kanji.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import christianzoeller.matane.feature.dictionary.kanji.KanjiDetailState
import christianzoeller.matane.feature.dictionary.kanji.KanjiListType
import christianzoeller.matane.feature.dictionary.kanji.KanjiLiteral
import christianzoeller.matane.feature.dictionary.kanji.KanjiOverviewState
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiInContextMocks
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiListItemModel
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiMocks
import christianzoeller.matane.feature.dictionary.kanji.model.RadicalInKanjiMocks
import christianzoeller.matane.ui.extensions.scrollToTop
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.ExpandedPreview
import christianzoeller.matane.ui.tooling.MediumPreview

@Composable
fun KanjiListDetailView(
    overviewData: KanjiOverviewState.Content,
    detailState: KanjiDetailState,
    onListTypeChange: (KanjiListType) -> Unit,
    onKanjiClick: (KanjiLiteral) -> Unit,
    onLoadMore: () -> Unit,
    onRadicalClick: (String) -> Unit,
    listDetailNavigator: ThreePaneScaffoldNavigator<KanjiLiteral> = rememberListDetailPaneScaffoldNavigator<KanjiLiteral>()
) {
    BackHandler(listDetailNavigator.canNavigateBack()) {
        listDetailNavigator.navigateBack()
    }

    val listState = rememberLazyListState()
    val detailScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    ListDetailPaneScaffold(
        directive = listDetailNavigator.scaffoldDirective,
        value = listDetailNavigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                KanjiList(
                    data = overviewData,
                    listState = listState,
                    onListTypeChange = onListTypeChange,
                    onKanjiClick = { kanji ->
                        onKanjiClick(kanji)
                        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, kanji)
                        detailScrollState.scrollToTop(coroutineScope)
                    },
                    onLoadMore = onLoadMore
                )
            }
        },
        detailPane = {
            val contentModifier = Modifier
                .verticalScroll(detailScrollState)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)

            AnimatedPane {
                when (detailState) {
                    KanjiDetailState.NoSelection -> KanjiDetailEmpty(contentModifier)

                    is KanjiDetailState.Loading -> KanjiDetail(
                        data = detailState,
                        isLoading = true,
                        onRadicalClick = {},
                        modifier = contentModifier
                    )

                    is KanjiDetailState.Data -> KanjiDetail(
                        data = detailState,
                        isLoading = false,
                        onRadicalClick = onRadicalClick,
                        modifier = contentModifier
                    )

                    KanjiDetailState.Error -> KanjiDetailError(contentModifier)
                }
            }
        }
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun KanjiListDetailView_Loading_Preview() = MataneTheme {
    KanjiListDetailView(
        overviewData = KanjiOverviewState.Loading(
            listType = KanjiListType.ByFrequency
        ),
        detailState = KanjiDetailState.Loading,
        onListTypeChange = {},
        onKanjiClick = {},
        onLoadMore = {},
        onRadicalClick = {}
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun KanjiListDetailView_Content_Preview() = MataneTheme {
    KanjiListDetailView(
        overviewData = KanjiOverviewState.Data(
            kanjiList = List(10) { index ->
                KanjiListItemModel(
                    kanji = KanjiInContextMocks.umi.copy(id = index),
                    isLoading = false
                )
            },
            listType = KanjiListType.ByFrequency
        ),
        detailState = KanjiDetailState.Data(
            kanji = KanjiMocks.sortOfThing,
            radicals = RadicalInKanjiMocks.sortOfThingRadicals
        ),
        onListTypeChange = {},
        onKanjiClick = {},
        onLoadMore = {},
        onRadicalClick = {}
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun KanjiListDetailView_Error_Preview() = MataneTheme {
    KanjiListDetailView(
        overviewData = KanjiOverviewState.Data(
            kanjiList = List(10) { index ->
                KanjiListItemModel(
                    kanji = KanjiInContextMocks.umi.copy(id = index),
                    isLoading = false
                )
            },
            listType = KanjiListType.ByFrequency
        ),
        detailState = KanjiDetailState.Error,
        onListTypeChange = {},
        onKanjiClick = {},
        onLoadMore = {},
        onRadicalClick = {}
    )
}