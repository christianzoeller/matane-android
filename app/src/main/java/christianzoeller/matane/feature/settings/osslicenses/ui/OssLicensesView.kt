package christianzoeller.matane.feature.settings.osslicenses.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import christianzoeller.matane.feature.settings.osslicenses.LibraryOverview
import christianzoeller.matane.feature.settings.osslicenses.OssLicensesState
import christianzoeller.matane.feature.settings.osslicenses.model.OssLicenseInfoMocks
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.ExpandedPreview
import christianzoeller.matane.ui.tooling.MediumPreview

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun OssLicensesView(
    data: OssLicensesState.Content,
    onLibraryClick: (LibraryOverview) -> Unit,
    contentPadding: PaddingValues
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<LibraryOverview>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                OssLicensesList(
                    data = data.overviewData,
                    isLoading = data is OssLicensesState.Loading,
                    onLibraryClick = { library ->
                        onLibraryClick(library)
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, library)
                    }
                )
            }
        },
        detailPane = {
            val contentModifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)

            AnimatedPane {
                when(data) {
                    is OssLicensesState.Loading -> OssLicensesDetailEmpty(contentModifier)

                    is OssLicensesState.Data -> when {
                        data.loadDetailError -> OssLicensesDetailEmpty(contentModifier)

                        data.detailData != null -> OssLicensesDetail(
                            data = data.detailData!!,
                            modifier = contentModifier
                        )
                    }
                }
            }
        },
        modifier = Modifier.padding(contentPadding)
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun OssLicensesView_Loading_Preview() = MataneTheme {
    OssLicensesView(
        data = OssLicensesState.Loading,
        onLibraryClick = {},
        contentPadding = PaddingValues()
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun OssLicensesView_Content_Preview() = MataneTheme {
    OssLicensesView(
        data = OssLicensesState.Data(
            overviewData = OssLicensesState.Content.Overview(OssLicenseInfoMocks.info),
            detailData = OssLicensesState.Content.Detail(
                library = OssLicenseInfoMocks.library,
                licenses = listOf(OssLicenseInfoMocks.license)
            ),
        ),
        onLibraryClick = {},
        contentPadding = PaddingValues()
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun OssLicensesView_Error_Preview() = MataneTheme {
    OssLicensesView(
        data = OssLicensesState.Data(
            overviewData = OssLicensesState.Content.Overview(OssLicenseInfoMocks.info),
            detailData = null,
            loadDetailError = true
        ),
        onLibraryClick = {},
        contentPadding = PaddingValues()
    )
}