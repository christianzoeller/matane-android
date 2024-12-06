package christianzoeller.matane.feature.settings.osslicenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.matane.R
import christianzoeller.matane.feature.settings.osslicenses.model.OssLicenseInfoMocks
import christianzoeller.matane.feature.settings.osslicenses.ui.OssLicensesListDetailView
import christianzoeller.matane.styleguide.components.DefaultErrorState
import christianzoeller.matane.styleguide.components.DefaultTopAppBar
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview

@Composable
fun OssLicensesScreen(
    viewModel: OssLicensesViewModel,
    onNavigateUp: () -> Unit
) {
    val overviewState by viewModel.overviewState.collectAsStateWithLifecycle()
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()

    OssLicensesScreen(
        overviewState = overviewState,
        detailState = detailState,
        onLibraryClick = viewModel::onLibraryClick,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun OssLicensesScreen(
    overviewState: OssLicensesOverviewState,
    detailState: OssLicensesDetailState,
    onLibraryClick: (LibraryOverview) -> Unit,
    onNavigateUp: () -> Unit,
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<LibraryOverview>()

    Column {
        DefaultTopAppBar(
            onNavigateUp = {
                if (listDetailNavigator.canNavigateBack()) {
                    listDetailNavigator.navigateBack()
                } else {
                    onNavigateUp()
                }
            },
            title = R.string.oss_licenses_header
        )

        when (overviewState) {
            is OssLicensesOverviewState.Content -> OssLicensesListDetailView(
                overviewData = overviewState,
                detailState = detailState,
                onLibraryClick = onLibraryClick,
                listDetailNavigator = listDetailNavigator
            )

            OssLicensesOverviewState.Error -> DefaultErrorState(modifier = Modifier.fillMaxSize())
        }
    }
}

@CompactPreview
@Composable
private fun OssLicensesScreen_Loading_Preview() = MatanePreview {
    OssLicensesScreen(
        overviewState = OssLicensesOverviewState.Loading,
        detailState = OssLicensesDetailState.NoSelection,
        onLibraryClick = {},
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun OssLicensesScreen_Content_Preview() = MatanePreview {
    OssLicensesScreen(
        overviewState = OssLicensesOverviewState.Data(
            ossLicenseInfo = OssLicenseInfoMocks.info
        ),
        detailState = OssLicensesDetailState.Content(
            library = OssLicenseInfoMocks.library,
            licenses = listOf(OssLicenseInfoMocks.license)
        ),
        onLibraryClick = {},
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun OssLicensesScreen_Error_Preview() = MatanePreview {
    OssLicensesScreen(
        overviewState = OssLicensesOverviewState.Error,
        detailState = OssLicensesDetailState.Error,
        onLibraryClick = {},
        onNavigateUp = {}
    )
}