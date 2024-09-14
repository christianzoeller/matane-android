package christianzoeller.matane.feature.dictionary.kanji.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.feature.dictionary.kanji.KanjiListType
import christianzoeller.matane.styleguide.components.DefaultModalBottomSheet
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview

@Composable
fun KanjiListInfoSheet(
    onDismissRequest: () -> Unit,
    listType: KanjiListType
) {
    DefaultModalBottomSheet(
        onDismissRequest = onDismissRequest,
        title = R.string.kanji_list_info_sheet_header
    ) {
        Content(listType = listType)
    }
}

@Composable
private fun Content(
    listType: KanjiListType,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(
                id = when (listType) {
                    // TODO these texts should give more information
                    KanjiListType.ByFrequency -> R.string.kanji_list_info_sheet_by_frequency_description
                    KanjiListType.ByGrade -> R.string.kanji_list_info_sheet_by_grade_description
                }
            )
        )
    }
}

@CompactPreview
@Composable
private fun KanjiListInfoSheet_ByFrequency_Preview() = MataneTheme {
    Content(
        listType = KanjiListType.ByFrequency,
        modifier = Modifier.padding(16.dp)
    )
}

@CompactPreview
@Composable
private fun KanjiListInfoSheet_ByGrade_Preview() = MataneTheme {
    Content(
        listType = KanjiListType.ByGrade,
        modifier = Modifier.padding(16.dp)
    )
}