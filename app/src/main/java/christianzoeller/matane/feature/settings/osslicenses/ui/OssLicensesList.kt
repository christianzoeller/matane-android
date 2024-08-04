package christianzoeller.matane.feature.settings.osslicenses.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import christianzoeller.matane.R
import christianzoeller.matane.feature.settings.osslicenses.LibraryOverview
import christianzoeller.matane.feature.settings.osslicenses.OssLicensesState
import christianzoeller.matane.feature.settings.osslicenses.model.OssLicenseInfoMocks
import christianzoeller.matane.styleguide.modifiers.placeholder
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview

@Composable
fun OssLicensesList(
    data: OssLicensesState.Content.Overview,
    isLoading: Boolean,
    onLibraryClick: (LibraryOverview) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = data.ossLicenseInfo.libraries,
            key = { it.uniqueId }
        ) { library ->
            ListItem(
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
private fun OssLicensesList_Loading_Preview() = MataneTheme {
    OssLicensesList(
        data = OssLicensesState.Content.Overview(OssLicenseInfoMocks.info),
        isLoading = true,
        onLibraryClick = {}
    )
}

@CompactPreview
@Composable
private fun OssLicensesList_Content_Preview() = MataneTheme {
    OssLicensesList(
        data = OssLicensesState.Content.Overview(OssLicenseInfoMocks.info),
        isLoading = false,
        onLibraryClick = {}
    )
}

