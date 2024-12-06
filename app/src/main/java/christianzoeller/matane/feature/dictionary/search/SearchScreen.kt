package christianzoeller.matane.feature.dictionary.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.matane.feature.dictionary.search.model.VocabularyMocks
import christianzoeller.matane.R
import christianzoeller.matane.feature.dictionary.search.ui.SearchBar
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.feature.dictionary.search.ui.SearchListDetailView
import christianzoeller.matane.ui.tooling.MatanePreview

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavigateUp: () -> Unit
) {
    val overviewState by viewModel.overviewState.collectAsStateWithLifecycle()
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()
    val query by viewModel.queryState.collectAsStateWithLifecycle()

    Screen(
        overviewState = overviewState,
        detailState = detailState,
        query = query,
        onEnterQuery = viewModel::onEnterQuery,
        onSearch = viewModel::search,
        onItemClick = viewModel::onItemClick,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun Screen(
    overviewState: SearchOverviewState,
    detailState: SearchDetailState,
    query: String,
    onEnterQuery: (String) -> Unit,
    onSearch: (String) -> Unit,
    onItemClick: (SelectedSearchListItem) -> Unit,
    onNavigateUp: () -> Unit
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<SelectedSearchListItem>()

    Column {
        SearchBar(
            query = query,
            onQueryChange = onEnterQuery,
            onSearch = onSearch,
            modifier = Modifier
                .windowInsetsPadding(
                    insets = WindowInsets.safeDrawing.only(
                        sides = WindowInsetsSides.Top
                    )
                )
                .padding(
                    start = 4.dp, end = 4.dp,
                    top = 4.dp, bottom = 12.dp
                ),
            leadingIcon = {
                IconButton(
                    onClick = onNavigateUp
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.global_navigate_up_icon_description)
                    )
                }
            },
            enabled = overviewState.searchEnabled && query.isNotBlank()
        )

        when (overviewState) {
            is SearchOverviewState.Empty -> {}

            is SearchOverviewState.Content -> SearchListDetailView(
                overviewData = overviewState,
                detailState = detailState,
                onItemClick = onItemClick,
                listDetailNavigator = listDetailNavigator
            )

            is SearchOverviewState.Error -> {}
        }
    }
}

@CompactPreview
@Composable
private fun SearchScreen_Loading_Preview() = MatanePreview {
    Screen(
        overviewState = SearchOverviewState.Loading,
        detailState = SearchDetailState.NoSelection,
        query = "something",
        onEnterQuery = {},
        onSearch = {},
        onItemClick = {},
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun SearchScreen_Content_Preview() = MatanePreview {
    Screen(
        overviewState = SearchOverviewState.Data(
            items = VocabularyMocks.searchResults
        ),
        detailState = SearchDetailState.Data(
            item = VocabularyMocks.umi
        ),
        query = "something",
        onEnterQuery = {},
        onSearch = {},
        onItemClick = {},
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun SearchScreen_Error_Preview() = MatanePreview {
    Screen(
        overviewState = SearchOverviewState.Error,
        detailState = SearchDetailState.Error,
        query = "something",
        onEnterQuery = {},
        onSearch = {},
        onItemClick = {},
        onNavigateUp = {}
    )
}