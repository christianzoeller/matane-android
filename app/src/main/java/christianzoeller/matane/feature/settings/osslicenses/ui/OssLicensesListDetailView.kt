@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package christianzoeller.matane.feature.settings.osslicenses.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.feature.settings.osslicenses.LibraryOverview
import christianzoeller.matane.feature.settings.osslicenses.OssLicensesDetailState
import christianzoeller.matane.feature.settings.osslicenses.OssLicensesOverviewState
import christianzoeller.matane.feature.settings.osslicenses.model.OssLicenseInfoMocks
import christianzoeller.matane.styleguide.components.DefaultErrorState
import christianzoeller.matane.styleguide.components.DefaultNoSelectionState
import christianzoeller.matane.ui.extensions.scrollToTop
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.ExpandedPreview
import christianzoeller.matane.ui.tooling.MatanePreview
import christianzoeller.matane.ui.tooling.MediumPreview
import kotlinx.coroutines.launch

@Composable
fun OssLicensesListDetailView(
    overviewData: OssLicensesOverviewState.Content,
    detailState: OssLicensesDetailState,
    onLibraryClick: (LibraryOverview) -> Unit,
    listDetailNavigator: ThreePaneScaffoldNavigator<LibraryOverview> = rememberListDetailPaneScaffoldNavigator<LibraryOverview>()
) {
    val coroutineScope = rememberCoroutineScope()

    BackHandler(listDetailNavigator.canNavigateBack()) {
        coroutineScope.launch {
            listDetailNavigator.navigateBack()
        }
    }

    val listState = rememberLazyListState()
    val detailScrollState = rememberScrollState()
    ListDetailPaneScaffold(
        directive = listDetailNavigator.scaffoldDirective,
        value = listDetailNavigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                OssLicensesList(
                    data = overviewData,
                    listState = listState,
                    isLoading = overviewData is OssLicensesOverviewState.Loading,
                    onLibraryClick = { library ->
                        coroutineScope.launch {
                            onLibraryClick(library)
                            listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, library)
                            detailScrollState.scrollToTop(coroutineScope)
                        }
                    }
                )
            }
        },
        detailPane = {
            val contentModifier = Modifier
                .verticalScroll(detailScrollState)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)

            AnimatedPane {
                when (detailState) {
                    is OssLicensesDetailState.NoSelection -> DefaultNoSelectionState(
                        message = stringResource(id = R.string.oss_licenses_detail_empty_disclaimer),
                        modifier = contentModifier
                    )

                    is OssLicensesDetailState.Content -> OssLicensesDetail(
                        data = detailState,
                        modifier = contentModifier
                    )

                    is OssLicensesDetailState.Error -> DefaultErrorState(contentModifier)
                }
            }
        }
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun OssLicensesListDetailView_Loading_Preview() = MatanePreview {
    OssLicensesListDetailView(
        overviewData = OssLicensesOverviewState.Loading,
        detailState = OssLicensesDetailState.NoSelection,
        onLibraryClick = {}
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun OssLicensesListDetailView_Content_Preview() = MatanePreview {
    OssLicensesListDetailView(
        overviewData = OssLicensesOverviewState.Data(
            ossLicenseInfo = OssLicenseInfoMocks.info
        ),
        detailState = OssLicensesDetailState.Content(
            library = OssLicenseInfoMocks.library,
            licenses = listOf(OssLicenseInfoMocks.license)
        ),
        onLibraryClick = {}
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun OssLicensesListDetailView_Error_Preview() = MatanePreview {
    OssLicensesListDetailView(
        overviewData = OssLicensesOverviewState.Data(
            ossLicenseInfo = OssLicenseInfoMocks.info
        ),
        detailState = OssLicensesDetailState.Error,
        onLibraryClick = {}
    )
}