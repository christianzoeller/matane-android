package christianzoeller.matane.styleguide.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import christianzoeller.matane.R
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    onNavigateUp: () -> Unit,
    @StringRes title: Int
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = title))
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.global_navigate_up_icon_description)
                )
            }
        }
    )
}

@CompactPreview
@Composable
private fun DefaultTopAppBar_Preview() = MataneTheme {
    DefaultTopAppBar(
        onNavigateUp = {},
        title = R.string.oss_licenses_header
    )
}