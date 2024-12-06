package christianzoeller.matane.feature.dictionary.search.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable RowScope.() -> Unit = {},
    enabled: Boolean = true
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon()
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            placeholder = { Text(text = stringResource(id = R.string.search_searchbar_placeholder)) },
            trailingIcon = {
                query
                    .takeIf { it.isNotEmpty() }
                    ?.let {
                        IconButton(
                            onClick = { onQueryChange("") },
                            enabled = query.isNotEmpty()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(id = R.string.search_searchbar_clear_icon_description)
                            )
                        }
                    }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch(query) }
            ),
            singleLine = true,
            shape = shapes.medium,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        IconButton(
            onClick = { onSearch(query) },
            enabled = enabled
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_search_button_icon_description)
            )
        }
    }
}

@CompactPreview
@Composable
private fun SearchBar_Preview() = MatanePreview {
    SearchBar(
        query = "something",
        onQueryChange = {},
        onSearch = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@CompactPreview
@Composable
private fun SearchBar_Placeholder_Preview() = MatanePreview {
    SearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@CompactPreview
@Composable
private fun SearchBar_WithLeadingIcon_Preview() = MatanePreview {
    SearchBar(
        query = "something",
        onQueryChange = {},
        onSearch = {},
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}