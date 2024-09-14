package christianzoeller.matane.feature.dictionary.kanji.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.data.dictionary.model.KanjiInContext
import christianzoeller.matane.feature.dictionary.kanji.KanjiListType
import christianzoeller.matane.feature.dictionary.kanji.KanjiLiteral
import christianzoeller.matane.feature.dictionary.kanji.KanjiOverviewState
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiInContextMocks
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiListItemModel
import christianzoeller.matane.styleguide.effects.OnScrollCloseToEndEffect
import christianzoeller.matane.styleguide.modifiers.placeholder
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun KanjiList(
    data: KanjiOverviewState.Content,
    listState: LazyListState,
    onListTypeChange: (KanjiListType) -> Unit,
    onKanjiClick: (KanjiLiteral) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showInfoSheet by rememberSaveable { mutableStateOf(false) }
    var showOptionsSheet by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        stickyHeader {
            ListHeader(
                listType = data.listType,
                onInfoClick = { showInfoSheet = true },
                onOptionsClick = { showOptionsSheet = true },
                modifier = Modifier
                    .background(color = colorScheme.background)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
        
        items(
            items = data.kanjiList,
            key = { it.kanji.id }
        ) { item ->
            KanjiListItem(
                kanji = item.kanji,
                listType = data.listType,
                isLoading = item.isLoading,
                onClick = {
                    onKanjiClick(KanjiLiteral(it.literal))
                }
            )
        }
    }

    OnScrollCloseToEndEffect(
        listState = listState,
        offsetFromEnd = 10,
        onScrolledCloseToEnd = onLoadMore
    )

    if (showInfoSheet) {
        KanjiListInfoSheet(
            onDismissRequest = { showInfoSheet = false },
            listType = data.listType
        )
    }

    if (showOptionsSheet) {
        KanjiListOptionsSheet(
            onDismissRequest = { showOptionsSheet = false },
            listType = data.listType,
            onListTypeChange = onListTypeChange
        )
    }
}

@Composable
private fun ListHeader(
    listType: KanjiListType,
    onInfoClick: () -> Unit,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(
                id = when (listType) {
                    KanjiListType.ByFrequency -> R.string.kanji_list_header_by_frequency_label
                    KanjiListType.ByGrade -> R.string.kanji_list_header_by_grade_label
                }
            ),
            modifier = Modifier.weight(1f),
            style = typography.titleLarge
        )
        IconButton(
            onClick = onInfoClick,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = stringResource(id = R.string.kanji_list_header_info_button_icon_description)
            )
        }
        IconButton(onClick = onOptionsClick) {
            Icon(
                imageVector = Icons.Default.Settings, // TODO change
                contentDescription = stringResource(id = R.string.kanji_list_header_switch_button_icon_description)
            )
        }
    }
}

@Composable
private fun KanjiListItem(
    kanji: KanjiInContext,
    listType: KanjiListType,
    isLoading: Boolean,
    onClick: (KanjiInContext) -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = kanji.meanings,
                modifier = Modifier.placeholder(visible = isLoading)
            )
        },
        modifier = modifier.clickable(
            enabled = !isLoading,
            onClick = { onClick(kanji) }
        ),
        supportingContent = {
            kanji
                .takeIf { listType == KanjiListType.ByGrade }
                ?.gradeDisclaimer
                ?.let {
                    Text(
                        text = stringResource(id = it),
                        modifier = Modifier.placeholder(visible = isLoading)
                    )
                }
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = colorScheme.secondaryContainer,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = kanji.literal,
                    modifier = Modifier.placeholder(
                        visible = isLoading,
                        color = colorScheme.secondaryContainer
                    ),
                    color = contentColorFor(colorScheme.secondaryContainer)
                )
            }
        },
        trailingContent = {
            kanji.priority
                ?.takeIf { listType == KanjiListType.ByFrequency }
                ?.let {
                    Text(
                        text = it,
                        modifier = Modifier.placeholder(visible = isLoading)
                    )
            }
        }
    )
}

// region Extensions
private val KanjiInContext.gradeDisclaimer: Int?
    @StringRes get() = when (this.priority?.trim()?.toIntOrNull()) {
        1 -> R.string.kanji_list_item_taught_in_grade_one
        2 -> R.string.kanji_list_item_taught_in_grade_two
        3 -> R.string.kanji_list_item_taught_in_grade_three
        4 -> R.string.kanji_list_item_taught_in_grade_four
        5 -> R.string.kanji_list_item_taught_in_grade_five
        6 -> R.string.kanji_list_item_taught_in_grade_six
        8 -> R.string.kanji_list_item_taught_in_junior_high
        9, 10 -> R.string.kanji_list_item_name_kanji_taught_in_junior_high
        else -> null
    }
// endregion

// region Previews
@CompactPreview
@Composable
private fun KanjiList_Loading_Preview() = MataneTheme {
    KanjiListPreview(isLoading = true)
}

@CompactPreview
@Composable
private fun KanjiList_Content_Preview() = MataneTheme {
    KanjiListPreview()
}

@CompactPreview
@Composable
private fun KanjiList_Content_ByGrade_Preview() = MataneTheme {
    KanjiListPreview(
        listType = KanjiListType.ByGrade,
        priority = "3"
    )
}

@Composable
private fun KanjiListPreview(
    listType: KanjiListType = KanjiListType.ByFrequency,
    isLoading: Boolean = false,
    priority: String? = null
) {
    KanjiList(
        data = KanjiOverviewState.Data(
            kanjiList = List(10) { index ->
                KanjiListItemModel(
                    kanji = KanjiInContextMocks.umi.copy(
                        id = index,
                        priority = priority ?: KanjiInContextMocks.umi.priority
                    ),
                    isLoading = isLoading
                )
            },
            listType = listType
        ),
        listState = rememberLazyListState(),
        onListTypeChange = {},
        onKanjiClick = {},
        onLoadMore = {}
    )
}
// endregion