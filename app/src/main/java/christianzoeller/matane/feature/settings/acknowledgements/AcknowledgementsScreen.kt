package christianzoeller.matane.feature.settings.acknowledgements

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.styleguide.components.DefaultTopAppBar
import christianzoeller.matane.styleguide.components.TextLink
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview

@Composable
fun AcknowledgementsScreen(
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                onNavigateUp = onNavigateUp,
                title = R.string.acknowledgements_header
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .padding(
                    start = 16.dp, end = 16.dp,
                    top = 24.dp, bottom = 32.dp
                ),
        ) {
            Text(text = stringResource(id = R.string.acknowledgments_intro))
            Spacer(modifier = Modifier.height(24.dp))
            Acknowledgement(
                header = R.string.acknowledgements_kanjidic_header,
                text = R.string.acknowledgements_kanjidic_text,
                linkLabel = R.string.acknowledgements_kanjidic_link_label,
                linkUrl = R.string.acknowledgements_kanjidic_link_url
            )
            Spacer(modifier = Modifier.height(16.dp))
            Acknowledgement(
                header = R.string.acknowledgements_kradfile_header,
                text = R.string.acknowledgements_kradfile_text,
                linkLabel = R.string.acknowledgements_kradfile_link_label,
                linkUrl = R.string.acknowledgements_kradfile_link_url
            )
        }
    }
}

@Composable
private fun Acknowledgement(
    @StringRes header: Int,
    @StringRes text: Int,
    @StringRes linkLabel: Int,
    @StringRes linkUrl: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = header),
            style = typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(id = text))
        Spacer(modifier = Modifier.height(12.dp))
        TextLink(
            text = stringResource(id = linkLabel),
            url = stringResource(id = linkUrl)
        )
    }
}

@CompactPreview
@Composable
private fun AcknowledgementsScreen_Preview() = MataneTheme {
    AcknowledgementsScreen(
        onNavigateUp = {}
    )
}