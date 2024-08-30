package christianzoeller.matane.feature.dictionary.kanji.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview

@Composable
fun KanjiDetailEmpty(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.kanji_detail_empty_disclaimer),
            textAlign = TextAlign.Center,
            style = typography.titleMedium
        )
    }
}

@CompactPreview
@Composable
private fun KanjiDetailEmpty_Preview() = MataneTheme {
    KanjiDetailEmpty(
        modifier = Modifier.padding(16.dp)
    )
}