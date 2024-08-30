package christianzoeller.matane.feature.dictionary.kanji.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import christianzoeller.matane.feature.dictionary.kanji.KanjiLiteral
import christianzoeller.matane.feature.dictionary.kanji.KanjiOverviewState
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiInContextMocks
import christianzoeller.matane.styleguide.modifiers.placeholder
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview

@Composable
fun KanjiList(
    data: KanjiOverviewState.Content,
    isLoading: Boolean,
    onKanjiClick: (KanjiLiteral) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = data.kanjiList,
            key = { it.id }
        ) { kanji ->
            ListItem(
                headlineContent = {
                    Text(
                        text = kanji.meanings,
                        modifier = Modifier.placeholder(visible = isLoading)
                    )
                },
                modifier = Modifier.clickable(
                    enabled = !isLoading,
                    onClick = {
                        onKanjiClick(
                            KanjiLiteral(kanji.literal)
                        )
                    }
                ),
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
                    kanji.priority?.let {
                        Text(
                            text = it,
                            modifier = Modifier.placeholder(visible = isLoading)
                        )
                    }
                }
            )
        }
    }
}

@CompactPreview
@Composable
private fun KanjiList_Loading_Preview() = MataneTheme {
    KanjiList(
        data = KanjiOverviewState.Data(
            kanjiList = List(10) { index ->
                KanjiInContextMocks.umi.copy(id = index)
            }
        ),
        isLoading = true,
        onKanjiClick = {}
    )
}

@CompactPreview
@Composable
private fun KanjiList_Content_Preview() = MataneTheme {
    KanjiList(
        data = KanjiOverviewState.Data(
            kanjiList = List(10) { index ->
                KanjiInContextMocks.umi.copy(id = index)
            }
        ),
        isLoading = false,
        onKanjiClick = {}
    )
}
