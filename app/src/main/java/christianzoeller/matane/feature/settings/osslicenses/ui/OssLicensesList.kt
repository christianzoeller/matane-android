package christianzoeller.matane.feature.settings.osslicenses.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import christianzoeller.matane.R
import christianzoeller.matane.feature.settings.osslicenses.LibraryOverview
import christianzoeller.matane.feature.settings.osslicenses.OssLicensesOverviewState
import christianzoeller.matane.feature.settings.osslicenses.model.OssLicenseInfoMocks
import christianzoeller.matane.styleguide.components.DefaultListItem
import christianzoeller.matane.styleguide.modifiers.placeholder
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview

@Composable
fun OssLicensesList(
    data: OssLicensesOverviewState.Content,
    listState: LazyListState,
    isLoading: Boolean,
    onLibraryClick: (LibraryOverview) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(
            items = data.ossLicenseInfo.libraries,
            key = { it.uniqueId }
        ) { library ->
            DefaultListItem(
                headlineContent = {
                    Text(
                        text = library.name,
                        modifier = Modifier.placeholder(visible = isLoading)
                    )
                },
                modifier = Modifier.clickable(
                    enabled = !isLoading,
                    onClick = {
                        onLibraryClick(
                            LibraryOverview(
                                libraryName = library.name,
                                libraryId = library.uniqueId,
                                licenses = library.licenses.map { it.name }
                            )
                        )
                    }
                ),
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(
                            id = R.string.oss_licenses_list_list_item_icon_description,
                            library.name
                        ),
                        modifier = Modifier.placeholder(visible = isLoading)
                    )
                }
            )
        }
    }
}

@CompactPreview
@Composable
private fun OssLicensesList_Loading_Preview() = MatanePreview {
    OssLicensesList(
        data = OssLicensesOverviewState.Data(OssLicenseInfoMocks.info),
        listState = rememberLazyListState(),
        isLoading = true,
        onLibraryClick = {}
    )
}

@CompactPreview
@Composable
private fun OssLicensesList_Content_Preview() = MatanePreview {
    OssLicensesList(
        data = OssLicensesOverviewState.Data(OssLicenseInfoMocks.info),
        listState = rememberLazyListState(),
        isLoading = false,
        onLibraryClick = {}
    )
}

