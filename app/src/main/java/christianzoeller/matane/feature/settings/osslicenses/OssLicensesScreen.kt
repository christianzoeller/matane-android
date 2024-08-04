package christianzoeller.matane.feature.settings.osslicenses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.matane.R
import christianzoeller.matane.feature.settings.osslicenses.model.OssLicenseInfoMocks
import christianzoeller.matane.feature.settings.osslicenses.ui.OssLicensesView
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview

@Composable
fun OssLicensesScreen(viewModel: OssLicensesViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    OssLicensesScreen(
        state = state,
        onLibraryClick = viewModel::onLibraryClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OssLicensesScreen(
    state: OssLicensesState,
    onLibraryClick: (LibraryOverview) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.oss_licenses_header))
                }
            )
        }
    ) { contentPadding ->
        when (state) {
            is OssLicensesState.Content -> OssLicensesView(
                data = state,
                onLibraryClick = onLibraryClick,
                contentPadding = contentPadding
            )

            OssLicensesState.Error -> ErrorView(contentPadding)
        }
    }
}

@Composable
private fun ErrorView(contentPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.oss_licenses_error_disclaimer),
            textAlign = TextAlign.Center,
            style = typography.titleMedium
        )
    }
}

@CompactPreview
@Composable
private fun OssLicensesScreen_Loading_Preview() = MataneTheme {
    OssLicensesScreen(
        state = OssLicensesState.Loading,
        onLibraryClick = {}
    )
}

@CompactPreview
@Composable
private fun OssLicensesScreen_Content_Preview() = MataneTheme {
    OssLicensesScreen(
        state = OssLicensesState.Data(
            overviewData = OssLicensesState.Content.Overview(OssLicenseInfoMocks.info),
            detailData = OssLicensesState.Content.Detail(
                library = OssLicenseInfoMocks.library,
                licenses = listOf(OssLicenseInfoMocks.license)
            )
        ),
        onLibraryClick = {}
    )
}

@CompactPreview
@Composable
private fun OssLicensesScreen_Error_Preview() = MataneTheme {
    OssLicensesScreen(
        state = OssLicensesState.Error,
        onLibraryClick = {}
    )
}