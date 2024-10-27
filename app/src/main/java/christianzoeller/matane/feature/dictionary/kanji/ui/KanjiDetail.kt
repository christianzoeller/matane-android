package christianzoeller.matane.feature.dictionary.kanji.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.data.dictionary.model.Kanji
import christianzoeller.matane.data.dictionary.model.RadicalInKanji
import christianzoeller.matane.data.dictionary.model.kanji.ReadingMeaningGroup
import christianzoeller.matane.feature.dictionary.kanji.KanjiDetailState
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiMocks
import christianzoeller.matane.feature.dictionary.kanji.model.RadicalInKanjiMocks
import christianzoeller.matane.styleguide.components.DefaultModalBottomSheet
import christianzoeller.matane.styleguide.modifiers.placeholder
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview

@Composable
fun KanjiDetail(
    data: KanjiDetailState.Content,
    isLoading: Boolean,
    onRadicalClick: ((String) -> Unit)?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = data.kanji.literal,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .placeholder(visible = isLoading),
            style = typography.displayMedium,
            color = colorScheme.primary
        )
        Spacer(modifier = Modifier.height(48.dp))
        ReadingMeaningSection(
            kanji = data.kanji,
            isLoading = isLoading
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column {
            data.kanji.misc.taughtInGrade?.let { grade ->
                Text(
                    text = stringResource(
                        id = R.string.kanji_detail_taught_in_grade,
                        grade
                    ),
                    modifier = Modifier.placeholder(visible = isLoading)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        RadicalSection(
            radicals = data.radicals,
            isLoading = isLoading,
            onRadicalClick = onRadicalClick
        )
    }
}

@Composable
private fun ReadingMeaningSection(
    kanji: Kanji,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    val groups = kanji.readingMeaning?.groups ?: return

    Column(modifier = modifier) {
        when (groups.size) {
            1 -> ReadingMeaningGroup(
                group = groups.first(),
                isLoading = isLoading
            )
            else -> {
                groups.forEachIndexed { index, group ->
                    Row {
                        Text(
                            text = "${index + 1}",
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .placeholder(visible = isLoading),
                            color = colorScheme.outline
                        )
                        ReadingMeaningGroup(
                            group = group,
                            isLoading = isLoading,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReadingMeaningGroup(
    group: ReadingMeaningGroup,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    require(!group.meanings.isNullOrEmpty())
    require(!group.readings.isNullOrEmpty())

    val localDensity = LocalDensity.current
    var onyomiLabelMinWidth by remember { mutableStateOf(0.dp) }

    val meanings = group.englishMeanings().orEmpty()
    val onyomi = group.onyomi()
    val kunyomi = group.kunyomi()
    Column(modifier = modifier) {
        Text(
            text = meanings,
            modifier = Modifier.placeholder(visible = isLoading)
        )
        Spacer(modifier = Modifier.height(8.dp))

        onyomi?.let {
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = stringResource(id = R.string.global_onyomi),
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .widthIn(min = onyomiLabelMinWidth),
                    color = colorScheme.outline
                )
                Text(
                    text = it,
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(visible = isLoading)
                )
            }
        }

        kunyomi?.let {
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = stringResource(id = R.string.global_kunyomi),
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .onPlaced {
                            with(localDensity) {
                                onyomiLabelMinWidth = it.size.width.toDp()
                            }
                        },
                    color = colorScheme.outline
                )
                Text(
                    text = it,
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(visible = isLoading)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RadicalSection(
    radicals: List<RadicalInKanji>,
    isLoading: Boolean,
    onRadicalClick: ((String) -> Unit)?,
    modifier: Modifier = Modifier
) {
    var showInfoSheet by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.kanji_detail_radical_section_header),
                modifier = Modifier.weight(1f),
                style = typography.titleMedium
            )
            IconButton(
                onClick = { showInfoSheet = true },
                enabled = !isLoading
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = stringResource(id = R.string.kanji_detail_radical_section_info_icon_description)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            radicals.forEach { radical ->
                RadicalTile(
                    radical = radical.literal,
                    isLoading = isLoading,
                    onRadicalClick = onRadicalClick
                )
            }
        }
    }

    if (showInfoSheet) {
        DefaultModalBottomSheet(
            onDismissRequest = { showInfoSheet = false },
            title = R.string.kanji_detail_radical_section_radical_explanation_header
        ) {
            Text(
                text = stringResource(
                    id = R.string.kanji_detail_radical_section_radical_explanation
                )
            )
        }
    }
}

@Composable
private fun RadicalTile(
    radical: String,
    isLoading: Boolean,
    onRadicalClick: ((String) -> Unit)?,
    modifier: Modifier = Modifier
) {
    when (onRadicalClick) {
        null -> {
            Surface(
                modifier = modifier,
                shape = shapes.medium,
                color = colorScheme.surfaceContainer
            ) {
                Text(
                    text = radical,
                    modifier = Modifier
                        .placeholder(visible = isLoading)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    style = typography.headlineMedium
                )
            }
        }

        else -> {
            Surface(
                onClick = { onRadicalClick(radical) },
                modifier = modifier,
                enabled = !isLoading,
                shape = shapes.medium,
                color = colorScheme.primaryContainer,
            ) {
                Text(
                    text = radical,
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
private fun KanjiDetail_Preview() = MataneTheme {
    KanjiDetail(
        data = KanjiDetailState.Data(
            kanji = KanjiMocks.sortOfThing,
            radicals = RadicalInKanjiMocks.sortOfThingRadicals
        ),
        isLoading = false,
        onRadicalClick = {},
        modifier = Modifier.padding(16.dp)
    )
}

@CompactPreview
@Composable
private fun KanjiDetail_NotClickable_Preview() = MataneTheme {
    KanjiDetail(
        data = KanjiDetailState.Data(
            kanji = KanjiMocks.sortOfThing,
            radicals = RadicalInKanjiMocks.sortOfThingRadicals
        ),
        isLoading = false,
        onRadicalClick = null,
        modifier = Modifier.padding(16.dp)
    )
}

@CompactPreview
@Composable
private fun KanjiDetail_Loading_Preview() = MataneTheme {
    KanjiDetail(
        data = KanjiDetailState.Data(
            kanji = KanjiMocks.sortOfThing,
            radicals = RadicalInKanjiMocks.sortOfThingRadicals
        ),
        isLoading = true,
        onRadicalClick = {},
        modifier = Modifier.padding(16.dp)
    )
}