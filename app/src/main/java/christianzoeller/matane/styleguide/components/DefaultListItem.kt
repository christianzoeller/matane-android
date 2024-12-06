package christianzoeller.matane.styleguide.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview

@Composable
fun DefaultListItem(
    headlineContent: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    overlineContent: @Composable (() -> Unit)? = null,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    shadowElevation: Dp = ListItemDefaults.Elevation
) {
    ListItem(
        headlineContent = headlineContent,
        modifier = modifier,
        overlineContent = overlineContent,
        supportingContent = supportingContent,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        colors = ListItemDefaults.colors(
            containerColor = colorScheme.background,
            headlineColor = colorScheme.onBackground,
            overlineColor = colorScheme.onBackground,
            supportingColor = colorScheme.onBackground,
            trailingIconColor = colorScheme.onBackground
        ),
        shadowElevation = shadowElevation
    )
}

@CompactPreview
@Composable
private fun DefaultListItem_Preview() = MatanePreview {
    DefaultListItem(
        headlineContent = { Text(text = "Username") },
        modifier = Modifier.fillMaxWidth(),
        overlineContent = { Text(text = "Role") },
        supportingContent = { Text(text = "Bio") },
        leadingContent = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
    )
}