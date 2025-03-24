package christianzoeller.matane.feature.dictionary.kanji

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiInContextMocks
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiListItemModel
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiMocks
import christianzoeller.matane.feature.dictionary.kanji.model.RadicalInKanjiMocks
import christianzoeller.matane.feature.dictionary.kanji.ui.KanjiListDetailView
import christianzoeller.matane.feature.dictionary.radical.RadicalDetailState
import christianzoeller.matane.feature.dictionary.radical.ui.RadicalDetail
import christianzoeller.matane.styleguide.components.DefaultErrorState
import christianzoeller.matane.styleguide.components.DefaultModalBottomSheet
import christianzoeller.matane.styleguide.components.DefaultTopAppBar
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanjiScreen(
    viewModel: KanjiViewModel,
    onNavigateUp: () -> Unit
) {
    val overviewState by viewModel.overviewState.collectAsStateWithLifecycle()
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()
    val radicalDetailState by viewModel.radicalDetailState.collectAsStateWithLifecycle()

    KanjiScreen(
        overviewState = overviewState,
        detailState = detailState,
        onListTypeChange = viewModel::onListTypeChange,
        onKanjiClick = viewModel::onKanjiClick,
        onLoadMore = viewModel::onLoadMore,
        onRadicalClick = viewModel::onRadicalClick,
        onNavigateUp = onNavigateUp
    )

    when (val radicalState = radicalDetailState) {
        is RadicalDetailState.Content, is RadicalDetailState.Error -> {
            DefaultModalBottomSheet(
                onDismissRequest = viewModel::onDismissRadical,
                sheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true
                )
            ) {
                if (radicalState is RadicalDetailState.Content) {
                    RadicalDetail(
                        data = radicalState,
                        isLoading = radicalState is RadicalDetailState.Loading,
                        onKanjiClick = null,
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth()
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
private fun KanjiScreen(
    overviewState: KanjiOverviewState,
    detailState: KanjiDetailState,
    onListTypeChange: (KanjiListType) -> Unit,
    onKanjiClick: (KanjiLiteral) -> Unit,
    onLoadMore: () -> Unit,
    onRadicalClick: (String) -> Unit,
    onNavigateUp: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<KanjiLiteral>()

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
            title = R.string.kanji_header
        )

        when (overviewState) {
            is KanjiOverviewState.Content -> KanjiListDetailView(
                overviewData = overviewState,
                detailState = detailState,
                onListTypeChange = onListTypeChange,
                onKanjiClick = onKanjiClick,
                onLoadMore = onLoadMore,
                onRadicalClick = onRadicalClick,
                listDetailNavigator = listDetailNavigator
            )

            is KanjiOverviewState.Error -> DefaultErrorState(modifier = Modifier.fillMaxSize())
        }
    }
}

@CompactPreview
@Composable
private fun KanjiScreen_Loading_Preview() = MatanePreview {
    KanjiScreen(
        overviewState = KanjiOverviewState.Loading(
            listType = KanjiListType.ByFrequency
        ),
        detailState = KanjiDetailState.NoSelection,
        onListTypeChange = {},
        onKanjiClick = {},
        onLoadMore = {},
        onRadicalClick = {},
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun KanjiScreen_Content_Preview() = MatanePreview {
    KanjiScreen(
        overviewState = KanjiOverviewState.Data(
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
        onRadicalClick = {},
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun KanjiScreen_Error_Preview() = MatanePreview {
    KanjiScreen(
        overviewState = KanjiOverviewState.Error(
            listType = KanjiListType.ByFrequency
        ),
        detailState = KanjiDetailState.Error,
        onListTypeChange = {},
        onKanjiClick = {},
        onLoadMore = {},
        onRadicalClick = {},
        onNavigateUp = {}
    )
}