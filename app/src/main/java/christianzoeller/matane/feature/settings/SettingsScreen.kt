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
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.styleguide.components.DefaultListItem
import christianzoeller.matane.styleguide.components.DefaultTopAppBar
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onAppearanceClick: () -> Unit,
    onAcknowledgementsClick: () -> Unit,
    onOssLicenseClick: () -> Unit
) {
    Column {
        DefaultTopAppBar(
            onNavigateUp = null,
            title = R.string.settings_header
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            SettingsListItem(
                label = R.string.settings_appearance,
                onClick = onAppearanceClick,
                iconDescription = R.string.settings_appearance_icon_description,
            )
            Spacer(modifier = Modifier.height(32.dp))
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
    DefaultListItem(
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
private fun SettingsScreen_Preview() = MatanePreview {
    SettingsScreen(
        onAppearanceClick = {},
        onAcknowledgementsClick = {},
        onOssLicenseClick = {}
    )
}