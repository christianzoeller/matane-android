package christianzoeller.matane.feature.dictionary.radical

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.matane.R
import christianzoeller.matane.feature.dictionary.kanji.KanjiDetailState
import christianzoeller.matane.feature.dictionary.kanji.ui.KanjiDetail
import christianzoeller.matane.feature.dictionary.radical.model.RadicalListItemModel
import christianzoeller.matane.feature.dictionary.radical.model.RadicalMocks
import christianzoeller.matane.feature.dictionary.radical.ui.RadicalListDetailView
import christianzoeller.matane.styleguide.components.DefaultErrorState
import christianzoeller.matane.styleguide.components.DefaultModalBottomSheet
import christianzoeller.matane.styleguide.components.DefaultTopAppBar
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadicalScreen(
    viewModel: RadicalViewModel,
    onNavigateUp: () -> Unit
) {
    val overviewState by viewModel.overviewState.collectAsStateWithLifecycle()
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()
    val kanjiDetailState by viewModel.kanjiDetailState.collectAsStateWithLifecycle()

    RadicalScreen(
        overviewState = overviewState,
        detailState = detailState,
        onRadicalClick = viewModel::onRadicalClick,
        onLoadMore = viewModel::onLoadMore,
        onKanjiClick = viewModel::onKanjiClick,
        onNavigateUp = onNavigateUp
    )

    when (val kanjiState = kanjiDetailState) {
        is KanjiDetailState.Content, is KanjiDetailState.Error -> {
            DefaultModalBottomSheet(
                onDismissRequest = viewModel::onDismissKanji,
                sheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true
                )
            ) {
                if (kanjiState is KanjiDetailState.Content) {
                    KanjiDetail(
                        data = kanjiState,
                        isLoading = kanjiState is KanjiDetailState.Loading,
                        onRadicalClick = null,
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    )
                } else {
                    DefaultErrorState(modifier = Modifier.padding(16.dp))
                }
            }
        }

        else -> {}
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun RadicalScreen(
    overviewState: RadicalOverviewState,
    detailState: RadicalDetailState,
    onRadicalClick: (RadicalLiteral) -> Unit,
    onLoadMore: () -> Unit,
    onKanjiClick: (String) -> Unit,
    onNavigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<RadicalLiteral>()

    Column {
        DefaultTopAppBar(
            onNavigateUp = {
                if (listDetailNavigator.canNavigateBack()) {
                    coroutineScope.launch {
                        listDetailNavigator.navigateBack()
                    }
                } else {
                    onNavigateUp()

                }
            },
            title = R.string.radical_header
        )

        when (overviewState) {
            is RadicalOverviewState.Content -> RadicalListDetailView(
                overviewData = overviewState,
                detailState = detailState,
                onRadicalClick = onRadicalClick,
                onLoadMore = onLoadMore,
                onKanjiClick = onKanjiClick,
                listDetailNavigator = listDetailNavigator
            )

            is RadicalOverviewState.Error -> DefaultErrorState(modifier = Modifier.fillMaxSize())
        }
    }
}

@CompactPreview
@Composable
private fun RadicalScreen_Loading_Preview() = MatanePreview {
    RadicalScreen(
        overviewState = RadicalOverviewState.Loading,
        detailState = RadicalDetailState.NoSelection,
        onRadicalClick = {},
        onLoadMore = {},
        onKanjiClick = {},
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun RadicalScreen_Content_Preview() = MatanePreview {
    RadicalScreen(
        overviewState = RadicalOverviewState.Data(
            radicals = List(10) { index ->
                RadicalListItemModel(
                    radical = RadicalMocks.default.copy(id = index),
                    isLoading = false
                )
            }
        ),
        detailState = RadicalDetailState.Data(
            radical = RadicalMocks.default
        ),
        onRadicalClick = {},
        onLoadMore = {},
        onKanjiClick = {},
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun RadicalScreen_Error_Preview() = MatanePreview {
    RadicalScreen(
        overviewState = RadicalOverviewState.Error,
        detailState = RadicalDetailState.Error,
        onRadicalClick = {},
        onLoadMore = {},
        onKanjiClick = {},
        onNavigateUp = {}
    )
}