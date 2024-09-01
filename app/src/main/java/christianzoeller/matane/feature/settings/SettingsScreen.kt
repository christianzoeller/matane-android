package christianzoeller.matane.feature.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onAcknowledgementsClick: () -> Unit,
    onOssLicenseClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.settings_header)) }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = stringResource(id = R.string.settings_about_the_app_section_header),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            SettingsListItem(
                label = R.string.settings_about_the_app_section_acknowledgements,
                onClick = onAcknowledgementsClick,
                iconDescription = R.string.settings_about_the_app_section_acknowledgements_icon_description
            )
            SettingsListItem(
                label = R.string.settings_about_the_app_section_oss_licenses,
                onClick = onOssLicenseClick,
                iconDescription = R.string.settings_about_the_app_section_oss_libraries_icon_description
            )
        }
    }
}

@Composable
private fun SettingsListItem(
    @StringRes label: Int,
    onClick: () -> Unit,
    @StringRes iconDescription: Int,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = stringResource(id = label)
            )
        },
        modifier = modifier.clickable(onClick = onClick),
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = stringResource(id = iconDescription)
            )
        }
    )
}

@CompactPreview
@Composable
private fun SettingsScreen_Preview() = MataneTheme {
    SettingsScreen(
        onAcknowledgementsClick = {},
        onOssLicenseClick = {}
    )
}