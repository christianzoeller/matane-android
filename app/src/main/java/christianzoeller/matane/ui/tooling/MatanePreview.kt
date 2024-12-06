package christianzoeller.matane.ui.tooling

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import christianzoeller.matane.ui.theme.MataneTheme

@Composable
fun MatanePreview(
    content: @Composable (() -> Unit)
) {
    MataneTheme {
        Surface(
            color = colorScheme.background,
            content = content
        )
    }
}