package christianzoeller.matane.feature.settings.osslicenses.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.feature.settings.osslicenses.OssLicensesState
import christianzoeller.matane.feature.settings.osslicenses.model.OssLicenseInfoMocks
import christianzoeller.matane.styleguide.components.TextLink
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.License

@Composable
fun OssLicensesDetail(
    data: OssLicensesState.Content.Detail,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        LibrarySectionHeader(library = data.library)
        Spacer(modifier = Modifier.height(24.dp))
        LibrarySection(library = data.library)
        Spacer(modifier = Modifier.height(32.dp))
        LicenseSectionHeader()
        Spacer(modifier = Modifier.height(24.dp))
        LicenseSection(licenses = data.licenses)
    }
}

@Composable
private fun LibrarySectionHeader(library: Library) {
    Text(
        text = library.name,
        style = typography.headlineMedium
    )

    when {
        library.organization != null -> {
            Spacer(modifier = Modifier.height(16.dp))

            when (val url = library.organization!!.url) {
                null -> Text(
                    text = library.organization!!.name,
                    style = typography.bodySmall
                )

                else -> TextLink(
                    text = library.organization!!.name,
                    url = url
                )
            }
        }

        else -> {
            library.developers
                .mapNotNull { it.name }
                .takeIf { it.isNotEmpty() }
                ?.joinToString()
                ?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = it,
                        style = typography.bodySmall
                    )
                }
        }
    }
}

@Composable
private fun LibrarySection(library: Library) {
    library.description?.let {
        Text(text = it)
    }

    library.website?.let {
        Spacer(modifier = Modifier.height(8.dp))
        TextLink(text = it, url = it)
    }
}

@Composable
private fun LicenseSectionHeader() {
    Text(
        text = stringResource(id = R.string.oss_licenses_detail_license_header),
        style = typography.titleLarge
    )
}

@Composable
private fun LicenseSection(licenses: List<License>) {
    licenses.forEachIndexed { index, license ->
        if (index != 0) {
            Spacer(modifier = Modifier.height(32.dp))
        }

        Text(
            text = license.name,
            style = typography.titleMedium
        )

        license.url?.let {
            Spacer(modifier = Modifier.height(16.dp))
            TextLink(
                text = it,
                url = it,
                style = typography.bodySmall
            )
        }

        license.licenseContent?.let {
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = it)
        }
    }
}

@CompactPreview
@Composable
private fun OssLicensesDetail_Preview() = MataneTheme {
    OssLicensesDetail(
        data = OssLicensesState.Content.Detail(
            library = OssLicenseInfoMocks.library,
            licenses = listOf(OssLicenseInfoMocks.license)
        ),
        modifier = Modifier.padding(16.dp)
    )
}