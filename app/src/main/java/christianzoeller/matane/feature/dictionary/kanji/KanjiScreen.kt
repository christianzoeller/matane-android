package christianzoeller.matane.feature.dictionary.kanji

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.matane.R
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiInContextMocks
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiListItemModel
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiMocks
import christianzoeller.matane.feature.dictionary.kanji.model.RadicalInKanjiMocks
import christianzoeller.matane.feature.dictionary.kanji.ui.KanjiListDetailView
import christianzoeller.matane.styleguide.components.DefaultTopAppBar
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview

@Composable
fun KanjiScreen(
    viewModel: KanjiViewModel,
    onNavigateUp: () -> Unit
) {
    val overviewState by viewModel.overviewState.collectAsStateWithLifecycle()
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()

    KanjiScreen(
        overviewState = overviewState,
        detailState = detailState,
        onListTypeChange = viewModel::onListTypeChange,
        onKanjiClick = viewModel::onKanjiClick,
        onLoadMore = viewModel::onLoadMore,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun KanjiScreen(
    overviewState: KanjiOverviewState,
    detailState: KanjiDetailState,
    onListTypeChange: (KanjiListType) -> Unit,
    onKanjiClick: (KanjiLiteral) -> Unit,
    onLoadMore: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<KanjiLiteral>()

    Scaffold(
        topBar = {
            DefaultTopAppBar(
                onNavigateUp = {
                    if (listDetailNavigator.canNavigateBack()) {
                        listDetailNavigator.navigateBack()
                    } else {
                        onNavigateUp()

                    }
                },
                title = R.string.kanji_header
            )
        }
    ) { contentPadding ->
        when (overviewState) {
            is KanjiOverviewState.Content -> KanjiListDetailView(
                overviewData = overviewState,
                detailState = detailState,
                onListTypeChange = onListTypeChange,
                onKanjiClick = onKanjiClick,
                onLoadMore = onLoadMore,
                contentPadding = contentPadding,
                listDetailNavigator = listDetailNavigator
            )

            is KanjiOverviewState.Error -> ErrorView(contentPadding)
        }
    }
}

@Composable
private fun ErrorView(contentPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.kanji_error_disclaimer),
            textAlign = TextAlign.Center,
            style = typography.titleMedium
        )
        // TODO add button to trigger a reload
    }
}

@CompactPreview
@Composable
private fun KanjiScreen_Loading_Preview() = MataneTheme {
    KanjiScreen(
        overviewState = KanjiOverviewState.Loading(
            listType = KanjiListType.ByFrequency
        ),
        detailState = KanjiDetailState.NoSelection,
        onListTypeChange = {},
        onKanjiClick = {},
        onLoadMore = {},
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun KanjiScreen_Content_Preview() = MataneTheme {
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
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun KanjiScreen_Error_Preview() = MataneTheme {
    KanjiScreen(
        overviewState = KanjiOverviewState.Error(
            listType = KanjiListType.ByFrequency
        ),
        detailState = KanjiDetailState.Error,
        onListTypeChange = {},
        onKanjiClick = {},
        onLoadMore = {},
        onNavigateUp = {}
    )
}