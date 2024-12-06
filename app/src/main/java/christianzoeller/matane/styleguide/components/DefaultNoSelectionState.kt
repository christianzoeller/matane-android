package christianzoeller.matane.styleguide.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview

@Composable
fun DefaultNoSelectionState(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = shapes.medium,
            color = colorScheme.surfaceVariant,
            contentColor = colorScheme.onSurface
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_question_mark_24),
                    contentDescription = stringResource(id = R.string.default_no_selection_state_illustration_description),
                    modifier = Modifier
                        .size(104.dp)
                        .background(
                            color = colorScheme.secondary,
                            shape = CircleShape
                        ),
                    tint = colorScheme.surfaceVariant
                )
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = stringResource(id = R.string.global_no_selection_disclaimer_japanese),
                    style = typography.titleLarge,
                    color = colorScheme.primary
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = message,
                    style = typography.bodyLarge
                )
            }
        }
    }

}

@CompactPreview
@Composable
private fun DefaultNoSelectionState_Preview() = MatanePreview {
    DefaultNoSelectionState(
        message = stringResource(id = R.string.kanji_detail_empty_disclaimer),
        modifier = Modifier.size(400.dp)
    )
}