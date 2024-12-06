package christianzoeller.matane.feature.dictionary.radical.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.data.dictionary.model.Radical
import christianzoeller.matane.feature.dictionary.radical.RadicalLiteral
import christianzoeller.matane.feature.dictionary.radical.RadicalOverviewState
import christianzoeller.matane.feature.dictionary.radical.model.RadicalListItemModel
import christianzoeller.matane.feature.dictionary.radical.model.RadicalMocks
import christianzoeller.matane.styleguide.components.DefaultListItem
import christianzoeller.matane.styleguide.effects.OnScrollCloseToEndEffect
import christianzoeller.matane.styleguide.modifiers.placeholder
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RadicalList(
    data: RadicalOverviewState.Content,
    listState: LazyListState,
    onRadicalClick: (RadicalLiteral) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(
            items = data.radicals,
            key = { it.radical.id }
        ) { item ->
            RadicalListItem(
                radical = item.radical,
                isLoading = item.isLoading,
                onClick = {
                    onRadicalClick(RadicalLiteral(it.literal))
                }
            )
        }
    }

    OnScrollCloseToEndEffect(
        listState = listState,
        offsetFromEnd = 10,
        onScrolledCloseToEnd = onLoadMore
    )
}

@Composable
private fun RadicalListItem(
    radical: Radical,
    isLoading: Boolean,
    onClick: (Radical) -> Unit,
    modifier: Modifier = Modifier
) {
    DefaultListItem(
        headlineContent = {
            Text(
                text = stringResource(
                    id = R.string.radical_list_item_contained_in_kanji,
                    radical.kanji.size
                ),
                modifier = Modifier.placeholder(visible = isLoading)
            )
        },
        modifier = modifier.clickable(
            enabled = !isLoading,
            onClick = { onClick(radical) }
        ),
        supportingContent = {
            Text(
                text = stringResource(
                    id = R.string.radical_list_item_stroke_count,
                    radical.strokeCount
                ),
                modifier = Modifier.placeholder(visible = isLoading)
            )
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = colorScheme.primaryContainer,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = radical.literal,
                    modifier = Modifier.placeholder(
                        visible = isLoading,
                        color = colorScheme.primaryContainer
                    ),
                    color = colorScheme.primary
                )
            }
        }
    )
}

// region Previews
@CompactPreview
@Composable
private fun RadicalList_Loading_Preview() = MatanePreview {
    RadicalListPreview(isLoading = true)
}

@CompactPreview
@Composable
private fun RadicalList_Content_Preview() = MatanePreview {
    RadicalListPreview()
}

@Composable
private fun RadicalListPreview(isLoading: Boolean = false) {
    RadicalList(
        data = RadicalOverviewState.Data(
            radicals = List(10) { index ->
                RadicalListItemModel(
                    radical = RadicalMocks.default.copy(
                        id = index
                    ),
                    isLoading = isLoading
                )
            }
        ),
        listState = rememberLazyListState(),
        onRadicalClick = {},
        onLoadMore = {}
    )
}
// endregion