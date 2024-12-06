@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package christianzoeller.matane.feature.dictionary.search.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
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
import christianzoeller.matane.ui.extensions.scrollToTop
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.ExpandedPreview
import christianzoeller.matane.ui.tooling.MediumPreview
import christianzoeller.matane.feature.dictionary.search.SearchOverviewState
import christianzoeller.matane.feature.dictionary.search.SearchDetailState
import christianzoeller.matane.feature.dictionary.search.SelectedSearchListItem
import christianzoeller.matane.feature.dictionary.search.model.VocabularyMocks
import christianzoeller.matane.ui.tooling.MatanePreview

@Composable
fun SearchListDetailView(
    overviewData: SearchOverviewState.Content,
    detailState: SearchDetailState,
    onItemClick: (SelectedSearchListItem) -> Unit,
    listDetailNavigator: ThreePaneScaffoldNavigator<SelectedSearchListItem> = rememberListDetailPaneScaffoldNavigator<SelectedSearchListItem>()
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
                SearchList(
                    data = overviewData,
                    listState = listState,
                    onItemClick = { item ->
                        onItemClick(item)
                        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                        detailScrollState.scrollToTop(coroutineScope)
                    }
                )
            }
        },
        detailPane = {
            val contentModifier = Modifier
                .verticalScroll(detailScrollState)
                .fillMaxSize()

            AnimatedPane {
                /*
                when (detailState) {
                    is SearchDetailState.Empty -> TODO()

                    is SearchDetailState.NoSelection -> TODO()

                    is SearchDetailState.Loading -> TODO()

                    is SearchDetailState.Data -> TODO()

                    is SearchDetailState.Error -> TODO()
                }
                 */
            }
        }
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun SearchListDetailView_Loading_Preview() = MatanePreview {
    SearchListDetailView(
        overviewData = SearchOverviewState.Loading,
        detailState = SearchDetailState.Loading,
        onItemClick = {}
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun SearchListDetailView_Content_Preview() = MatanePreview {
    SearchListDetailView(
        overviewData = SearchOverviewState.Data(
            items = VocabularyMocks.searchResults
        ),
        detailState = SearchDetailState.Data(
            item = VocabularyMocks.umi
        ),
        onItemClick = {}
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun SearchListDetailView_Error_Preview() = MatanePreview {
    SearchListDetailView(
        overviewData = SearchOverviewState.Data(
            items = VocabularyMocks.searchResults
        ),
        detailState = SearchDetailState.Error,
        onItemClick = {}
    )
}