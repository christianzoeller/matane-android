package christianzoeller.matane.feature.dictionary.kanji.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
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
import christianzoeller.matane.feature.dictionary.kanji.KanjiListType
import christianzoeller.matane.styleguide.components.DefaultModalBottomSheet
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanjiListOptionsSheet(
    onDismissRequest: () -> Unit,
    listType: KanjiListType,
    onListTypeChange: (KanjiListType) -> Unit
) {
    DefaultModalBottomSheet(
        onDismissRequest = onDismissRequest,
        title = R.string.kanji_list_options_sheet_header
    ) {
        Content(
            onClose = onDismissRequest,
            listType = listType,
            onListTypeChange = onListTypeChange
        )
    }
}

@Composable
private fun Content(
    onClose: () -> Unit,
    listType: KanjiListType,
    onListTypeChange: (KanjiListType) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedListType by rememberSaveable {
        mutableStateOf(listType)
    }

    Column(modifier = modifier) {
        Text(
            text = stringResource(
                id = R.string.kanji_list_options_sheet_description
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        RadioButtonWithText(
            selected = selectedListType == KanjiListType.ByFrequency,
            onClick = { selectedListType = KanjiListType.ByFrequency },
            text = R.string.kanji_list_options_sheet_by_frequency_option
        )
        RadioButtonWithText(
            selected = selectedListType == KanjiListType.ByGrade,
            onClick = { selectedListType = KanjiListType.ByGrade },
            text = R.string.kanji_list_options_sheet_by_grade_option
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                onListTypeChange(selectedListType)
                onClose()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.secondary,
                contentColor = colorScheme.onSecondary
            )
        ) {
            Text(text = stringResource(id = R.string.global_confirm))
        }
    }
}

@Composable
private fun RadioButtonWithText(
    selected: Boolean,
    onClick: () -> Unit,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = colorScheme.secondary,
                unselectedColor = colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(id = text),
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
            color = when (selected) {
                true -> colorScheme.primary
                else -> colorScheme.onSurface
            }
        )
    }
}

@CompactPreview
@Composable
private fun KanjiListOptionsSheet_Preview() = MatanePreview {
    Content(
        onClose = {},
        listType = KanjiListType.ByFrequency,
        onListTypeChange = {},
        modifier = Modifier.padding(16.dp)
    )
}