@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package christianzoeller.matane.feature.dictionary.radical.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.feature.dictionary.radical.RadicalDetailState
import christianzoeller.matane.feature.dictionary.radical.RadicalLiteral
import christianzoeller.matane.feature.dictionary.radical.RadicalOverviewState
import christianzoeller.matane.feature.dictionary.radical.model.RadicalListItemModel
import christianzoeller.matane.feature.dictionary.radical.model.RadicalMocks
import christianzoeller.matane.styleguide.components.DefaultErrorState
import christianzoeller.matane.styleguide.components.DefaultNoSelectionState
import christianzoeller.matane.ui.extensions.scrollToTop
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.ExpandedPreview
import christianzoeller.matane.ui.tooling.MatanePreview
import christianzoeller.matane.ui.tooling.MediumPreview
import kotlinx.coroutines.launch

@Composable
fun RadicalListDetailView(
    overviewData: RadicalOverviewState.Content,
    detailState: RadicalDetailState,
    onRadicalClick: (RadicalLiteral) -> Unit,
    onLoadMore: () -> Unit,
    onKanjiClick: (String) -> Unit,
    listDetailNavigator: ThreePaneScaffoldNavigator<RadicalLiteral> = rememberListDetailPaneScaffoldNavigator<RadicalLiteral>()
) {
    val coroutineScope = rememberCoroutineScope()

    BackHandler(listDetailNavigator.canNavigateBack()) {
        coroutineScope.launch {
            listDetailNavigator.navigateBack()
        }
    }

    val listState = rememberLazyListState()
    val detailScrollState = rememberScrollState()
    ListDetailPaneScaffold(
        directive = listDetailNavigator.scaffoldDirective,
        value = listDetailNavigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                RadicalList(
                    data = overviewData,
                    listState = listState,
                    onRadicalClick = { radical ->
                        coroutineScope.launch {
                            onRadicalClick(radical)
                            listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, radical)
                            detailScrollState.scrollToTop(coroutineScope)
                        }
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
                    RadicalDetailState.NoSelection -> DefaultNoSelectionState(
                        message = stringResource(id = R.string.radical_detail_empty_disclaimer),
                        modifier = contentModifier
                    )

                    is RadicalDetailState.Loading -> RadicalDetail(
                        data = detailState,
                        isLoading = true,
                        onKanjiClick = {},
                        modifier = contentModifier
                    )

                    is RadicalDetailState.Data -> RadicalDetail(
                        data = detailState,
                        isLoading = false,
                        onKanjiClick = onKanjiClick,
                        modifier = contentModifier
                    )

                    RadicalDetailState.Error -> DefaultErrorState(contentModifier)
                }
            }
        }
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun RadicalListDetailView_Loading_Preview() = MatanePreview {
    RadicalListDetailView(
        overviewData = RadicalOverviewState.Loading,
        detailState = RadicalDetailState.Loading,
        onRadicalClick = {},
        onLoadMore = {},
        onKanjiClick = {}
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun RadicalListDetailView_Content_Preview() = MatanePreview {
    RadicalListDetailView(
        overviewData = RadicalOverviewState.Data(
            radicals = List(10) { index ->
                RadicalListItemModel(
                    radical = RadicalMocks.default.copy(id = index),
                    isLoading = false
                )
            }
        ),
        detailState = RadicalDetailState.Data(
            radical = RadicalMocks.default,
        ),
        onRadicalClick = {},
        onLoadMore = {},
        onKanjiClick = {}
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun RadicalListDetailView_Error_Preview() = MatanePreview {
    RadicalListDetailView(
        overviewData = RadicalOverviewState.Data(
            radicals = List(10) { index ->
                RadicalListItemModel(
                    radical = RadicalMocks.default.copy(id = index),
                    isLoading = false
                )
            }
        ),
        detailState = RadicalDetailState.Error,
        onRadicalClick = {},
        onLoadMore = {},
        onKanjiClick = {}
    )
}