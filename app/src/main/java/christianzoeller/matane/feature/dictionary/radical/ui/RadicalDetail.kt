package christianzoeller.matane.feature.dictionary.radical.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.data.dictionary.model.KanjiInRadical
import christianzoeller.matane.feature.dictionary.radical.RadicalDetailState
import christianzoeller.matane.feature.dictionary.radical.model.RadicalMocks
import christianzoeller.matane.styleguide.modifiers.placeholder
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview
import kotlin.collections.forEach

@Composable
fun RadicalDetail(
    data: RadicalDetailState.Content,
    isLoading: Boolean,
    onKanjiClick: ((String) -> Unit)?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = data.radical.literal,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .placeholder(visible = isLoading),
            style = typography.displayMedium,
            color = colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(
                id = R.string.radical_detail_stroke_count,
                data.radical.strokeCount
            ),
            modifier = Modifier.placeholder(visible = isLoading)
        )
        Spacer(modifier = Modifier.height(16.dp))
        KanjiSection(
            kanji = data.radical.kanji,
            isLoading = isLoading,
            onKanjiClick = onKanjiClick
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun KanjiSection(
    kanji: List<KanjiInRadical>,
    isLoading: Boolean,
    onKanjiClick: ((String) -> Unit)?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(
                id = when (onKanjiClick) {
                    null -> R.string.radical_detail_contained_in_kanji_not_clickable
                    else -> R.string.radical_detail_contained_in_kanji
                },
                kanji.size
            ),
            modifier = Modifier.placeholder(visible = isLoading)
        )
        Spacer(modifier = Modifier.height(32.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            kanji.forEach { containedKanji ->
                KanjiTile(
                    kanji = containedKanji.literal,
                    isLoading = isLoading,
                    onKanjiClick = onKanjiClick
                )
            }
        }
    }
}

@Composable
private fun KanjiTile(
    kanji: String,
    isLoading: Boolean,
    onKanjiClick: ((String) -> Unit)?,
    modifier: Modifier = Modifier
) {
    when (onKanjiClick) {
        null -> {
            Surface(
                modifier = modifier,
                shape = shapes.medium,
                color = colorScheme.surfaceContainer
            ) {
                Text(
                    text = kanji,
                    modifier = Modifier
                        .placeholder(visible = isLoading)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    style = typography.headlineMedium
                )
            }
        }

        else -> {
            Surface(
                onClick = { onKanjiClick(kanji) },
                modifier = modifier,
                enabled = !isLoading,
                shape = shapes.medium,
                color = colorScheme.primaryContainer,
            ) {
                Text(
                    text = kanji,
                    modifier = Modifier
                        .placeholder(visible = isLoading)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    style = typography.headlineMedium
                )
            }
        }
    }
}

@CompactPreview
@Composable
private fun RadicalDetail_Preview() = MataneTheme {
    RadicalDetail(
        data = RadicalDetailState.Data(
            radical = RadicalMocks.default
        ),
        isLoading = false,
        onKanjiClick = {},
        modifier = Modifier.padding(16.dp)
    )
}

@CompactPreview
@Composable
private fun RadicalDetail_NotClickable_Preview() = MataneTheme {
    RadicalDetail(
        data = RadicalDetailState.Data(
            radical = RadicalMocks.default
        ),
        isLoading = false,
        onKanjiClick = null,
        modifier = Modifier.padding(16.dp)
    )
}

@CompactPreview
@Composable
private fun RadicalDetail_Loading_Preview() = MataneTheme {
    RadicalDetail(
        data = RadicalDetailState.Data(
            radical = RadicalMocks.default
        ),
        isLoading = true,
        onKanjiClick = {},
        modifier = Modifier.padding(16.dp)
    )
}
