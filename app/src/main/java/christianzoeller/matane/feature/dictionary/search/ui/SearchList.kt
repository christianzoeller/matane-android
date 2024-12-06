package christianzoeller.matane.feature.dictionary.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.feature.dictionary.search.SearchOverviewState
import christianzoeller.matane.feature.dictionary.search.SelectedSearchListItem
import christianzoeller.matane.feature.dictionary.search.model.VocabularyMocks
import christianzoeller.matane.styleguide.modifiers.placeholder
import christianzoeller.matane.ui.tooling.MatanePreview

@Composable
fun SearchList(
    data: SearchOverviewState.Content,
    listState: LazyListState,
    onItemClick: (SelectedSearchListItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val isLoading = data !is SearchOverviewState.Data

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(
            items = data.items
        ) { item ->
            ListItem(
                headlineContent = {
                    Text(
                        text = item.word,
                        modifier = Modifier.placeholder(visible = isLoading)
                    )
                },
                modifier = Modifier.clickable(
                    enabled = !isLoading
                ) {
                    onItemClick(SelectedSearchListItem(item.id))
                },
                overlineContent = {
                    item.readings?.let {
                        Text(
                            text = it,
                            modifier = Modifier.placeholder(visible = isLoading)
                        )
                    }
                },
                supportingContent = {
                    Text(
                        text = item.meanings,
                        modifier = Modifier.placeholder(visible = isLoading)
                    )
                }
            )
        }
    }
}

@CompactPreview
@Composable
private fun SearchList_Loading_Preview() = MatanePreview {
    SearchList(
        data = SearchOverviewState.Loading,
        listState = rememberLazyListState(),
        onItemClick = {}
    )
}

@CompactPreview
@Composable
private fun SearchList_Content_Preview() = MatanePreview {
    SearchList(
        data = SearchOverviewState.Data(
            items = VocabularyMocks.searchResults
        ),
        listState = rememberLazyListState(),
        onItemClick = {}
    )
}
